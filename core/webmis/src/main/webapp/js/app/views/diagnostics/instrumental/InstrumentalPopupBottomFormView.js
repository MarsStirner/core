define(function(require) {
	var tmpl = require('text!templates/diagnostics/instrumental/instrumental-bottom-partial.tmpl');
	var MKBView = require('views/ui/MkbInputView');
	var SelectView = require('views/ui/SelectView');

	var rivets = require('rivets');

	var InstrumentalPopupBottomForm = View.extend({
		template: tmpl,
		initialize: function(options) {

			this.data = this.options.data;

			this.mkb = new MKBView();
			this.depended(this.mkb);
		},

		initFinanseSelect: function() {
			var view = this;

			var financeDictionary = new App.Collections.DictionaryValues([], {
				name: 'finance'
			});

			//financeDictionary.fetch();

			view.financeSelect = new SelectView({
				collection: financeDictionary,
				el: view.$('#finance'),
				initSelection: view.data.finance
			});

			view.depended(view.financeSelect);
		},

		renderNested: function(view, selector) {
			var $element = (selector instanceof $) ? selector : this.$el.find(selector);
			view.setElement($element).render();
		},
		close: function() {
			this.$el.remove();
			this.mkb.close();

		},
		render: function() {
			this.$el.html($.tmpl(this.template, this.data));

			//rivets.bind(this.el, {test: this.options.model});
			this.renderNested(this.mkb, ".mkb");

			if (this.data.mkbId) {
				//var mkb = this.appealDiagnosis.get('mkb');
				this.$("input[name='diagnosis[mkb][diagnosis]']").val(this.data.mkbDiagnosis);
				this.$("input[name='diagnosis[mkb][code]']").val(this.data.mkbCode);
				this.$("input[name='diagnosis[mkb][code]']").data('mkb-id', this.data.mkbId);
			}

			this.initFinanseSelect();

			// this.ui = {};
			// this.ui.$datepicker = this.$el.find("#start-date");
			// this.ui.$timepicker = this.$el.find("#start-time");

			// //UIInitialize(this.$el);
			// //Дата и время создания
			// var now = new Date();

			// //this.ui.$datepicker.datepicker().datepicker("setDate", this.data.assessmentDate);

			// this.ui.$timepicker.val(this.data.assessmentTime).mask("99:99").timepicker({
			// 	showPeriodLabels: false
			// });




			return this;
		}

	});

	return InstrumentalPopupBottomForm;
});
