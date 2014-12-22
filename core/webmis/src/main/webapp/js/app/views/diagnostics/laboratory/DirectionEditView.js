/**
 * User: FKurilov
 * Date: 25.06.12
 */

//попап редактирования направления на лабисследование

define(function(require) {
	'use strict';

	var tmpl = require('text!templates/diagnostics/laboratory/direction-edit.tmpl');
	var popupMixin = require('mixins/PopupMixin');
	var SelectView = require('views/ui/SelectView');
	var test4EditTmpl = require('text!templates/diagnostics/laboratory/node-test4edit.html');
	var MkbInputView = require('views/ui/MkbInputView');
	var PersonDialogView = require('views/ui/PersonDialog');

	return View.extend({
		template: tmpl,
		events: {
			'click #assigner-outer': 'openAssignerSelectPopup',
			'click #executor-outer': 'openExecutorSelectPopup',
			'change .select_date': 'onChangePlannedEndDate',
			'change .select_time': 'onChangePlannedEndDate',
			'change .cito': 'onChangeCito',
			'change .test-select': 'onSelectTest',
			'click .icon': 'onClickArrow',
			'click .title2': 'onClickTitle'
		},
		initialize: function() {
			_.bindAll(this);

			var view = this;


			view.appeal = view.options.appeal;
			view.diagnosis = view.appeal.getDiagnosis();


			view.model = this.options.model;
			view.model.eventId = view.options.appeal.get('id');
			view.model.on('change:plannedEndDate', this.validateForm, this);

			view.assigner = view.getAssigner();
			view.executor = view.getExecutor();

			//инпут классификатора диагнозов
			view.mkbInputView = new MkbInputView();
			view.depended(view.mkbInputView);

			pubsub.on('assigner:changed', function(assigner) {
				//console.log('assigner:changed', arguments, view)
				view.assigner = assigner;
				view.ui.$assigner.val(assigner.name.raw);

			});

			pubsub.on('executor:changed', function(executor) {
				//console.log('executor:changed', arguments, view)
				view.executor = executor;
				view.ui.$executor.val(executor.name.raw);

			});

		},

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

		onSelectTest: function(event) {
			var $target = $(event.target);
			var name = $target.val();
			var value = $target.prop('checked');

			this.model.setProperty(name, 'isAssigned', '' + value);
		},

		onChangeCito: function() {
			var view = this;
			var value = this.ui.$cito.prop('checked');

			view.model.setProperty('urgent', 'value', '' + value);

		},

		onChangePlannedEndDate: function() {
			var view = this;
			var rawDate = this.ui.$plannedDatepicker.val();
			var rawTime = this.ui.$plannedTimepicker.val();

			var date = moment(rawDate, 'DD.MM.YYYY').format('YYYY-MM-DD');
			var time = rawTime + ':00';

			view.model.setProperty('plannedEndDate', 'value', date + ' ' + time);

			//console.log('onChangePlannedEndDate', date + ' ' + time, view.model);

		},

		onClickTitle: function() {
			var closed = this.$el.find('.icons').hasClass('closed');
			var open = this.$el.find('.icons').hasClass('open');

			if (closed || open) {
				if (closed) {
					this.expand();
				} else {
					this.collapse();
				}
			}

		},

		onClickArrow: function(e) {
			var $target = $(e.target);

			if ($target.hasClass('icon-open')) {
				this.collapse();
			} else {
				this.expand();
			}

		},

		triggerIcons: function(select) {
			if (select) {
				this.ui.$icons.removeClass('closed').addClass('open');
			} else {
				this.ui.$icons.removeClass('open').addClass('closed');
			}
		},

		triggerTestsList: function(select) {
			if (select) {
				this.ui.$tests.show();
			} else {
				this.ui.$tests.hide();
			}
		},

		expand: function() {
			this.triggerTestsList(true);
			this.triggerIcons(true);
		},

		collapse: function() {
			this.triggerTestsList(false);
			this.triggerIcons(false);
		},


		initFinanseSelect: function(elSelector) {
			var view = this;
			var appealFinanceId = view.options.appeal.get('appealType').get('finance').get('id');

			view.financeDictionary = new App.Collections.DictionaryValues([], {
				name: 'finance'
			});

			var modelFinanceId = view.model.getProperty('finance');

			view.financeSelect = new SelectView({
				collection: view.financeDictionary,
				el: view.$(elSelector),
				initSelection: modelFinanceId ? modelFinanceId : appealFinanceId
			});

			view.depended(view.financeSelect);

		},

		getAssigner: function() {
			var view = this;

			var assignerLastName = view.model.getProperty('assignerLastName');
			var assignerFirstName = view.model.getProperty('assignerFirstName');
			var assignerMiddleName = view.model.getProperty('assignerMiddleName');
			var assignerId = view.model.getProperty('assignerId');

			return {
				id: assignerId ? assignerId : Core.Cookies.get('userId'),
				name: {
					first: assignerFirstName ? assignerFirstName : Core.Cookies.get('doctorFirstName'),
					last: assignerLastName ? assignerLastName : Core.Cookies.get('doctorLastName'),
					middle: assignerMiddleName ? assignerMiddleName : ''
				}
			};

		},

		getExecutor: function() {
			var view = this;

			var executorLastName = view.model.getProperty('doctorLastName');
			var executorFirstName = view.model.getProperty('doctorFirstName');
			var executorMiddleName = view.model.getProperty('doctorMiddleName');
			var executorId = view.model.getProperty('executorId');

			return {
				id: executorId ? executorId : '',
				name: {
					first: executorFirstName ? executorFirstName : '',
					last: executorLastName ? executorLastName : '',
					middle: executorMiddleName ? executorMiddleName : ''
				}
			};

		},


		data: function() {
			var view = this;
			var data = {};

			data.id = view.model.get('id');
			data.title = view.model.get('name');

			var plannedEndDate = moment(view.model.getProperty('plannedEndDate'), 'YYYY-MM-DD HH:mm:ss'); //2013-03-30 07:00:00
			data.plannedDate = moment(plannedEndDate).format('DD.MM.YYYY');
			data.plannedTime = moment(plannedEndDate).format('HH:mm');

			data.cito = (view.model.getProperty('urgent') === 'true') ? true : false;

			data.tests = [];

			var attributes = view.model.get('group')[1].attribute;
			var stringAttributes = _.filter(attributes, function(attr) {
				return attr.type == 'String';
			});

			data.tests = _.map(stringAttributes, function(attr) {
				return {
					title: attr.name,
					select: view.model.getProperty(attr.name, 'isAssigned') == 'true' ? true : false
				};
			});

			return data;
		},

		renderNested: function(view, selector) {
			var $element = (selector instanceof $) ? selector : this.$el.find(selector);
			view.setElement($element).render();
		},

		render: function() {
			var view = this;

			view.$el.html(_.template(view.template, {
				assigner: view.assigner,
				executor: view.executor,
				analysis: view.data()
			}));

			view.renderNested(view.mkbInputView, '.mkb');

			view.ui = {};
			view.ui.$mkbDiagnosis = view.$('input[name="diagnosis[mkb][diagnosis]"]');
			view.ui.$mkbCode = view.$('input[name="diagnosis[mkb][code]"]');
			view.ui.$startDate = view.$('#start-date');
			view.ui.$startTime = view.$('#start-time');
			view.ui.$finance = view.$('#finance');
			view.ui.$assigner = view.$('#assigner');
			view.ui.$executor = view.$('#executor');

			view.ui.$plannedDatepicker = view.$el.find('.select_date');
			view.ui.$plannedTimepicker = view.$el.find('.select_time');
			view.ui.$cito = view.$el.find('.cito');
			view.ui.$tests = view.$el.find('.tests');
			view.ui.$icons = view.$el.find('.icons');
			view.ui.$errors = view.$('#errors');

			this.$('.change-assigner,.change-executor').button();


			view.ui.$plannedDatepicker.datepicker({
				minDate: new Date(),
				onSelect: function() {
					var day = moment(view.$(this).datepicker('getDate')).startOf('day');
					var currentDay = moment().startOf('day');
					var currentHour = moment().hour();
					var hour = view.ui.$plannedTimepicker.timepicker('getHour');
					//если выбрана текущая дата и время в таймпикере меньше текущего, то сбрасываем таймпикер
					if (day.diff(currentDay, 'days') === 0) {
						if (hour <= currentHour) {
							view.ui.$plannedTimepicker.val('').trigger('change');
						}
					}

					$(this).change();
				}
			});

			view.ui.$plannedDatepicker.next('.icon-calendar').on('click', function() {
				view.ui.$plannedDatepicker.datepicker('show');
			});

			view.ui.$plannedTimepicker.timepicker({
				defaultTime: 'now',
				onHourShow: function(hour) {
					var day = moment(view.ui.$plannedDatepicker.datepicker('getDate')).startOf('day');
					var currentDay = moment().startOf('day');
					var currentHour = moment().hour();
					//если выбран текущий день, то часы меньше текущего нельзя выбрать
					if (day.diff(currentDay, 'days') === 0) {
						if (hour < currentHour) {
							return false;
						}
					}

					return true;
				},
				onMinuteShow: function(hour, minute) {
					var day = moment(view.ui.$plannedDatepicker.datepicker('getDate')).startOf('day');
					var currentDay = moment().startOf('day');
					var currentHour = moment().hour();
					var currentMinute = moment().minute();
					//если выбран текущий день и час, то минуты меньше текущего времени нельзя выбрать
					if (day.diff(currentDay, 'days') === 0) {
						if (hour === currentHour && minute <= currentMinute) {
							return false;
						}
					}
					return true;
				},
				showPeriodLabels: false,
				showOn: 'both'
			});
			//Вид оплаты
			view.initFinanseSelect('#finance');



			//Диагноз
			var diagnosis = view.model.getProperty('Направительный диагноз', 'value');
			var diagnosisId = view.model.getProperty('Направительный диагноз', 'valueId');
			if (diagnosis) {
				//console.log('diagnosis',diagnosis);
				diagnosis = diagnosis.split(/\s+/);
				var diagnosisCode = diagnosis[0];
				var diagnosisText = (diagnosis.splice(1)).join(' ');
				view.ui.$mkbDiagnosis.val(diagnosisText);
				view.ui.$mkbCode.val(diagnosisCode);
				view.ui.$mkbCode.data('mkb-id', diagnosisId);
			}


			//Дата и время создания
			var assessmentBeginDate = view.model.getProperty('assessmentBeginDate');
			var date = new Date(assessmentBeginDate);

			view.ui.$startDate.addClass('Disabled')
				.val(moment(date).format('DD.MM.YYYY')).prop('disabled', true);

			view.ui.$startTime.addClass('Disabled').prop('disabled', true).val(moment(date).format('HH:mm'));



			return view;
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
			var errors = [];
			var plannedEndDate = this.model.getProperty('plannedEndDate', 'value');

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

			return errors;
		},

		onSave: function() {
			var view = this;

			var startDate = moment(view.ui.$startDate.val(), 'DD.MM.YYYY').format('YYYY-MM-DD');
			var startTime = view.ui.$startTime.val() + ':00';

			view.model.setProperty('doctorFirstName', 'value', view.executor.name.first);
			view.model.setProperty('doctorLastName', 'value', view.executor.name.last);
			view.model.setProperty('doctorMiddleName', 'value', view.executor.name.middle);
			view.model.setProperty('executorId', 'value', view.executor.id);

			view.model.setProperty('assignerFirstName', 'value', view.assigner.name.first);
			view.model.setProperty('assignerLastName', 'value', view.assigner.name.last);
			view.model.setProperty('assignerMiddleName', 'value', view.assigner.name.middle);
			view.model.setProperty('assignerId', 'value', view.assigner.id);

			view.model.setProperty('assessmentBeginDate', 'value', startDate + ' ' + startTime);
			view.model.setProperty('finance', 'value', view.ui.$finance.val());
			var mkbId = view.ui.$mkbCode.data('mkb-id');

			view.model.setProperty('Направительный диагноз', 'valueId', mkbId);

			if (!mkbId) {
				view.model.setProperty('Направительный диагноз', 'value', '');
			}


			view.model.save({}, {
				success: function() {
					pubsub.trigger('lab-diagnostic:added');
					//pubsub.trigger('noty', {text:'Направление обновлено'});
					view.close();
				},
				error: function(a, b, c) {
					var response = JSON.parse(b.responseText);
					var errorMessage = response.errorMessage ? response.errorMessage : 'Ошибка при попытке сохранить направление';

					pubsub.trigger('noty', {
						text: errorMessage,
						type: 'error'
					});

				}
			});



		},

		close: function() {
			this.$el.dialog('close');
			this.$el.remove();
			this.remove();
			pubsub.off('assigner:changed');
			pubsub.off('executor:changed');

			this.mkbInputView.close();
			this.financeSelect.close();


		}

	}).mixin([popupMixin]);

});
