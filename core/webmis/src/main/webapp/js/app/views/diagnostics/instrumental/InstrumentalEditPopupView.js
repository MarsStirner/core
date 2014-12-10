define(function(require) {
	'use strict';
	var tmpl = require('text!templates/diagnostics/instrumental/instrumental-edit-popup.tmpl');
	var popupMixin = require('mixins/PopupMixin');

	var BFView = require('views/diagnostics/instrumental/InstrumentalPopupBottomFormView');
	var PersonDialogView = require('views/ui/PersonDialog');
	var ViewModel = require('views/diagnostics/instrumental/InstrumentalEditPopupViewModel');

	//require('models/DeepModel');


	return View.extend({
		template: tmpl,
		events: {
			'click #assigner-outer': 'openAssignerSelectPopup',
			'click #executor-outer': 'openExecutorSelectPopup',
			'change input[name=urgent]': 'onChangeUrgentInput',
			'change #finance': 'onChangeFinanceInput',
			'change #plannedTime': 'onChangePlannedTimePicker',
			'change #plannedDate': 'onChangePlannedDatePicker',
			'change input[name="diagnosis[mkb][code]"]': 'onChangeMkbInput'
		},

		initialize: function(options) {
			_.bindAll(this);

			this.viewModel = new ViewModel({}, {
				appeal: this.options.appeal,
				model: this.options.model
			});

			this.viewModel.on('change', this.validateForm, this);

			this.model = this.options.model;
			this.data = this.model.toJSON();

			this.bfView = new BFView({
				data: this.viewModel.toJSON()
			});


			this.depended(this.bfView);

			pubsub.on('assigner:changed', this.onChangeAssigner, this);
			pubsub.on('executor:changed', this.onChangeExecutor, this);

		},

		openAssignerSelectPopup: function() {
			console.log('openAssignerSelectPopup');
			this.personDialogView = new PersonDialogView({
				title: 'Направивший врач',
				appeal: this.options.appeal,
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

		onChangeAssigner: function(assigner) {
			this.viewModel.set({
				'assignerId': assigner.id,
				'assignerFirstName': assigner.name.first,
				'assignerMiddleName': assigner.name.middle,
				'assignerLastName': assigner.name.last
			});

			this.ui.$assigner.val(assigner.name.last + ' ' + assigner.name.first + ' ' + assigner.name.middle);
		},

		onChangeExecutor: function(executor) {
			this.viewModel.set({
				'executorId': executor.id,
				'executorFirstName': executor.name.first,
				'executorMiddleName': executor.name.middle,
				'executorLastName': executor.name.last
			});

			this.ui.$executor.val(executor.name.last + ' ' + executor.name.first + ' ' + executor.name.middle);
		},

		onChangeUrgentInput: function() {
			var urgent = this.ui.$urgent.prop('checked');
			this.viewModel.set('urgent', urgent);
		},

		onChangeFinanceInput: function() {
			var financeId = this.$(this.ui.$finance.find('option:selected')[0]).val();
			this.viewModel.set('finance', financeId);
		},

		onChangeMkbInput: function() {
			var mkbId = this.ui.$mkbCode.data('mkb-id');
			this.viewModel.set('mkbId', mkbId);
		},

		onChangePlannedTimePicker: function() {
			var plannedTime = this.ui.$plannedTime.val();
			this.viewModel.set('plannedTime', plannedTime);
		},

		onChangePlannedDatePicker: function() {
			var plannedDate = this.ui.$plannedDate.datepicker('getDate');
			this.viewModel.set('plannedDay', plannedDate);
		},

		validateForm: function() {
			var errors = this.viewModel.validateModel(this.viewModel.toJSON());

			this.showErrors(errors);
			this.saveButton(!errors);

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

		saveButton: function(enabled, msg) {
			var $saveButton = this.ui.$saveButton;
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

		render: function() {
			this.renderNested(this.bfView, ".bottom-form");

			return this;
		},
		afterRender: function() {
			var view = this;
			view.ui = {};
			view.ui.$createDate = view.$('#createDate');
			view.ui.$createTime = view.$('#createTime');
			view.ui.$plannedDate = view.$('#plannedDate');
			view.ui.$plannedTime = view.$('#plannedTime');
			view.ui.$finance = view.$('#finance');
			view.ui.$urgent = view.$('input[name=urgent]');
			view.ui.$mkbDiagnosis = view.$('input[name="diagnosis[mkb][diagnosis]"]');
			view.ui.$mkbCode = view.$('input[name="diagnosis[mkb][code]"]');
			view.ui.$assigner = view.$('input[name=assigner]');
			view.ui.$executor = view.$('input[name=executor]');
			view.$('.change-assigner,.change-executor').button();
			view.ui.$saveButton = view.$el.closest('.ui-dialog').find('.save');
			view.ui.$errors = view.$('#errors');

			view.ui.$createDate
				.val(moment(view.viewModel.get('createDay')).format('DD.MM.YYYY'))
				.addClass('Disabled')
				.prop('disabled', true);


			view.ui.$createTime
				.val(view.viewModel.get('createTime'))
				.addClass('Disabled')
				.prop('disabled', true);

			view.ui.$plannedDate.datepicker({
				minDate: new Date(),
				onSelect: function() {
					view.ui.$plannedDate.trigger('change');
					var day = moment(view.$(this).datepicker('getDate')).startOf('day');
					var currentDay = moment().startOf('day');
					var currentHour = moment().hour();
					var hour = view.ui.$plannedTime.timepicker('getHour');
					//если выбрана текущая дата и время в таймпикере меньше текущего, то сбрасываем таймпикер
					if (day.diff(currentDay, 'days') === 0) {
						if (hour <= currentHour) {
							view.ui.$plannedTime.val('').trigger('change');
						}
					}
				}
			}).datepicker('setDate', view.viewModel.get('plannedDay'));

			view.ui.$plannedTime.timepicker({
				showPeriodLabels: false,
				showOn: 'both',
				button: '.timepicker .icon-time',
				defaultTime: 'now',
				onHourShow: function(hour) {
					var day = moment(view.ui.$plannedDate.datepicker('getDate')).startOf('day');
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
					var day = moment(view.ui.$plannedDate.datepicker('getDate')).startOf('day');
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
			}).val(this.viewModel.get('plannedTime'));

			if (this.viewModel.get('urgent') == 'true') {
				view.ui.$urgent.prop('checked', true);
			}

			view.ui.$mkbDiagnosis.val(view.viewModel.get('mkbText'));
			view.ui.$mkbCode.val(view.viewModel.get('mkbCode'));
			view.ui.$mkbCode.data('mkb-id', view.viewModel.get('mkbId'));

			//console.log('finance',view.viewModel.get('finance'));
			view.ui.$finance.select2('val', parseInt(view.viewModel.get('finance')));


		},

		onSave: function() {
			var view = this;

			view.model.setProperty('executorId', 'value', view.viewModel.get('executorId'));
			//doctorFirstName - имя врача назначившего исследование
			view.model.setProperty('doctorFirstName', 'value', view.viewModel.get('executorFirstName'));
			//doctorMiddleName - отчество врача назначившего исследование
			view.model.setProperty('doctorMiddleName', 'value', view.viewModel.get('executorMiddleName'));
			//doctorLastName - фамилия врача назначившего исследование
			view.model.setProperty('doctorLastName', 'value', view.viewModel.get('executorLastName'));


			view.model.setProperty('assignerId', 'value', view.viewModel.get('assignerId'));
			//assignerFirstName - имя врача назначившего исследование
			view.model.setProperty('assignerFirstName', 'value', view.viewModel.get('assignerFirstName'));
			//assignerMiddleName - отчество врача назначившего исследование
			view.model.setProperty('assignerMiddleName', 'value', view.viewModel.get('assignerMiddleName'));
			//assignerLastName - фамилия врача назначившего исследование
			view.model.setProperty('assignerLastName', 'value', view.viewModel.get('assignerLastName'));

			//plannedEndDate - планируемая дата выполнения иследования
			view.model.setProperty('plannedEndDate', 'value', view.viewModel.get('plannedDatetime'));

			//finance - идентификатор типа оплаты
			view.model.setProperty('finance', 'value', view.viewModel.get('finance'));

			//urgent - срочность
			view.model.setProperty('urgent', 'value', view.viewModel.get('urgent'));

			//идентификатор направительного диагноза
			view.model.setProperty('Направительный диагноз', 'valueId', view.viewModel.get('mkbId'));
			if (!view.viewModel.get('mkbId')) {
				view.model.setProperty('Направительный диагноз', 'value', '');
			}

			this.saveButton(false, 'Сохраняем');
			view.model.save({}, {
				success: function() {
					view.close();
					pubsub.trigger('instrumental-diagnostic:added');
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

			this.ui.$plannedDate.datepicker('destroy');
			this.$el.dialog('close');
			this.bfView.close();
			this.$el.remove();
			this.remove();
		}
	}).mixin([popupMixin]);


});
