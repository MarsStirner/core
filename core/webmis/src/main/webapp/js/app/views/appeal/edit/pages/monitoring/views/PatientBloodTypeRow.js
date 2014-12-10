define(function(require) {
	var shared = require('views/appeal/edit/pages/monitoring/shared');

	var patientBloodTypesRowTmpl = require('text!templates/appeal/edit/pages/monitoring/patient-blood-type-row.tmpl');

	var BaseView = require('views/appeal/edit/pages/monitoring/views/BaseView');



	/*
	 * Текущая группа крови пациента
	 * @type {*}
	 */

	var PatientBloodTypeRow = BaseView.extend({
		template: patientBloodTypesRowTmpl,

		data: function() {
			return {
				currentBloodType: this.appeal.get("patient").get("medicalInfo").get("blood"),
				bloodTypes: this.bloodTypesDict,
				canChangeBloodType: this.canChangeBloodType()
			};
		},

		events: {
			"click .edit-blood": "onEditBloodClick",
			"click .save-blood": "onSaveBloodClick",
			"click .cancel-blood": "onCancelBloodClick",
			"click .show-patient-blood-history": "onShowPatientBloodHistory"
		},

		historyShown: false,

		initialize: function(options) {
			BaseView.prototype.initialize.apply(this);

			this.appeal = options.appeal;
			this.collection = options.patientBloodTypes;

			this.bloodTypesDict = options.bloodTypesDict;
			this.bloodTypesDict.on("reset", this.render, this);//.fetch();


		},

		canChangeBloodType: function() {
			if (this.appeal.get('closed') || ((Core.Data.currentRole() === ROLES.NURSE_RECEPTIONIST) || (Core.Data.currentRole() === ROLES.NURSE_DEPARTMENT))) {
				return false;
			} else {
				return true;
			}
		},

		onEditBloodClick: function(event) {
			event.preventDefault();

			this.toggleEditState(true);
		},

		onSaveBloodClick: function(event) {
			event.preventDefault();

			var self = this;
			var newBloodId = parseInt(this.$(".blood-type").val());
			var newBloodName = this.$(".blood-type option:selected").text();

			if (newBloodId != this.data().currentBloodType.get("id")) {
				this.collection.create({
					"bloodDate": new Date().getTime(),
					"bloodType": {
						"id": newBloodId,
						"name": newBloodName
					}
				}, {
					success: function() {
						pubsub.trigger("noty", {
							text: "Группа крови пациента изменена."
						});
						self.collection.fetch();
					},
					error: function() {
						pubsub.trigger("noty", {
							text: "Ошибка при изменении группы крови."
						});
						self.collection.fetch();
					}
				});
			}

			//this.$(".show-patient-blood-history").text(this.$(".blood-type option:selected").html());
			this.appeal.get("patient")
				.get("medicalInfo")
				.get("blood")
				.set({
					id: newBloodId,
					group: newBloodName
				});

			this.toggleEditState(false);
		},

		onCancelBloodClick: function(event) {
			event.preventDefault();

			this.toggleEditState(false);
		},

		onShowPatientBloodHistory: function(event) {
			event.preventDefault();
			var $target = $(event.currentTarget);

			this.historyShown = !this.historyShown;

			$target.prop("title", this.historyShown ? "Скрыть историю изменения" : "Показать историю изменения");

			this.collection.trigger(this.historyShown ? "request:show" : "request:hide");
		},

		toggleEditState: function(enabled) {
			if (enabled) {
				this.$("td").first().prop("colspan", 2);
				this.$("td").last().hide();
				this.$(".current-blood-type").hide();
				this.$(".blood-type-selector").show();
				this.$(".blood-type").focus();

				this.$el.css({
					"background-color": "whitesmoke"
				});
			} else {
				this.$("td").first().prop("colspan", 1);
				this.$("td").last().show();
				this.$el.css({
					"background-color": "white"
				});
				this.render();
			}
		},

		render: function() {
			BaseView.prototype.render.apply(this);

			this.$(".blood-type-selector").hide();

			this.$(".save-blood").button();

			this.$(".edit-blood").button();

			return this;
		}
	});

	return PatientBloodTypeRow;
});
