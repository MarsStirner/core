define(function(require) {
	// var shared = require("views/appeal/edit/pages/monitoring/shared");
	var BaseView = require("views/appeal/edit/pages/monitoring/views/BaseView");

	var patientDiagnosesListTmpl = require("text!templates/appeal/edit/pages/monitoring/patient-diagnoses-list.tmpl");


	/**
	 * Список диагнозов пациента
	 * @type {*}
	 */
	var PatientDiagnosesList = BaseView.extend({
		template: patientDiagnosesListTmpl,

		data: function() {
			return {
				diagnoses: this.collection
			};
		},

		initialize: function(options) {
			BaseView.prototype.initialize.apply(this);

			this.collection.on("reset", this.render, this); //.fetch();
			this.collection.on("reset", this.checkClinicalDiagnosis, this);
		},

		clinicalDiagnosisDocsCount: function() {
			return $.ajax({
				url: '/api/v1/appeals/' + this.options.appealId + '/countActionsByType?actyonTypeId=426',
				dataType: 'jsonp'
			});
		},

		clinicalDiagnosisExist: function() {
			var diagnoses = this.collection.some(function(model) {
				return _.some(["mainDiagMkb"], function(diagnosisKind) {
					return diagnosisKind === model.get("diagnosisKind");
				}, this);
			}, this);

			return diagnoses;
		},

		checkClinicalDiagnosis: function() {
			var self = this;

			var diff = moment().diff(moment(this.options.appeal.get("rangeAppealDateTime").get("start")), "days");

			if (!self.clinicalDiagnosisExist() && (diff >= 4)) {
				self.showWarning();
			}
			// this.clinicalDiagnosisDocsCount()
			// 	.done(function(response) {

			// 		if ((!self.clinicalDiagnosisExist() || (response.data.actionsCount == 0)) && (diff >= 4)) {
			// 			self.showWarning();
			// 		}

			// 	});
		},

		showWarning: function() {
			pubsub.trigger("noty", {
				text: "Не установлен  клинический диагноз! ",
				type: "warning"
			});

			this.$('.diagnosis-title').append(' <span id="warnings" style="color: red;"><i class="icon-warning-sign"></i> <span class="msg">Не установлен  клинический диагноз!</span> </span>')
		},

		render: function() {

			BaseView.prototype.render.apply(this);


			$(".HasToolTip", this.$el).each(function() {
				var tip = $($(this).data("tooltip-content"));
				var dx = -tip.width() / 2 - 35,
					dy = 15;
				tip.css("position", "absolute");
				tip.hide();

				function position(e) {
					tip.css("left", e.pageX + dx + "px").css("top", e.pageY + dy + "px");
				}

				$(this).mousemove(position);

				$(this).hover(function(e) {
					position(e);
					tip.show();
				}, function(e) {
					tip.hide();
				});
			});

			return this;

		}
	});

	return PatientDiagnosesList;

});
