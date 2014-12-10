define(function(require) {
	// var shared = require('views/appeal/edit/pages/monitoring/shared');

	var signalInfoTmpl = require('text!templates/appeal/edit/pages/monitoring/signal-info.tmpl');

	var BaseView = require('views/appeal/edit/pages/monitoring/views/BaseView');

	/**
	 * Блок сигнальной информации о пациенте
	 * @type {*}
	 */
	var SignalInfo = BaseView.extend({
		template: signalInfoTmpl,

		data: function() {
			var data = {
				lastMove: this.moves.last(),
				appeal: this.appeal.toJSON(),
				days: this.days(),
				canAssign: this.canAssign()
			};
			//console.log('data', data)

			return data;
		},

		events: {
			"click .assign-exec-person": "onAssignExecPersonClick"
		},
		days: function() {
			var days;
			var appealJSON = this.appealJSON;
			//продолжительность лечения
			if (appealJSON.appealType.requestType.id === 1) {
				//дневной стационар
				days = moment().startOf('day').diff(moment(appealJSON.rangeAppealDateTime.start).startOf('day'), "days") + 1;
			} else if (appealJSON.appealType.requestType.id === 2) {
				//круглосуточный стационар
				days = moment().startOf('day').diff(moment(appealJSON.rangeAppealDateTime.start).startOf('day'), "days");
			}


			return days;
		},

		initialize: function(options) {
			this.appeal = options.appeal;
			this.appealJSON = options.appealJSON;
			this.execPersonAssignmentDialog = options.execPersonAssignmentDialog;
			BaseView.prototype.initialize.apply(this);

			this.moves = options.moves;
			//this.moves.appealId = appeal.get("id");
			//console.log("fetching moves");
			this.moves.on("reset", this.render, this); //.fetch();


			this.appeal.on("change:execPerson", this.onExecPersonDoctorChange, this);

			if (!this.appeal.get("execPerson").id) {
				pubsub.trigger("noty", {
					text: "Требуется назначить лечащего врача.",
					type: "warning"
				});
			}
		},

		canAssign: function() {
			if (this.appeal.get('closed') || ((Core.Data.currentRole() === ROLES.NURSE_RECEPTIONIST) || (Core.Data.currentRole() === ROLES.NURSE_DEPARTMENT))) {
				return false;
			} else {
				return true;
			}
		},

		onAssignExecPersonClick: function(event) {
			event.preventDefault();
			this.openExecPersonAssignmentDialog();
		},

		onExecPersonDoctorChange: function() {
			this.render();
		},

		openExecPersonAssignmentDialog: function() {
			this.execPersonAssignmentDialog.render().open();
		},

		render: function() {
			this.$el.empty().append(this._template(this.data()));

			this.$('.assign-exec-person').button();
			return this;
		}
	});


	return SignalInfo;
});
