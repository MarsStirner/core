define([
	"text!templates/appeal/edit/pages/examinations.tmpl",
	"collections/examinations",
	"views/grid",
	"views/appeal/edit/pages/examination-primary",
	"views/appeal/edit/pages/examination-primary-preview",
	"views/paginator"
], function (template) {

	App.Views.Examinations = View.extend({
		className: "ContentHolder",
		template: template,

		events: {
			"click #new-exam":"toggleExamTypes",
			"click #exam-type-list li":"createNewExam",
			"click .Actions.ToggleFilters":"toggleFilters"
		},

		/*examTypeViews: {
			"primary": App.Views.ExaminationPrimary,
			"primaryPreview": App.Views.ExaminationPrimaryPreview
		},*/

		initialize: function () {
			var exams = new App.Collections.Examinations();
			exams.appealId = this.options.appealId;

			this.collection = exams;

			this.grid = new App.Views.Grid({
				collection: exams,
				template: "grids/examinations",
				gridTemplateId: "#examination-grid-department",
				rowTemplateId: "#examination-grid-row-department",
				defaultTemplateId: "#examination-grid-department-default"
			});

			this.depended(this.grid);

			this.collection.on("reset", function () {
				if (this.options.appeal.isClosed()) {
					this.grid.$(".EditExam").attr("disabled", true);
				}
			}, this);

			exams.setParams({
				sortingField: "id",
				sortingMethod: "desc"
			});
			exams.fetch();
		},

		render: function () {
			var self = this;

			this.$el.html($.tmpl(this.template, {isClosed: self.options.appeal.isClosed()}));

			this.$("#new-exam").button({icons: {primary: "icon-plus icon-color-green", secondary: "icon-caret-down"}});
			this.$(".ToggleFilters").button({icons: {primary: "icon-filter"}});

			this.$("#lab-grid").html(this.grid.el);

			this.grid.$("tbody tr").live("click", function () {
				$(this).die().off();

				var examId = $(this).find(".ExamIdHolder").data("exam-id");

				self.openExam(examId, "examination-primary-preview", false);

				/*console.log(examId);

				self.trigger("exam:selected", {examId: examId});

				var exam = new App.Dynamic.ExaminationOld({id: examId, appealId: self.options.appealId});
				*//*exam.on("change:id", function () {
					var examView = new self.examTypeViews.primary({model: exam});
					examView.setElement(self.el);
					App.Router.updateUrl("appeals/" + self.options.appealId + "/examinations/primary/" + examId + "/edit");
				}, this);*//*
				$.when(exam.fetch()).done(function () {
					var examPreview = new self.examTypeViews.primaryPreview({model: exam});
					examPreview.setElement(self.el).render();
					*//*var examView = new self.examTypeViews.primary({model: exam});
					examView.setElement(self.el);*//*
					App.Router.updateUrl("appeals/" + self.options.appealId + "/examinations/primary/" + examId);
				});*/
			});

			this.grid.$(".EditExam").live("click", function (event) {
				event.preventDefault();
				event.stopPropagation();

				if (!self.options.appeal.isClosed()) {
					self.grid.$("tbody tr, .EditExam").die().off();

					var examId = $(this).parent().siblings(".ExamIdHolder").data("exam-id");

					self.openExam(examId, "examination-primary", false);
				}

				/*console.log(examId);

				self.trigger("exam:selected", {examId: examId});

				var exam = new App.Dynamic.ExaminationOld({id: examId, appealId: self.options.appealId});
				$.when(exam.fetch()).done(function () {
					var examView = new self.examTypeViews.primary({model: exam});
					examView.setElement(self.el);
					App.Router.updateUrl("appeals/" + self.options.appealId + "/examinations/primary/" + examId + "/edit");
				});*/
			});

			// Пэйджинатор
			this.paginator = new App.Views.Paginator({
					collection: self.collection
			});
			this.depended(this.paginator);

			this.$el.append(this.paginator.render().el);

			this.delegateEvents();
			this.grid.delegateEvents();
			this.paginator.delegateEvents();

			return this;
		},

		openExam: function (exam, mode, isFetched) {
			var self = this;
			mode = mode || "edit";
			exam = isFetched ? exam : new App.Dynamic.ExaminationOld({id: exam, appealId: self.options.appealId});

			var createAndRenderExamView = function (e) {
				self.trigger("change:viewState", {type: mode, options: {model: e}});

				/*self.examView = new self.examTypeViews[mode === "edit" ? "primary" : "primaryPreview"]({model: e, appeal: self.options.appeal});
				self.examView.setElement(self.el).render();

				self.currentExam = e;

				this.canPrint = mode === "preview";

				self.trigger("change:printState");

				App.Router.updateUrl("appeals/" + self.options.appealId + "/examinations/primary/" + exam.get("id") + (mode === "edit" ? "/edit" : ""));*/
			};

			if (isFetched) {
				createAndRenderExamView(exam);
			} else {
				$.when(exam.fetch()).done(function () {
					createAndRenderExamView(exam);
				});
			}
		},

		toggleExamTypes: function (event) {
			this.$(".DDList").toggleClass("Active");
			event.stopPropagation();
		},

		createNewExam: function (event) {
			this.$(".DDList").removeClass("Active");

			var examType = $(event.currentTarget).data("exam-type");
			var structId = $(event.currentTarget).data("exam-struct-id");

			this.trigger("change:viewState", {type: examType, options: {structId: structId}});

			/*if (this.examTypeViews[examType]) {
				var examView = new this.examTypeViews[examType](this.options);
				examView.setElement(this.el);//.render();

				App.Router.updateUrl("appeals/" + this.options.appealId + "/examinations/" + examType + "/new");
			} else {
				console.warn("No such exam type", examType);
			}*/
		},

		toggleFilters: function (event) {
			$(event.currentTarget).toggleClass("Pushed");
			this.$(".Grid thead tr" ).toggleClass("EditTh");
			this.$(".Grid .Filter").toggle();
		}
	});

	return App.Views.Examinations
});