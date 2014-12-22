/**
 * User: FKurilov
 * Date: 25.06.12
 */

define(function (require) {
    'use strict';

    var popupMixin = require('mixins/PopupMixin');

    var InstrumentalResearchs = require('collections/diagnostics/instrumental/InstrumentalResearchs');
    var InstrumentalResearchTemplate = require('models/diagnostics/instrumental/InstrumentalResearchTemplate');
    var InstrumntalGroups = require('collections/diagnostics/instrumental/InstrumntalGroups');

    var BFView = require('views/diagnostics/instrumental/InstrumentalPopupBottomFormView');
    var PersonDialogView = require('views/ui/PersonDialog');
    var ResearchGroupsListView = require('views/diagnostics/instrumental/ResearchGroupsListView');
    var ResearchListView = require('views/diagnostics/instrumental/ResearchsListView');
    var ViewModel = require('views/diagnostics/instrumental/InstrumentalPopupViewModel');

    var popupTmpl = require('text!templates/diagnostics/instrumental/instrumental-popup.tmpl');
    var PatientDiagnoses = require('views/appeal/edit/pages/monitoring/collections/PatientDiagnoses');


    var InstrumentalPopup = View.extend({

        template: popupTmpl,
        events: {
            'click #assigner-outer': 'openAssignerSelectPopup',
            'click #executor-outer': 'openExecutorSelectPopup',
            'click .document-type-filter-orgstruct': 'onDocumentTypeFilterOrgStructToggle',
            'change input[name=urgent]': 'onChangeUrgentInput',
            'change #finance': 'onChangeFinanceInput',
            'change #plannedTime': 'onChangePlannedTimePicker',
            'change #plannedDate': 'onChangePlannedDatePicker',
            'change #createDate': 'onChangeCreateDatePicker',
            'change #createTime': 'onChangeCreateTimePicker',
            'change input[name="diagnosis[mkb][code]"]': 'onChangeMkbInput'
        },

        initialize: function () {
            _.bindAll(this);


            this.viewModel = new ViewModel({}, {
                appeal: this.options.appeal
            });



            //диагнозы из госпитализации
            this.appealDiagnosis = new PatientDiagnoses(null, {
                appealId: this.options.appeal.get('id')
            });

            //список групп исследований
            this.instrumntalResearchs = new InstrumntalGroups();
            this.instrumntalResearchs.patientId  = this.options.appeal.get('patient').get('id');

            this.researchGroupsListView = new ResearchGroupsListView({
                collection: this.viewModel.instrumntalGroups
            });

            this.viewModel.instrumntalGroups.fetch();

            //список исследований в выбранной группе
            this.researchListView = new ResearchListView({
                collection: this.instrumntalResearchs
            });


            this.bfView = new BFView({
                data: this.viewModel.toJSON()
            });
            this.depended(this.bfView);


            pubsub.on('research:selected', this.onSelectResearch, this);
            pubsub.on('research:deselected', this.onDeselectResearch, this);
            pubsub.on('assigner:changed', this.onChangeAssigner, this);
            pubsub.on('executor:changed', this.onChangeExecutor, this);



        },

        openAssignerSelectPopup: function () {
            this.personDialogView = new PersonDialogView({
                title: 'Направивший врач',
                appeal: this.options.appeal,
                callback: function (person) {
                    pubsub.trigger('assigner:changed', person);
                }
            });

            this.personDialogView.render().open();

        },

        openExecutorSelectPopup: function () {
            this.personDialogView = new PersonDialogView({
                appeal: this.options.appeal,
                title: 'Исполнитель',
                callback: function (person) {
                    pubsub.trigger('executor:changed', person);
                }
            });

            this.personDialogView.render().open();

        },

        onSelectResearch: function (researchCode) {
            var executor,
                self = this;

            self.viewModel.set('code', researchCode);
            //шаблон данных в формате commonData
            self.testTemplate = new InstrumentalResearchTemplate({}, {
                code: researchCode,
                patientId: self.viewModel.get('patientId')
            });

            self.testTemplate.fetch().done(function () {

                if (self.testTemplate.getProperty('executorId', 'value') > 0) {
                    executor = {
                        id: self.testTemplate.getProperty('executorId', 'value'),
                        name: {
                            first: self.testTemplate.getProperty('doctorFirstName', 'value'),
                            middle: self.testTemplate.getProperty('doctorMiddleName', 'value'),
                            last: self.testTemplate.getProperty('doctorLastName', 'value')
                        }
                    };

                    pubsub.trigger('executor:changed', executor);
                }
            });

        },

        onDeselectResearch: function () {
            var executor = {
                id: '',
                name: {
                    first: '',
                    middle: '',
                    last: ''
                }
            };

            this.viewModel.set('code', '');

            if (this.testTemplate) {
                this.testTemplate.clear();
            }

            pubsub.trigger('executor:changed', executor);
        },

        onChangeAssigner: function (assigner) {
            this.viewModel.set({
                'assignerId': assigner.id,
                'assignerFirstName': assigner.name.first,
                'assignerMiddleName': assigner.name.middle,
                'assignerLastName': assigner.name.last
            });

            this.ui.$assigner.val(assigner.name.last + ' ' + assigner.name.first + ' ' + assigner.name.middle);
        },

        onChangeExecutor: function (executor) {
            this.viewModel.set({
                'executorId': executor.id,
                'executorFirstName': executor.name.first,
                'executorMiddleName': executor.name.middle,
                'executorLastName': executor.name.last
            });

            this.ui.$executor.val(executor.name.last + ' ' + executor.name.first + ' ' + executor.name.middle);
        },

        onChangeCreateDatePicker: function () {
            var createDate = this.ui.$createDate.datepicker('getDate');
            this.viewModel.set('createDay', createDate);
        },

        onChangeCreateTimePicker: function () {
            var createTime = this.ui.$createTime.val();
            this.viewModel.set('createTime', createTime);
        },

        onChangeUrgentInput: function () {
            var urgent = this.ui.$urgent.prop('checked');
            this.viewModel.set('urgent', urgent);
        },

        onChangeFinanceInput: function () {
            var financeId = this.$(this.ui.$finance.find('option:selected')[0]).val();
            this.viewModel.set('finance', financeId);
        },


        onChangeMkbInput: function () {
            var mkbId = this.ui.$mbkCode.data('mkb-id');
            this.viewModel.set('mkbId', mkbId);
        },
        onChangePlannedTimePicker: function () {
            var plannedTime = this.ui.$plannedTime.val();
            this.viewModel.set('plannedTime', plannedTime);
        },

        onChangePlannedDatePicker: function () {
            var plannedDate = this.ui.$plannedDate.datepicker('getDate');
            this.viewModel.set('plannedDay', plannedDate);
        },

        onDocumentTypeFilterOrgStructToggle: function(event) {
            if ($(event.target).attr('checked')){
                this.viewModel.instrumntalGroups.setOrgStructFilter('1');
            } else {
                this.viewModel.instrumntalGroups.setOrgStructFilter('0');
            }
        },

        validateForm: function () {
            var errors = this.viewModel.validateModel(this.viewModel.toJSON());

            this.showErrors(errors);
            this.saveButton(!errors);

        },

        showErrors: function (errors) {
            var self = this;
            self.ui.$errors.html('').hide();
            if (errors) {
                _.each(errors, function (error) {
                    self.ui.$errors.append(error.message);
                });
                self.ui.$errors.show();
            }
        },

        saveButton: function (enabled, msg) {
            console.log('saveButton', this.ui);
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

        onSave: function () {
            var view = this;

            view.testTemplate.setProperty('assignerId', 'value', view.viewModel.get('assignerId'));
            //assignerFirstName - имя врача назначившего исследование
            view.testTemplate.setProperty('assignerFirstName', 'value', view.viewModel.get('assignerFirstName'));
            //assignerMiddleName - отчество врача назначившего исследование
            view.testTemplate.setProperty('assignerMiddleName', 'value', view.viewModel.get('assignerMiddleName'));
            //assignerLastName - фамилия врача назначившего исследование
            view.testTemplate.setProperty('assignerLastName', 'value', view.viewModel.get('assignerLastName'));


            view.testTemplate.setProperty('executorId', 'value', view.viewModel.get('executorId'));
            //doctorFirstName - имя врача исполнителя исследование
            view.testTemplate.setProperty('doctorFirstName', 'value', view.viewModel.get('executorFirstName'));
            //doctorMiddleName - отчество врача исполнителя исследование
            view.testTemplate.setProperty('doctorMiddleName', 'value', view.viewModel.get('executorMiddleName'));
            // doctorLastName - фамилия врача исполнителя исследование
            view.testTemplate.setProperty('doctorLastName', 'value', view.viewModel.get('executorLastName'));


            //assessmentDate - дата создания направления на исследование
            view.testTemplate.setProperty('assessmentDate', 'value', view.viewModel.get('createDatetime'));

            //plannedEndDate - планируемая дата выполнения иследования
            view.testTemplate.setProperty('plannedEndDate', 'value', view.viewModel.get('plannedDatetime'));

            //finance - идентификатор типа оплаты
            view.testTemplate.setProperty('finance', 'value', view.viewModel.get('finance'));

            //urgent - срочность
            view.testTemplate.setProperty('urgent', 'value', view.viewModel.get('urgent'));

            //идентификатор направительного диагноза
            var mkbId = view.viewModel.get('mkbId');

            view.testTemplate.setProperty('Направительный диагноз', 'valueId', mkbId);

            if (!mkbId) {
                view.testTemplate.setProperty('Направительный диагноз', 'value', '');
            }


            //создание направления сейчас реализованно только для группы тестов....
            //поэтому создаём коллекцию и добавляем в неё модель...
            view.tests = new InstrumentalResearchs(null, {
                appealId: view.viewModel.get('appealId')
            });

            view.tests.add(view.testTemplate);

            this.saveButton(false, 'Сохраняем');

            view.tests.saveAll({
                success: function () {
                    view.close();
                    pubsub.trigger('instrumental-diagnostic:added');
                },
                error: function (response, error) {

                    view.saveButton(true, 'Сохранить');
                    if (response.responseText) {
                        var text = $.parseJSON(response.responseText);
                        var errorMessage = text.errorMessage || 'Ошибка при создании направления';
                        if (errorMessage) {
                            pubsub.trigger('noty', {
                                text: errorMessage,
                                type: 'error'
                            });
                        }

                    }
                }
            });

        },

        render: function () {
            var view = this;
            view.renderNested(this.bfView, '.bottom-form');
            view.researchGroupsListView.setElement(this.$el.find('.instrumental-groups'));
            view.researchListView.setElement(this.$el.find('.instrumental-researchs'));
            if (!Core.Cookies.get('userDepartmentId')) {
                this.$el.find('.document-type-filter-orgstruct').attr('disabled', 'disabled').removeAttr('checked');
            }
            return this;
        },
        afterRender: function () {
            var view = this;
            var now = new Date();

            view.ui = {};

            view.ui.$createDate = view.$('#createDate');
            view.ui.$createDateIcon = view.bfView.$el.find('.icon-calendar');
            view.ui.$createTime = view.$('#createTime');
            view.ui.$plannedDate = view.$('#plannedDate');
            view.ui.$plannedTime = view.$('#plannedTime');
            view.ui.$saveButton = view.$el.closest('.ui-dialog').find('.save');
            view.ui.$assigner = view.$('#assigner');
            view.ui.$executor = view.$('#executor');
            view.ui.$urgent = view.$('input[name=urgent]');
            view.ui.$finance = view.$('#finance');
            view.ui.$mbkCode = view.$('input[name="diagnosis[mkb][code]"]');
            view.ui.$mbkDiagnosis = view.$('input[name="diagnosis[mkb][diagnosis]"]');
            view.ui.$errors = view.$('#errors');

            this.$('.change-assigner,.change-executor').button();


            view.ui.$createDate.datepicker({
                minDate: now,
                onSelect: function () {
                    view.ui.$createDate.trigger('change');
                    // var date = view.$(this).datepicker('getDate');
                    // var day = moment(date).startOf('day');
                    // var currentDay = moment().startOf('day');
                    // var currentHour = moment().hour();
                    // var hour = view.ui.$createTime.timepicker('getHour');
                    // //если выбрана текущая дата и время в таймпикере меньше текущего, то сбрасываем таймпикер
                    // if (day.diff(currentDay, 'days') === 0) {
                    //     if (hour <= currentHour) {
                    //         view.ui.$createTime.val('').trigger('change');
                    //     }
                    // }
                }
            }).datepicker('setDate', view.viewModel.get('createDay'));

            view.ui.$createDateIcon.on('click', function () {
                view.ui.$createDate.datepicker('show');
            });

            view.ui.$createTime.timepicker({
                showPeriodLabels: false,
                defaultTime: 'now',
                showOn: 'both',
                button: '.bottom-form .icon-time' //,
                // onHourShow: function(hour) {
                //     var date = view.ui.$createDate.datepicker('getDate');
                //     var day = moment(date).startOf('day');
                //     var currentDay = moment().startOf('day');
                //     var currentHour = moment().hour();
                //     //если выбран текущий день, то часы меньше текущего нельзя выбрать
                //     if (day.diff(currentDay, 'days') === 0) {
                //         if (hour < currentHour) {
                //             return false;
                //         }
                //     }

                //     return true;
                // },
                // onMinuteShow: function(hour, minute) {
                //     var date = view.ui.$createDate.datepicker('getDate');
                //     var day = moment(date).startOf('day');
                //     var currentDay = moment().startOf('day');
                //     var currentHour = moment().hour();
                //     var currentMinute = moment().minute();
                //     //если выбран текущий день и час, то минуты меньше текущего времени нельзя выбрать
                //     if (day.diff(currentDay, 'days') === 0) {
                //         if (hour === currentHour && minute <= currentMinute) {
                //             return false;
                //         }
                //     }
                //     return true;
                // }
            }).mask('99:99').val(view.viewModel.get('createTime'));


            view.ui.$plannedDate.datepicker({
                minDate: new Date(),
                onSelect: function () {
                    view.ui.$plannedDate.trigger('change');
                    var date = view.$(this).datepicker('getDate');
                    var day = moment(date).startOf('day');
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
            }).datepicker('setDate', this.viewModel.get('plannedDay'));

            view.ui.$plannedTime.timepicker({
                defaultTime: 'now',
                showPeriodLabels: false,
                showOn: 'both',
                button: '.icon-time',
                onHourShow: function (hour) {
                    var date = view.ui.$plannedDate.datepicker('getDate');
                    var day = moment(date).startOf('day');
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
                onMinuteShow: function (hour, minute) {
                    var date = view.ui.$plannedDate.datepicker('getDate');
                    var day = moment(date).startOf('day');
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
                }
            }).val(this.viewModel.get('plannedTime'));

            view.appealDiagnosis.fetch().done(function () {
                //установка диагноза
                if ((view.appealDiagnosis.length > 0) && view.appealDiagnosis.first()) {
                    var diagnosis = view.appealDiagnosis.first().get('mkb');
                    view.viewModel.set('mkbId', diagnosis.id);
                    view.ui.$mbkDiagnosis.val(diagnosis.diagnosis);
                    view.ui.$mbkCode.val(diagnosis.code);
                    view.ui.$mbkCode.data('mkb-id', diagnosis.id);
                }
            });

            this.viewModel.on('change', this.validateForm, this);
            //            this.validateForm();

        },
        close: function () {

            this.ui.$plannedDate.datepicker('destroy');
            this.ui.$createDate.datepicker('destroy');
            this.$el.dialog('close');
            this.bfView.close();
            this.researchGroupsListView.close();
            this.researchListView.close();
            this.viewModel.off('change');
            pubsub.off('research:selected research:deselected assigner:changed executor:changed');
            this.$el.remove();
            this.remove();
        }

    }).mixin([popupMixin]);

    return InstrumentalPopup;
});
