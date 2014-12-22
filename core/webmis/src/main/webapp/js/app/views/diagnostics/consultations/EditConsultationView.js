var primer =  {
  'finance': {
    'id': 7
  },
  'eventId': '193527',
  'patientId': 6402,
  'assignerId': 205,
  'createDateTime': 1378902190000,
  'diagnosis': {
    'code': 'D84.8'
  },
  'urgent': true,
  'overQueue': true,
  'actionTypeId': 1465,
  'plannedEndDate': 1378929610000,

  'executorId': 177
};


define(function(require) {

    var popupMixin = require('mixins/PopupMixin');
    var tmpl = require('text!templates/diagnostics/consultations/edit-consultation-popup.html');

    require('collections/doctors');
    var ConsultantsFree = require('collections/doctors-free');
    var Consultation = require('models/diagnostics/consultations/Consultation');
    var ConsultationsGroups = require('collections/diagnostics/consultations/ConsultationsGroups');


    var ConsultantsFreeView = require('views/diagnostics/consultations/ConsultantsFreeView');
    var ConsultationsGroupsView = require('views/diagnostics/consultations/ConsultationsGroupsView');
    var MkbInputView = require('views/ui/MkbInputView');
    var PersonDialogView = require('views/ui/PersonDialog');
    var ScheduleView = require('views/diagnostics/consultations/ScheduleView');
    var SelectView = require('views/ui/SelectView');


    return View.extend({
        template: tmpl,
        className: 'popup',

        events: {
            'change #finance': 'onChangeFinance',
            'change input[name="diagnosis[mkb][code]"]': 'onChangeMKB',
            'click #assigner-outer': 'openAssignerSelectPopup',
            'change #assign-date': 'onChangeAssignDate',
            'change #assign-time': 'onChangeAssignDate',
            'change #urgent': 'onChangeUrgent',
            'change #over': 'onChangeOver',
            'change #datepicker': 'onCangePlannedDate'
        },

        initialize: function(options) {
            var self = this;
            console.log('init new consultation view', this);
            _.bindAll(this);

            this.appealDiagnosis = this.options.appeal.getDiagnosis();
            var appealDoctor = this.options.appeal.get('execPerson');
            self.assigner = {
                id: '',
                name: ''
            };


            //список доступных консультаций
            this.consultationsGroups = new ConsultationsGroups({});
            this.consultationsGroupsView = new ConsultationsGroupsView({
                collection: this.consultationsGroups
            });
            this.consultationsGroups.fetch();


            //список специалистов которые могут оказать консультацию
            this.consultantsFree = new ConsultantsFree();
            this.consultantsFreeView = new ConsultantsFreeView({
                collection: this.consultantsFree
            });

            //классификатор диагнозов
            this.mkbInputView = new MkbInputView();
            this.depended(this.mkbInputView);

            //список видов оплаты
            this.financeDictionary = new App.Collections.DictionaryValues([], {
                name: 'finance'
            });

            //расписание врача
            this.scheduleView = new ScheduleView();


            this.newConsultation = new Consultation({
                'id': this.options.id
            });
            this.newConsultation.eventId = this.options.appealId;

            //получение данных о направление на консультацию которую надо отредактировать
            this.consultation = new Consultation({
                'id': this.options.id
            });
            this.consultation.eventId = this.options.appealId;


            this.consultation.fetch().done(function() {

                var createDateTime = moment(self.consultation.getProperty('assessmentBeginDate'),'YYYY-MM-DD HH:mm:ss').valueOf();
                var plannedEndDate = moment(self.consultation.getProperty('plannedEndDate'),'YYYY-MM-DD HH:mm:ss').startOf('day').valueOf();

                var urgent = false;
                if(self.consultation.getProperty('urgent') === 'true'){
                    urgent = true;
                }

                var overQueue = false;
                if(self.consultation.getProperty('pacientInQueueType') === '2'){
                    overQueue = true;
                }

                var diagnosisCode = '';
                if(self.consultation.getProperty('Направительный диагноз')){

                    diagnosisArray = self.consultation.getProperty('Направительный диагноз').split(' ');
                    diagnosisCode = diagnosisArray[0];
                }

                if(self.consultation.getProperty('pacientInQueueType') == 0){
                    self.newConsultation.plannedTimeChanged = false;
                }else{
                    self.newConsultation.plannedTimeChanged = true;
                }


                self.newConsultation.set({
                    'finance': {
                        'id': parseInt(self.consultation.getProperty('finance'), 10)
                      },
                      'pacientInQueue': parseInt(self.consultation.getProperty('pacientInQueueType'), 10),
                      'eventId': self.options.appealId,
                      'patientId': parseInt(self.options.appeal.get('patient').get('id'), 10),
                      'assignerId': parseInt(self.consultation.getProperty('assignerId'), 10),
                      'createDateTime': createDateTime,
                      'diagnosis': {
                        'code': diagnosisCode
                      },
                      'urgent': urgent,
                      'overQueue': overQueue,
                      'actionTypeId': parseInt(self.consultation.get('typeId'), 10),
                      'plannedEndDate': plannedEndDate,

                      'executorId': parseInt(self.consultation.getProperty('executorId'), 10)
                });


                var executorId = self.consultation.getProperty('executorId');

                self.assigner = {
                    id: executorId,
                    name: {
                        first: self.consultation.getProperty('assignerFirstName'),
                        last: self.consultation.getProperty('assignerLastName'),
                        middle: self.consultation.getProperty('assignerMiddleName')
                    }
                };

                self.data = {
                    'assigner': self.assigner
                };


                self.consultationsGroupsView.selectedItemCode = self.consultation.get('code');

                self.consultantsFreeView.selectedItemCode = executorId;
                var actionType = self.consultation.get('typeId');
                var beginDate = moment(self.consultation.getProperty('plannedEndDate'), 'YYYY-MM-DD HH:mm:ss').valueOf();

                self.on('after:render', function() {
                    self.ui.$planedDate.datepicker('enable');
                    self.ui.$planedDate.datepicker('setDate', moment(beginDate).toDate());

                });

                self.consultantsFree.fetch({
                    data: {
                        filter: {
                            beginDate: beginDate,
                            actionType: actionType
                        }
                    }
                }).done(function() {
                    var consultant = self.consultantsFree.find(function(item) {
                        return item.get('doctor').id == executorId;
                    });

                    self.renderShedule(consultant);
                    self.consultantsFree._params = null;
                });

                self.render();

                self.showType();
                self.newConsultation.on('change:actionTypeId', self.loadConsultants, self);
                self.newConsultation.on('changed:plannedEndDate', self.loadConsultants, self);

               // self.newConsultation.on('change', self.setPacientInQueue, self);

                self.newConsultation.on('change', self.validateForm, self);

            });


            pubsub.on('consultation:selected', this.onConsultationSelect, this);
            pubsub.on('consultant:selected', this.onConsultantSelect, this);

            pubsub.on('person:changed', this.onChangeAssignPerson, this);

            pubsub.on('date:selected', this.onSelectDate, this);
            pubsub.on('time:selected', this.onTimeSelect, this);
            pubsub.on('time:unselected', this.onTimeUnselect, this);



        },


        //при выборе консультации
        onConsultationSelect: function(code) {

            var consultation = this.consultationsGroups.find(function(model) {
                return model.get('code') === code;
            });

            if (consultation) {
                this.newConsultation.set('actionTypeId', consultation.get('id'));
            }

        },

        //при выборе консультанта
        onConsultantSelect: function(executorId) {

            var consultant = this.consultantsFree.find(function(consultant) {
                return consultant.get('doctor').id === executorId;
            });
            this.newConsultation.set('executorId', executorId);
            this.renderShedule(consultant);
        },

        onCangePlannedDate: function(e) {
            var $target = this.$(e.target);
            var timestamp = moment($target.val(), 'DD.MM.YYYY').valueOf();
            pubsub.trigger('date:selected', timestamp);
        },

        //при выборе даты
        onSelectDate: function(date) {
            // console.log('onChangePlanedDate', date);
            var newDate = moment(date);
            var year = newDate.year();
            var month = newDate.month();
            var date = newDate.date();

            var plannedDate = moment(this.newConsultation.get('plannedEndDate'))
                .year(year).month(month).date(date).valueOf();

            this.newConsultation.set('plannedEndDate', plannedDate);
            this.newConsultation.trigger('changed:plannedEndDate')


        },


        //при выборе времени
        onTimeSelect: function(plannedTime) {
            this.newConsultation.set('plannedTime', plannedTime);
            this.newConsultation.plannedTimeChanged = true;
        },

        onTimeUnselect: function() {
            this.newConsultation.set('plannedTime', null);
            this.newConsultation.plannedTimeChanged = true;
        },

        setPacientInQueue: function(){
            console.log('setPacientInQueue',
                this.newConsultation.get('pacientInQueue'),
                this.newConsultation.get('plannedTime'),
                this.newConsultation.plannedTimeChanged,
                this.newConsultation.get('urgent'),
                this.newConsultation.get('overQueue'));

            if(this.newConsultation.get('plannedTime') || !this.newConsultation.plannedTimeChanged){
                this.newConsultation.set('pacientInQueue', 0);
            }else{
                //if(this.newConsultation.plannedTimeChanged){
                    if(this.newConsultation.get('urgent')){
                        this.newConsultation.set('pacientInQueue', 1);
                    }

                    if(this.newConsultation.get('overQueue')){
                        this.newConsultation.set('pacientInQueue', 2);
                    }
                    if(!this.newConsultation.get('urgent') && !this.newConsultation.get('overQueue')){
                        this.newConsultation.set('pacientInQueue', 4);
                    }

                //}else{
                //    this.newConsultation.set('pacientInQueue', 4 );
                //}
            }
            console.log('setPacientInQueue2',
                this.newConsultation.get('pacientInQueue')
                ,this.newConsultation.get('plannedTime'),
                this.newConsultation.plannedTimeChanged,
                this.newConsultation.get('urgent'),
                this.newConsultation.get('overQueue'));


        },
        onChangeAssignDate: function() {
            var date = this.ui.$assignDate.val();
            var time = this.ui.$assignTime.val();
            var datetime = moment(date + ' ' + time, 'DD.MM.YYYY HH:mm').valueOf();

            this.newConsultation.set('createDateTime', datetime);
        },

        //при выборе вида оплаты
        onChangeFinance: function(e) {
            var $target = this.$(e.target);

            this.newConsultation.set('finance', {
                id: $target.val()
            });
        },

        onChangeUrgent: function(e) {
            var $target = this.$(e.target);
            var urgent = $target.prop('checked');
            this.newConsultation.set('urgent', urgent);
            
            if(urgent){
                this.onTimeUnselect();
                this.ui.$over.prop('checked',false).trigger('change');
            }

            this.sheduleState();
        },

        //при изменении 'Сверх сетки приёма'
        onChangeOver: function(e) {
            var $target = this.$(e.target);
            var over = $target.prop('checked');

            this.newConsultation.set('overQueue', over);

            if (over) {
                this.onTimeUnselect();
                this.ui.$urgent.prop('checked',false).trigger('change');
            } else {
            }
            this.sheduleState();
        },

        //при изменении диагноза
        onChangeMKB: function(e) {
            var $target = this.$(e.target);
            var mkbId = $target.data('mkb-id');
            var mkbCode = $target.val();

            this.newConsultation.set('diagnosis', {'code': mkbCode});

            if (!mkbId) {
                this.newConsultation.set('diagnosis', {'code': ''});
            }

        },

        onChangeAssignPerson: function(assigner) {
            this.ui.$assignPerson.val(assigner.name.raw);

            this.newConsultation.set('assignerId', assigner.id);
        },

        openAssignerSelectPopup: function() {
            this.personDialogView = new PersonDialogView({
                title: 'Направивший врач',
                appeal: this.options.appeal
            });

            this.personDialogView.render().open();
        },

        //загрузка списка консультантов
        loadConsultants: function() {

            if (!this.newConsultation.get('plannedEndDate') || !this.newConsultation.get('actionTypeId')) {
                return false;
            }

            var beginDate = this.newConsultation.get('plannedEndDate');
            var actionType = this.newConsultation.get('actionTypeId');

            this.consultantsFree.fetch({
                data: {
                    filter: {
                        beginDate: beginDate,
                        actionType: actionType
                    }
                }
            });
        },
        //расписание приёма консультанта на определённый день
        renderShedule: function(consultant) {
            this.scheduleView.collection.reset(consultant.get('schedule').toJSON());
            this.scheduleView.render();
            this.sheduleState();
        },
        sheduleState: function(){
			if (this.newConsultation.get('overQueue') || this.newConsultation.get('urgent')) {
				this.scheduleView.disable();
			}else{
                this.scheduleView.enable(); 
            }
        },

        //при клике на кнопку 'Сохранить'
        onSave: function() {
            var self = this;
            //this.setPacientInQueue();
            if(!this.newConsultation.get('plannedTime')) this.newConsultation.unset('plannedTime');
            //.unset

            this.saveButton(false, 'Сохраняем...');

            this.newConsultation.save({}, {
                success: function() {

                    pubsub.trigger('noty', {
                        text: 'Направление сохранено',
                        type: 'success'
                    });
                    self.close();
                    pubsub.trigger('consultation:added', this.newConsultation);
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    var textStatus = JSON.parse(textStatus.responseText);
                    var errorMessage = textStatus.errorMessage ? textStatus.errorMessage : 'Ошибка при создании направления';

                    pubsub.trigger('noty', {
                        text: errorMessage,
                        type: 'error'
                    });
                    self.close();
                    //pubsub.trigger('consultation:added', this.consultation);
                }
            });
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
        showType: function(){
            var type = this.newConsultation.get('pacientInQueue');
            var message = 'не определён';

            if(type===0||type===1||type===2){
                if(type===0){
                    message = 'запись по времени';
                }
                if(type===0 && this.newConsultation.get('urgent')){
                    message = 'срочная запись по времени';
                }
                if(type===1){
                    message = 'срочная запись';
                }
                if(type===2){
                    message = 'запись сверх сетки';
                }

            }
            this.ui.$type.html(message);
        },
        showErrors: function(errors) {
            var self = this;
            self.ui.$errors.html('').hide();
            if (errors.length > 0) {
                // _.each(errors, function(error) {
                // self.ui.$errors.append(error.message);
                // });
                self.ui.$errors.append(errors[0].message);
                self.ui.$errors.show();
            }
        },

        validateForm: function() {
            var errors = this.isValid();
            this.saveButton(!errors.length);
            this.showErrors(errors);
            this.setPacientInQueue();

            this.showType();
        },

        isValid: function() {
            var errors = [];
            console.log('con', this.newConsultation.toJSON());

            if (!this.newConsultation.get('actionTypeId')) {
                errors.push({
                    message: 'Не выбрана консультация. '
                });
            }

            if (!this.newConsultation.get('executorId')) {
                errors.push({
                    message: 'Не выбран консультант. '
                });
            }

            if (!this.newConsultation.get('plannedEndDate')) {
                errors.push({
                    message: 'Не выбрана планируемая дата консультации. '
                });
            } else {
                var diff = moment(this.newConsultation.get('plannedEndDate')).diff(moment(), 'days');
                if (diff < 0) {
                    errors.push({
                        message: 'Планируемая дата не может быть меньше текущей'
                    });
                }
            }

            // if (moment(this.newConsultation.get('createDateTime')).startOf('day').diff(moment().startOf('day'),'days') < 0) {
            //     errors.push({
            //         message: 'Дата создания направления не может быть меньше текущей'
            //     });
            //     // console.log('create', moment(this.newConsultation.get('createDateTime')).diff(moment()));
            // }

            if (this.newConsultation.plannedTimeChanged && !this.newConsultation.get('plannedTime') && !(this.newConsultation.get('overQueue') || this.newConsultation.get('urgent'))) {
                errors.push({
                    message: 'Не выбрано планируемое время консультации. '
                });
            }

            if (this.newConsultation.get('urgent') && this.newConsultation.get('overQueue')) {
                errors.push({
                    message: 'Поля "Срочно" и "Сверх сетки приёма" не могут быть выбраны вместе. '
                });
            }


            return errors;

        },


        render: function() {
            var self = this;

            this.ui = {};
            this.ui.$saveButton = this.$el.find('.save');
            //this.ui.$consultations = this.$el.find('#consultations');
            this.ui.$planedDate = this.$el.find('#datepicker');
            this.ui.$type = this.$el.find('[data-type]');
            //this.ui.$saveButton.button('disable');
            this.ui.$finance = this.$el.find('#finance');
            //this.ui.$assignPerson = this.$el.find('#assign-person');
            this.ui.$assignDate = this.$el.find('#assign-date');
            this.ui.$assignTime = this.$el.find('#assign-time');
            this.ui.$assignPerson = this.$el.find('#assigner');

            this.ui.$urgent = this.$el.find('#urgent');
            this.ui.$over = this.$el.find('#over');
            this.ui.$errors = this.$el.find('#errors');

            this.$el.find('.change-assigner').button();

            //календарь
            this.ui.$planedDate.datepicker({
                minDate: 0,
                onSelect: function(dateText, inst) {
                    var timestamp = moment(dateText, 'DD.MM.YYYY').valueOf();
                    pubsub.trigger('date:selected', timestamp);
                    //self.ui.$datepicker.trigger('change');

                }
            });
            this.ui.$planedDate.next('.icon-calendar').on('click', function() {
                this.ui.$planedDate.datepicker('show');
            });
            this.ui.$planedDate.datepicker('disable');


            //дата создания направления
            var assignDate = moment(this.newConsultation.get('createDateTime')).toDate();
            this.ui.$assignDate.datepicker({
                minDate: 0
            }).datepicker('setDate', assignDate)
                .addClass('Disabled')
                .prop('disabled', true);


            this.ui.$assignTime.timepicker();
            this.ui.$assignTime.timepicker('setTime', assignDate)
                .addClass('Disabled')
                .prop('disabled', true);

            //направительный диагноз
            this.renderNested(this.mkbInputView, '#mkb');

            if (this.consultation.getProperty('Направительный диагноз')) {
                var mkbId = this.consultation.getProperty('Направительный диагноз', 'valueId');
                var diagnosis = (this.consultation.getProperty('Направительный диагноз', 'value')).split(' ');
                var mkbCode = diagnosis[0];
                diagnosis.shift();
                var mkbDiagnosis = diagnosis.join(' ');


                //if (this.appealDiagnosis) {
                this.$('input[name="diagnosis[mkb][diagnosis]"]').val(mkbDiagnosis);
                this.$('input[name="diagnosis[mkb][code]"]').val(mkbCode);
                this.$('input[name="diagnosis[mkb][code]"]').data('mkb-id', mkbId);
                //}
            }

            if (this.newConsultation.get('urgent')) {
                this.ui.$urgent.prop('checked', true);
            }


            if (this.newConsultation.get('overQueue')) {
                this.ui.$over.prop('checked', true).trigger('change');
            }


            //список консультаций
            this.renderNested(this.consultationsGroupsView, '#consultations');
            //список консультантов
            this.renderNested(this.consultantsFreeView, '#consultants');
            //расписание на день
            this.scheduleView.setElement(this.$el.find('#schedule'));

            //вид оплаты
            this.financeSelect = new SelectView({
                collection: this.financeDictionary,
                el: this.ui.$finance,
                initSelection: (this.newConsultation.get('finance')).id
            });
            this.depended(this.financeSelect);

            this.open();
            this.trigger('after:render');

            return this;
        },
        close: function() {
            // console.log('popup view close');
            this.mkbInputView.close();
            this.consultationsGroupsView.close();
            this.consultantsFreeView.close();
            this.scheduleView.close();

            this.ui.$planedDate.datepicker('destroy');

            pubsub.off('consultation:selected');
            pubsub.off('consultant:selected');

            pubsub.off('date:selected');
            pubsub.off('person:changed');

            pubsub.off('time:selected');
            pubsub.off('time:unselected');

            this.$el.dialog('close');
            this.undelegateEvents();
            this.$el.remove();
        }

    }).mixin([popupMixin]);


});
