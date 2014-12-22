/**
 * User: FKurilov
 * Date: 08.06.12
 */
define(function(require) {

	var template = require("text!templates/appeal/edit/main.tmpl");
	var LaboratoryView = require("views/diagnostics/laboratory/AnalyzesListView");
	var LaboratoryResultView = require("views/diagnostics/laboratory/AnalysisResultView");
	var InstrumentalView = require("views/diagnostics/instrumental/InstrumentalView");
	var InstrumentalResultView = require("views/diagnostics/instrumental/InstrumentalResultView");
	var ConsultationView = require("views/diagnostics/consultations/ConsultationsListView");
	var ConsultationResultView = require("views/diagnostics/consultations/ConsultationsResultView");
	var QuotesView = require("views/appeal/edit/pages/QuotesView");
	var PatientMonitoringView = require("views/appeal/edit/pages/PatientMonitoringView");
	var Monitoring = require("views/appeal/edit/pages/monitoring/monitoring");
	var Documents = require("views/documents/documents");
	var Moves = require("views/moves/moves");
	var HospitalBed = require("views/moves/HospitalBedView");

    var AppealPrescriptionsView = require("views/prescriptions/views/appealPrescriptions/AppealPrescriptions");
    var PrescriptionNewView = require("views/prescriptions/views/appealPrescriptions/PrescriptionNewView");
    var PrescriptionEditView = require("views/prescriptions/views/appealPrescriptions/PrescriptionEditView");
	require("models/appeal");
	require("collections/patient-appeals");
	require("views/breadcrumbs");
	require("views/menu");
	require("views/card-header");
	/*require("views/appeal/edit/pages/examinations");
	require("views/appeal/edit/pages/examination-edit");
	require("views/appeal/edit/pages/examination-primary");
	require("views/appeal/edit/pages/card");*/


	App.Views.Main = View.extend({
		template: template,

		appeal: {},
		patient: {},

		documentEditorMode: false,

		/*getBreadcrumbsRoot: function () {
			return [
				{title: "Пациенты", url: "/patients/"},
				{title: this.patient.get("name").get("raw") + " (" + moment(this.patient.get("birthDate")).year() + ") г.р.", url: "/appeals/"}
			];
		},*/

		pageViews: {
			"card": {
				REVIEW: App.Views.Card
				/*REVIEW: function () {
					var title = "Основное";
					if (Core.Data.currentRole() === ROLES.DOCTOR_DEPARTMENT) {
						title = "Титульный лист ИБ";
					}

					return {
						title: title,
						view: App.Views.Card,
						breadcrumbs: _.union(this.getBreadcrumbsRoot(), [
							{label: "Лабораторные исследования", path: false}
						])
					};
				}*/
			},

			"diagnostics-laboratory": {
				"REVIEW": LaboratoryView,
				"SUB_REVIEW": LaboratoryResultView,
				"SUB_EDIT": LaboratoryResultView //TODO: impl edit mode
			},

			"diagnostics-instrumental": {
				"REVIEW": InstrumentalView,
				"SUB_REVIEW": InstrumentalResultView,
				"SUB_EDIT": InstrumentalResultView //TODO: impl edit mode
			},

			"diagnostics-consultations": {
				"REVIEW": ConsultationView,
				"SUB_REVIEW": ConsultationResultView,
				"SUB_EDIT": Documents.Views.Edit.Consultation.Layout
			},
            "prescriptions": {
                "REVIEW": AppealPrescriptionsView,
                "SUB_REVIEW": PrescriptionNewView,
                "SUB_EDIT": PrescriptionEditView
            },
			"quotes": {
				"REVIEW": QuotesView
			},

			"patient-monitoring": {
				"REVIEW": PatientMonitoringView
			},

			"moves": {
				"REVIEW": Moves
			},

			"hospitalbed": {
				"REVIEW": HospitalBed
			},

			"monitoring": {
				"REVIEW": Monitoring.Views.Layout
			},

			"documents": {
				"REVIEW": Documents.Views.List.Common.Layout,
				"SUB_REVIEW": Documents.Views.Review.Common.Layout,
				"SUB_EDIT": Documents.Views.Edit.Common.Layout,
				"SUB_NEW": Documents.Views.Edit.Common.Layout
			},

			"therapy": {
				"REVIEW": Documents.Views.List.Therapy.Layout,
				"SUB_REVIEW": Documents.Views.Review.Therapy.Layout,
				"SUB_EDIT": Documents.Views.Edit.Therapy.Layout,
				"SUB_NEW": Documents.Views.Edit.Therapy.Layout
			},
			"examinations": {
				"REVIEW": Documents.Views.List.Examination.Layout,
				"SUB_REVIEW": Documents.Views.Review.Examination.Layout,
				"SUB_EDIT": Documents.Views.Edit.Examination.Layout,
				"SUB_NEW": Documents.Views.Edit.Examination.Layout
			}
		},

		breadCrumbsMap: {
			"diagnostics-laboratory": App.Router.cachedBreadcrumbs.LABORATORY,
			"diagnostics-laboratory-result": App.Router.cachedBreadcrumbs.LABORATORY_RESULT,
			"diagnostics-instrumental": App.Router.cachedBreadcrumbs.INSTRUMENTAL,
			"diagnostics-instrumental-result": App.Router.cachedBreadcrumbs.INSTRUMENTAL_RESULT,
			"diagnostics-consultations": App.Router.cachedBreadcrumbs.CONSULTATION,
			"diagnostics-consultations-result": App.Router.cachedBreadcrumbs.CONSULTATION_RESULT,
			"quotes": App.Router.cachedBreadcrumbs.QUOTES,
			"patient-monitoring": App.Router.cachedBreadcrumbs.PATIENT_MONITORING,
			"examinations": App.Router.cachedBreadcrumbs.EXAMS,
			"summary": App.Router.cachedBreadcrumbs.SUMMARY,
			"examinations-primary": App.Router.cachedBreadcrumbs.EXAMS,
			"card": App.Router.cachedBreadcrumbs.APPEAL,
			"moves": App.Router.cachedBreadcrumbs.MOVES,
			"hospitalbed": App.Router.cachedBreadcrumbs.HOSPITALBED,
			"monitoring": App.Router.cachedBreadcrumbs.APPEAL,
			"documents": App.Router.cachedBreadcrumbs.APPEAL
		},

		initialize: function() {
			var self = this;
			this.appealId = this.options.appealId;
			this.page = this.options.page;

			if (!(this.appealId && this.pageViews[this.page])) {
				throw new Error("Invalid diagnostic type or empty appeal id");
			}

			this.appeal = new App.Models.Appeal({
				id: this.appealId
			});

			this.breadcrumbs = new App.Views.Breadcrumbs();

			this.cardHeader = new App.Views.CardHeader({
				model: this.appeal
			});

			pubsub.on('appeal:closed', function(opt) {
				self.appeal.set('closed', opt.closed);
				self.appeal.set('closeDateTime', opt.closeDate);
				self.appeal.set('result', {
					name: opt.resultText
				});

			}, this);

			this.appeal.on("change", this.onAppealLoaded, this);
			this.appeal.fetch();
		},

		setContentView: function(page, mode, extraOptions) {
			mode = mode || "REVIEW";

			//if (this.page !== page || !this.contentView || (extraOptions && extraOptions.force)) {
			if (this.pageViews[page][mode]) {
				this.page = page;

				if (this.contentView) {
					this.setBreadcrumbsStructure();
					this.contentView.off(null, null, this);
					if (this.contentView.model) {
						this.contentView.model.off(null, null, this.contentView);
					}
					if (this.contentView.cleanUp) {
						this.contentView.cleanUp();
					}
					if (this.contentView.tearDown) {
						this.contentView.tearDown();
					}
				}

				//this.contentView = null;

				this.contentView = new this.pageViews[page][mode](_.extend({
					appealId: this.appealId,
					appeal: this.appeal,
					path: this.options.path,
					referrer: this.options.referrer,
					mode: mode,
					page: this.options.page,
					subId: this.options.subId
				}, extraOptions));

				this.contentView.on("change:printState", this.onPrintStateChange, this);
				this.contentView.on("change:viewState", this.onViewStateChange, this);
				this.contentView.on("change:mainState", this.onMainStateChange, this);

				//this.togglePrintBtn();
				this.cardHeader.hidePrintBtn();
				this.renderPageContent();
			}
			//}
		},

		onPrintStateChange: function() {
			this.togglePrintBtn();
		},

		onViewStateChange: function(event) {
			//console.log('onViewStateChange', event);
			this.setContentView(event.type, event.mode, event.options);
		},

		onMainStateChange: function(event) {
			switch (event.stateName) {
				case "default":
					this.toggleMenu(true);
					break;
				case "documentEditor":
					this.toggleMenu(false);
					break;
			}
		},

		onAppealLoaded: function() {
			var self = this;

			this.appeal.closed = this.appeal.get('closed') ? this.appeal.get('closed') : false;
			//console.log('appeal', this.appeal);
			var patient = this.appeal.get("patient");
			patient.on("change", this.onPatientLoaded, this);
			patient.fetch();

			this.menu = Data.Menu = new App.Views.Menu(this.getMenuStructure());

			this.menu.options.structure.on("change-page", function(step) {
				pubsub.trigger('noty_clear');

				if (this.contentView.persistenceCheck) {
					this.contentView.persistenceCheck(_.bind(function(checkResult) {
						if (checkResult) {
							this.setContentView(step.name);
						}
					}, this));
				} else {
					this.setContentView(step.name);
				}

			}, this);

			this.appeal.off("change", this.onAppealLoaded, this);
		},


		onPatientLoaded: function(patient) {
			this.patient = Cache.Patient = patient;
			this.ready();
			this.setContentView(this.page, this.options.mode);
			this.setBreadcrumbsStructure();

			patient.off("change", this.onPatientLoaded, this);

			window.document.title = this.patient.get("name").get("raw");
		},

		setBreadcrumbsStructure: function() {
			if (this.breadCrumbsMap[this.page]) {
				this.breadcrumbs.setStructure([
					App.Router.cachedBreadcrumbs.PATIENTS,
					App.Router.compile(App.Router.cachedBreadcrumbs.PATIENT, this.appeal.get("patient").toJSON()),
					this.breadCrumbsMap[this.page]
				]);

				this.$("#page-head").html(this.breadcrumbs.render().el);
			}
		},

		ready: function() {
			this.$el.html($.tmpl(this.template, this.appeal.toJSON()));

			this.assign({
				".CardHeader": this.cardHeader
			});

			this.$("#page-head").html(this.breadcrumbs.render().el);

			this.$(".LeftSideBar").html(this.menu.render().el);
		},

		render: function() {
			return this;
		},

		renderPageContent: function() {
			this.menu.setPage(this.page);
			this.contentView.render();
			this.$(".ContentSide").html(this.contentView.el);
		},

		togglePrintBtn: function() {
			if (this.contentView.canPrint) {
				this.cardHeader.showPrintBtn(this.contentView.printOptions());
			} else {
				this.cardHeader.hidePrintBtn();
			}
			//this.$(".CardHeader .CardPrint")[this.contentView.canPrint ? "show" : "hide"]();
		},

		toggleMenu: function(visible) {
			this.menu.$el.parent().toggle( !! visible);
		},

		getMenuStructure: function() {
			var menuStructure = {};
			var self = this;

			this.separateRoles(ROLES.DOCTOR_DEPARTMENT, function() {
				var appealJSON = this.appeal.toJSON();
				menuStructure = {
					structure: [
						App.Router.compile({
							name: "monitoring",
							title: "Основные&nbsp;сведения",
							uri: "/appeals/:id/monitoring"
						}, appealJSON),
						App.Router.compile({
							name: "patient-monitoring",
							title: "Мониторинг&nbsp;состояния",
							uri: "/appeals/:id/patient-monitoring"
						}, appealJSON),
						App.Router.compile({
							name: "documents",
							title: "Документы",
							uri: "/appeals/:id/documents/"
						}, appealJSON),
						App.Router.compile({
							name: "diagnostics-laboratory",
							title: "Лабораторные исследования",
							uri: "/appeals/:id/diagnostics-laboratory/"
						}, appealJSON),
						App.Router.compile({
							name: "diagnostics-instrumental",
							title: "Инструментальные исследования",
							uri: "/appeals/:id/diagnostics-instrumental/"
						}, appealJSON),

						// App.Router.compile({
						// 	name: "diagnostics-consultations",
						// 	title: "Консультации",
						// 	uri: "/appeals/:id/diagnostics-consultations/"
						// }, appealJSON),

						App.Router.compile({
							name: "therapy",
							title: "Лечение",
							uri: "/appeals/:id/therapy"
						}, appealJSON),

						(function() {
							var appeal = self.appeal;
							if (appeal.get('appealType') && appeal.get('appealType').get('finance') && (appeal.get('appealType').get('finance').get('name') === 'ВМП')) {
								return {
									name: "quotes",
									title: "Квоты ВМП",
									uri: "/appeals/" + appeal.get('id') + "/quotes"
								};
							} else {
								return false;
							}
						}()),
						App.Router.compile({
							name: "moves",
							title: "Движение пациента",
							uri: "/appeals/:id/moves"
						}, appealJSON),
						App.Router.compile({
							name: "card",
							title: "Титульный лист ИБ",
							uri: "/appeals/:id/"
						}, appealJSON),
                        App.Router.compile({
                            name: "prescriptions",
                            title: "Назначения",
                            uri: "/appeals/:id/prescriptions/"
                        }, appealJSON),
					]
				}
			}, this);

			this.separateRoles(ROLES.CHIEF, function() {
				var appealJSON = this.appeal.toJSON();
				menuStructure = {
					structure: [
						App.Router.compile({
							name: "monitoring",
							title: "Основные&nbsp;сведения",
							uri: "/appeals/:id/monitoring"
						}, appealJSON),
						App.Router.compile({
							name: "patient-monitoring",
							title: "Мониторинг&nbsp;состояния",
							uri: "/appeals/:id/patient-monitoring"
						}, appealJSON),
						App.Router.compile({
							name: "documents",
							title: "Документы",
							uri: "/appeals/:id/documents/"
						}, appealJSON),
						App.Router.compile({
							name: "diagnostics-laboratory",
							title: "Лабораторные исследования",
							uri: "/appeals/:id/diagnostics-laboratory/"
						}, appealJSON),
						App.Router.compile({
							name: "diagnostics-instrumental",
							title: "Инструментальные исследования",
							uri: "/appeals/:id/diagnostics-instrumental/"
						}, appealJSON),

						// App.Router.compile({
						// 	name: "diagnostics-consultations",
						// 	title: "Консультации",
						// 	uri: "/appeals/:id/diagnostics-consultations/"
						// }, appealJSON),

						App.Router.compile({
							name: "therapy",
							title: "Лечение",
							uri: "/appeals/:id/therapy"
						}, appealJSON),

						(function() {
							var appeal = self.appeal;
							if (appeal.get('appealType') && appeal.get('appealType').get('finance') && (appeal.get('appealType').get('finance').get('name') === 'ВМП')) {
								return {
									name: "quotes",
									title: "Квоты ВМП",
									uri: "/appeals/" + appeal.get('id') + "/quotes"
								};
							} else {
								return false;
							}
						}()),
						App.Router.compile({
							name: "moves",
							title: "Движение пациента",
							uri: "/appeals/:id/moves"
						}, appealJSON),
						App.Router.compile({
							name: "card",
							title: "Титульный лист ИБ",
							uri: "/appeals/:id/"
						}, appealJSON)
					]
				}
			}, this);

			this.separateRoles(ROLES.NURSE_DEPARTMENT, function() {
				var appealJSON = this.appeal.toJSON();
				menuStructure = {
					structure: [
						App.Router.compile({
							name: "monitoring",
							title: "Основные&nbsp;сведения",
							uri: "/appeals/:id/monitoring"
						}, appealJSON),
						App.Router.compile({
							name: "moves",
							title: "Движение пациента",
							uri: "/appeals/:id/moves"
						}, appealJSON),
						App.Router.compile({
							name: "card",
							title: "Титульный лист ИБ",
							uri: "/appeals/:id/"
						}, appealJSON)

					]
				};
			}, this);

			this.separateRoles(ROLES.DOCTOR_RECEPTIONIST, function() {
				var appealJSON = this.appeal.toJSON();
				menuStructure = {
					structure: [
						App.Router.compile({
							name: "card",
							title: "Основное",
							uri: "/appeals/:id/"
						}, appealJSON),
						App.Router.compile({
							name: "examinations",
							title: "Документы",
							uri: "/appeals/:id/examinations/"
						}, appealJSON),
						App.Router.compile({
							name: "diagnostics-laboratory",
							title: "Лабораторные исследования",
							uri: "/appeals/:id/diagnostics-laboratory/"
						}, appealJSON),
						App.Router.compile({
							name: "diagnostics-instrumental",
							title: "Инструментальные исследования",
							uri: "/appeals/:id/diagnostics-instrumental/"
						}, appealJSON),
						// App.Router.compile({
						// 	name: "diagnostics-consultations",
						// 	title: "Консультации",
						// 	uri: "/appeals/:id/diagnostics-consultations/"
						// }, appealJSON),
						App.Router.compile({
							name: "moves",
							title: "Движение пациента",
							uri: "/appeals/:id/moves"
						}, appealJSON)

					]
				};
			}, this);

			this.separateRoles(ROLES.NURSE_RECEPTIONIST, function() {
				var appealJSON = this.appeal.toJSON();
				menuStructure = {
					structure: [App.Router.compile({
							name: "card",
							title: "Основное",
							uri: "/appeals/:id/"
						}, appealJSON),

						App.Router.compile({
							name: "moves",
							title: "Движение по отделениям",
							uri: "/appeals/:id/moves"
						}, appealJSON)
					]
				};
			}, this);

			menuStructure.structure = _.compact(menuStructure.structure);

			return menuStructure;
		}
	});

	return App.Views.Main;
});
