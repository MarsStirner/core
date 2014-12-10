define([
	"text!templates/pages/appeal-representative.tmpl",
	"views/pages/patient-edit",
	"views/pages/patients-list"
], function (tmpl) {

	App.Views.AppealRepresentative = Backbone.View.extend({
		//template: tmpl,
		tagName: "section",
		className: "PopUpLine popup",

		events: {
			"click .PrevPage"    : "onBackToSearchClick",
			"click .CloseHistory": "close"
		},

		/*states: {
			search: App.Views.PatientsList,
			edit  : App.Views.PatientEdit
		},*/

		initialize: function () {
			_.bindAll(this);

			/*this.stateView = new App.Views.PatientsList({
				path: Backbone.history.fragment,
				popUpMode: true
			});

			this.stateView.on("patient:selected", this.onPatientSelect);
			this.stateView.on("patient:newClick", this.onNewPatientClick);*/

			/*this.patientEdit = new App.Views.PatientEdit({
				model: new App.Models.Patient,
				popUpMode: true
			});

			this.patientEdit.on("patient:created", this.onPatientCreate);
			this.patientEdit.on("patient:canceled", this.onCancel);*/
		},

		onNewPatientClick: function () {
			this.toggleState("new");
		},

		onBackToSearchClick: function (event) {
			event.preventDefault();
			this.toggleState("search");
		},

		onPatientSelect: function (patient) {
			this.representativeSelectionMade(patient);
		},

		onPatientCreate: function (patient) {
			this.representativeSelectionMade(patient);
		},

		onCancel: function () {
			this.close();
		},

		representativeSelectionMade: function (patient) {
			this.trigger("representative:selected", patient);
			this.close();
		},

		toggleState: function (state, model) {
			this.cleanUp();

			switch (state) {
				case "edit":
					this.stateView = new App.Views.PatientEdit({
						model: model,
						popUpMode: true
					});
					this.stateView.on("patient:created", this.onPatientCreate);
					this.stateView.on("patient:canceled", this.onCancel);

					this.$el.dialog("option", "title", "Редактирование карточки законного представителя");
					this.$el.parent().find(".PrevPage").removeClass("Hidden");
					break;

				case "new":
					this.stateView = new App.Views.PatientEdit({
						popUpMode: true
					});
					this.stateView.on("patient:created", this.onPatientCreate);
					this.stateView.on("patient:canceled", this.onCancel);

					this.$el.dialog("option", "title", "Создание карточки законного представителя");
					this.$el.parent().find(".PrevPage").removeClass("Hidden");
					break;

				case "search":
					this.stateView = new App.Views.PatientsList({
						path: Backbone.history.fragment,
						popUpMode: true
					});
					this.stateView.on("patient:selected", this.onPatientSelect);
					this.stateView.on("patient:newClick", this.onNewPatientClick);

					this.$el.dialog("option", "title", "Добавление законного представителя");
					this.$el.parent().find(".PrevPage").addClass("Hidden");
					break;
			}

			this.$el.empty().append(this.stateView.render().el);
		},

		render: function () {
			var self = this;

			this.$el.dialog({
				autoOpen: false,
				width: "118em",
				maxHeight: "600px",
				modal: true,
				dialogClass: "webmis",
				resizable: false,
				title: "Добавление законного представителя",
				close: this.close
			});

			var prevPageLink = $("<a class='Hidden Actions PrevPage' style='float: left; margin: .1em 16px .2em 0;'><span>← Назад к поиску</span></a>");
			prevPageLink.on("click", this.onBackToSearchClick);
			this.$el.parent().find(".ui-dialog-titlebar").prepend(prevPageLink);

			/*if ($(this.$el.parent()).length === 0) {
				this.$el.html($.tmpl(this.template, {}));

				this.$el.dialog({
					autoOpen: false,
					width: "118em",
					maxHeight: "600px",
					modal: true,
					dialogClass: "webmis",
					resizable: false,
					title: "Добавление законного представителя"
				});

				var prevPageLink = $("<a href='#' class='Hidden PrevPage' style='float: left; margin: .1em 16px .2em 0;'>← Назад к поиску</a>");
				prevPageLink.on("click", this.onBackToSearchClick);

				this.$el.parent().find(".ui-dialog-titlebar").prepend(prevPageLink);

				//this.$el.parent().find(".ui-dialog-titlebar").prepend("<a href='#' class='Hidden PrevPage' style='float: left;'>← Назад к поиску</a>");

				this.$el.parent().find("a").click(function (event) {
					event.preventDefault();
				});

				this.$(".SearchLine").html(this.patientList.render().el);
				this.$(".EditLine").html(this.patientEdit.render().el);
			}*/

			return this;
		},

		open: function (state, model) {
			state ? this.toggleState(state, model) : this.toggleState("search");
			this.$el.dialog("open");
			this.$el.dialog("option", "position", ["top", 30]);
		},

		openWithEdit: function (model) {
			/*this.patientEdit = new App.Views.PatientEdit({
				model: patient,
				popUpMode: true
			});

			this.patientEdit.on("patient:created",this.onPatientCreate, this);
			this.patientEdit.on("patient:canceled", this.onCancel, this);*/

			//this.$(".EditLine").html(this.patientEdit.render().el);
			/*this.patientEdit.attachModelHandlers();
			this.patientEdit.changeStep("", true);
			this.patientEdit.updateButtonText();*/

			this.open("edit", model);
		},

		cleanUp: function () {
			if (this.stateView) {
				this.stateView.off(null, null, this);
				if (this.stateView.model) {
					this.stateView.model.off(null, null, this.stateView);
				}
				if (this.stateView.collection) {
					this.stateView.collection.off(null, null, this.stateView);
					this.stateView.collection.reset();
				}
			}
		},

		close: function () {
			this.trigger("close");
			this.off(null, null, this);
			this.cleanUp();
			this.$el.dialog("close");
			this.remove();
		}
	});
});