/**
 * User: FKurilov
 * Date: 25.06.12
 */
//попап создания направления на лабисследование
define(function(require) {
	'use strict';

	var popupMixin = require('mixins/PopupMixin');
	var tmpl = require('text!templates/diagnostics/laboratory/direction.tmpl');

	var Analyzes = require('collections/diagnostics/laboratory/Analyzes');
	var AnalyzesTreeView = require('views/diagnostics/laboratory/DirectionAnalyzesTreeView');

	var AnalyzesSelected = require('collections/diagnostics/laboratory/AnalyzesSelected');
	var AnalyzesSelectedView = require('views/diagnostics/laboratory/DirectionAnalyzesSelectedView');

	var PatientDiagnoses = require('views/appeal/edit/pages/monitoring/collections/PatientDiagnoses');

	var MkbInputView = require('views/ui/MkbInputView');
	var PersonDialogView = require('views/ui/PersonDialog');
	var SelectView = require('views/ui/SelectView');


	var DirectionPopup = View.extend({
		template: tmpl,
		className: 'popup',

		events: {
			'click .ShowHidePopup': 'close',
			'click #assigner-outer': 'openAssignerSelectPopup',
			'click #executor-outer': 'openExecutorSelectPopup',
			'click .document-type-filter-orgstruct': 'onDocumentTypeFilterOrgStructToggle',
			'change #start-time': 'validateForm',
			'change #start-date': 'validateForm',
			'keyup #tree-search': 'onSearchKeyup'
		},
		detach_event: function(e_name) {
			delete this.events[e_name];
			this.delegateEvents();
		},
		initialize: function(options) {
			_.bindAll(this);

			var view = this;
			var appealDoctor = this.options.appeal.get('execPerson');
			//назначивший исследование
			if ((Core.Cookies.get('currentRole') === 'nurse-department') || (Core.Cookies.get('currentRole') === 'nurse-receptionist')) {
				//если юзер не врач
				view.assigner = {
					id: appealDoctor.id,
					name: {
						first: appealDoctor.name.first,
						last: appealDoctor.name.last,
						middle: appealDoctor.name.middle
					}
				};

			} else {
				//если юзер врач
				view.assigner = {
					id: Core.Cookies.get('userId'),
					name: {
						first: Core.Cookies.get('doctorFirstName'),
						last: Core.Cookies.get('doctorLastName'),
						middle: ''
					}
				};
			}

			//исполнитель
			view.executor = {
				id: '',
				name: {
					first: '',
					last: '',
					middle: ''
				}
			};


			view.appeal = view.options.appeal;

			view.appealDiagnosis = new PatientDiagnoses(null, {
				appealId: view.appeal.get('id')
			});

			//диагнозы из госпитализации
			//view.appealDiagnosis = view.appeal.getDiagnosis();


			// коллекция с деревом лаб.исследований
			view.analyzes = new Analyzes();

			view.analyzes.setParams({
				'filter[view]': 'tree',
				sortingField: 'name',
				sortingMethod: 'asc'
			});

			// вью для коллекции с деревом лаб.исследований
			view.analyzesTreeView = new AnalyzesTreeView({
				collection: view.analyzes,
				patientId: view.options.appeal.get('patient').get('id')
			});
			view.analyzes.on('reset', function() {
                console.log('reset tree collection', view.analyzes);
                $('#tree-search').prop('disabled', false).removeClass('Disabled');
			});


			view.depended(view.analyzesTreeView);

			//коллекция с выбранными лаб.исследованиями
			view.analyzesSelected = new AnalyzesSelected([], {
				appeal: view.options.appeal
			});

			view.analyzesSelected.on('updateAll:success', function() {
				pubsub.trigger('lab-diagnostic:added');
				view.close();
			});

			view.analyzesSelected.on('reset add remove', this.executorInputState, this);

			view.analyzesSelected.on('all', function() {
				view.validateForm();
			});

			view.analyzesSelected.on('updateAll:error', function(response) {
				pubsub.trigger('noty', {
					text: response.errorMessage ? response.errorMessage : 'Ошибка при создании направления',
					type: 'error'
				});
			});

			//вью для коллекции с выбранными лаб.исследованиями
			view.analyzesSelectedView = new AnalyzesSelectedView({
				collection: view.analyzesSelected,
				patientId: view.options.appeal.get('patient').get('id')
			});

			view.depended(view.analyzesSelectedView);

			//инпут классификатора диагнозов
			view.mkbInputView = new MkbInputView();
			view.depended(view.mkbInputView);

			//изменение назначившего исследование
			pubsub.on('assigner:changed', function(assigner) {
				//console.log('assigner:changed', assigner)
				view.assigner = assigner;
				view.ui.$assigner.val(assigner.name.last + ' ' + assigner.name.first + ' ' + assigner.name.middle);
			});

			//изменение исполнителя исследования
			pubsub.on('executor:changed', function(executor) {
				console.log('executor:changed', executor, view.analyzesSelected)
				if (view.analyzesSelected.length === 1) {
					view.executor = executor;
					view.ui.$executor.val(executor.name.last + ' ' + executor.name.first + ' ' + executor.name.middle);
				}

			});

		},
		onSearchKeyup: function(event) {
			var $target = $(event.currentTarget);

			this.analyzes.search($target.val());
		},

		onDocumentTypeFilterOrgStructToggle: function(event) {
			if ($(event.target).attr('checked')){
                this.analyzes.setOrgStructFilter('1');
            } else {
                this.analyzes.setOrgStructFilter('0');
            }
		},

		validateForm: function() {
			var errors = this.isValid();
			this.saveButton(!errors.length);
			this.showErrors(errors);
		},

		showErrors: function(errors) {
			var self = this;
			self.ui.$errors.html('').hide();
			if (errors) {
				_.each(errors, function(error) {
					self.ui.$errors.append(error.message);
				});
				self.ui.$errors.show();
			}
		},

		isValid: function() {
			var view = this;
			var errors = [];
			var valid = true;

			var assessmentDate = view.ui.$assessmentDatepicker.datepicker('getDate');
			assessmentDate = assessmentDate ? assessmentDate : new Date();
			var assessmentDate = moment(assessmentDate).format('YYYY-MM-DD');

			var assessmentTime = view.ui.$assessmentTimepicker.val();
			var assessmentDatetime = assessmentDate + ' ' + assessmentTime + ':00';

			if (!assessmentTime) {
				errors.push({
					message: 'Не задано время создания направления. '
				});
			}

			if (!moment(assessmentDatetime, 'YYYY-MM-DD HH:mm:ss').isValid()) {
				errors.push({
					message: 'Неверный формат даты создания направления. '
				});
			}


			if (assessmentTime
				&& moment(assessmentDatetime, 'YYYY-MM-DD HH:mm:ss').isValid()
				&& (moment(assessmentDatetime, 'YYYY-MM-DD HH:mm:ss').startOf('day').diff(moment().startOf('day'),'days') < 0)) {
				errors.push({
					message: 'Дата создания не может быть меньше текущей даты. '
				});
			}

			if (view.analyzesSelected.length === 0) {
				errors.push({
					message: 'Не выбрано исследование. '
				});
			}

			view.analyzesSelected.each(function(analysis) {
				var plannedEndDate = analysis.getProperty('plannedEndDate', 'value');
				//console.log('diff',moment(plannedEndDate, 'YYYY-MM-DD HH:mm:ss').diff(moment()), moment(assessmentDatetime, 'YYYY-MM-DD HH:mm:ss').diff(moment()))

				if (!plannedEndDate || !moment(plannedEndDate, 'YYYY-MM-DD HH:mm:ss').isValid()) {
					errors.push({
						message: 'Неверный формат планируемой даты выполнения направления. '
					});
				}

				if (plannedEndDate && moment(plannedEndDate, 'YYYY-MM-DD HH:mm:ss').isValid() && !(moment(plannedEndDate, 'YYYY-MM-DD HH:mm:ss').diff(moment()) > -(60 * 1000))) {
					errors.push({
						message: 'Планируемая дата и время выполнения не могут быть меньше текущей даты и времени. '
					});
				}



			});

			return errors;
		},


		//открывает попап для выбора направившего врача
		openAssignerSelectPopup: function() {
			this.personDialogView = new PersonDialogView({
				appeal: this.options.appeal,
				title: 'Направивший врач',
				callback: function(person) {
					pubsub.trigger('assigner:changed', person);
				}
			});

			this.personDialogView.render().open();
		},

		//открывает попап для выбора исполнителя
		openExecutorSelectPopup: function() {
			this.personDialogView = new PersonDialogView({
				appeal: this.options.appeal,
				title: 'Исполнитель',
				callback: function(person) {
					pubsub.trigger('executor:changed', person);
				}
			});

			this.personDialogView.render().open();
		},

		executorInputState: function() {
			if (this.analyzesSelected.length > 1) {
				this.detach_event('click #executor-outer');
				this.ui.$executor.val('');
				this.$('.change-executor').button('disable');
				this.executor = {
					id: '',
					name: {
						first: '',
						last: '',
						middle: ''
					}
				};

			} else {
				this.events['click #executor-outer'] = 'openExecutorSelectPopup';
				this.delegateEvents();
				this.$('.change-executor').button('enable');
			}
		},

		//селект выбора типа финансирования
		initFinanseSelect: function() {
			var view = this;
			var appealFinanceId = view.options.appeal.get('appealType').get('finance').get('id');

			var financeDictionary = new App.Collections.DictionaryValues([], {
				name: 'finance'
			});

			view.financeSelect = new SelectView({
				collection: financeDictionary,
				el: view.$('#finance'),
				initSelection: appealFinanceId
			});

			view.depended(view.financeSelect);
		},


		saveButton: function(enabled, msg) {
			var $saveButton = this.$el.closest('.ui-dialog').find('.save');
            $saveButton.button();

			if (enabled) {
				$saveButton.button('enable');
			} else {
				$saveButton.button('disable');
			}
			if (msg) {
				$saveButton.button('option', 'label', msg);
			} else {
				$saveButton.button('option', 'label', 'Сохранить');
			}

		},

		//создание выбранных исследований
		onSave: function() {
			var view = this;

			var startDate = moment(view.ui.$assessmentDatepicker.datepicker('getDate')).format('YYYY-MM-DD');
			var startTime = view.ui.$assessmentTimepicker.val() + ':00';

			view.analyzesSelected.forEach(function(model) {
				var id = model.get('id');

				model.setProperty('assessmentDate', 'value', startDate + ' ' + startTime);

				if (view.analyzesSelected.length === 1) {
					model.setProperty('executorId', 'value', view.executor.id);
				}


				model.setProperty('assignerId', 'value', view.assigner.id);

				model.setProperty('finance', 'value', view.ui.$finance.val());

				var mkbId = view.$('input[name="diagnosis[mkb][code]"]').data('mkb-id');

				model.setProperty('Направительный диагноз', 'valueId', mkbId);

				if (!mkbId) {
					model.setProperty('Направительный диагноз', 'value', '');
				}


			});

			view.saveButton(false, 'Сохраняем...');
			view.analyzesSelected.updateAll();

		},


		renderNested: function(view, selector) {
			var $element = (selector instanceof $) ? selector : this.$el.find(selector);
			view.setElement($element).render();
		},

		render: function() {
			var view = this;

			view.$el.html(_.template(this.template, {
				executor: this.executor,
				assigner: this.assigner
			}));

			view.renderNested(view.mkbInputView, '.mkb');

			view.ui = {};
			view.ui.$assessmentDatepicker = this.$('#start-date');
			view.ui.$assessmentTimepicker = this.$('#start-time');
			view.ui.$mbkCode = view.$('input[name="diagnosis[mkb][code]"]');
			view.ui.$mbkDiagnosis = view.$('input[name="diagnosis[mkb][diagnosis]"]');
			view.ui.$finance = view.$('#finance');
			view.ui.$assigner = view.$('#assigner');
			view.ui.$executor = view.$('#executor');
			view.ui.$errors = view.$('#errors');

			this.$('.change-assigner,.change-executor').button();



			//селект вида оплаты
			view.initFinanseSelect();

			view.analyzesTreeView.setElement(this.$el.find('.groups'));

			view.analyzesSelectedView.setElement(this.$el.find('.group-tests'));
            console.log('render')
			pubsub.trigger('lab:click');


			if (!Core.Cookies.get('userDepartmentId')) {
				this.$el.find('.document-type-filter-orgstruct').attr('disabled', 'disabled').removeAttr('checked');
			}

			view.appealDiagnosis.fetch().done(function() {
				//установка диагноза
				// console.log('view.appealDiagnosis',view.appealDiagnosis.first())
				if ((view.appealDiagnosis.length > 0) && view.appealDiagnosis.first()) {
					var diagnosis = view.appealDiagnosis.first().get('mkb');
					view.ui.$mbkDiagnosis.val(diagnosis.diagnosis);
					view.ui.$mbkCode.val(diagnosis.code);
					view.ui.$mbkCode.data('mkb-id', diagnosis.id);
				}

			});



			//Дата и время создания
			var now = new Date();

			view.ui.$assessmentDatepicker.mask('99.99.9999').datepicker({
				minDate: now,
				onSelect: function(dateText, inst) {
					$(this).change();
				}
			}).datepicker('setDate', now);

			view.ui.$assessmentDatepicker.next('.icon-calendar').on('click', function() {
				view.ui.$assessmentDatepicker.datepicker('show');
			})


			view.ui.$assessmentTimepicker.mask('99:99').timepicker({
				defaultTime: 'now',
				showPeriodLabels: false,
				showOn: 'both'
			}).timepicker('setTime', now);

			this.$el.closest('.ui-dialog').find('.save');

			return view;
		},

		//закрытие попапа
		close: function() {
			var view = this;
            console.log('close direction popap', view.$el.dialog())
			view.$el.dialog('close');
			view.$el.remove();
			view.remove();
            view.analyzes.off();
			view.analyzesTreeView.close();
			view.analyzesSelectedView.close();

			view.mkbInputView.close();
			view.financeSelect.close();

			pubsub.off('executor:changed');
			pubsub.off('assigner:changed');

		}

	}).mixin([popupMixin]);

	return DirectionPopup;
});
