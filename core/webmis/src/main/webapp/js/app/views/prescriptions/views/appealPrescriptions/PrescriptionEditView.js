define(function (require) {
    var template = require('text!views/prescriptions/templates/appeal/prescription-edit.html');
    var BaseView = require('views/prescriptions/views/BaseView');
    var AddDrugPopupView = require('views/prescriptions/views/appealPrescriptions/AddDrugPopupView');
    var AdministrationMethod = require('collections/AdministrationMethod');
    var Prescription = require('views/prescriptions/models/Prescription');
    var rivets = require('rivets');
    require('datetimeEntry');
    var popupMixin = require('mixins/PopupMixin');

    return BaseView.extend({
        template: template,
        initialize: function (opt) {
            var self = this;
            this.options.title = 'Редактирование назначения';
            //console.log('init new prescription view', this);
            this.prescription = opt.prescription;
            this.administration = new AdministrationMethod();
            self.administration.fetch({
                data: {
                    code: self.getMoaKeys()
                }
            })
                .done(function () {
                    // self.debug();
                    self.render();

                    self.listenTo(self.prescription, 'change', self.validateForm);
                });

        },
        validateForm: function () {

            var errors = this.validate();

            this.saveButton(!errors.length);
            this.showErrors(errors);
        },

        showErrors: function (errors) {
            var $errors = this.$el.find('.errors')
            $errors.html('')
                .hide();

            if (errors.length > 0) {
                _.each(errors, function (error) {
                    $errors.append(error);
                }, this);
                //	$errors.append(errors[0]);
                $errors.show();
            }
        },

        validate: function () {
            var errors = [];

            var drugs = this.prescription.get('drugs');
            if (drugs && drugs instanceof Backbone.Collection) {
                if (!drugs.length) {
                    errors.push('Список лекарственных препаратов пуст. ');
                }
                var drugsErrors = drugs.validateCollection();
                if (drugsErrors && drugsErrors.length) {
                    errors = errors.concat(drugsErrors);
                }

            }

            var intervals = this.prescription.get('assigmentIntervals');
            if (intervals && intervals instanceof Backbone.Collection) {
                if (!intervals.length) {
                    errors.push('Список интервалов приёма препаратов пуст. ');
                }
                intervalsErrors = intervals.validateCollection();
                if (intervalsErrors && intervalsErrors.length) {
                    errors = errors.concat(intervalsErrors);
                }
            }
            console.log('validate', errors);
            return errors;
        },

        saveButton: function (enabled, msg) {
            var $saveButton = this.$el.closest('.ui-dialog')
                .find('.save');
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

        // close: function () {
        //     this.$el.dialog('close');
        //     this.remove();
        // },

        getMoaKeys: function () {
            var moaModel = this.prescription.getPropertyByCode('moa');
            var moaModelValueDomain = moaModel.get('valueDomain');
            var moaKeys = (moaModelValueDomain.split(';'))[1];
            return moaKeys;
        },
        debug: function () {
            /*
             *            this.prescription.on('change', function () {
             *                console.log('prescription change', this.prescription);
             *            }, this);
             *
             */
            this.prescription.on('change', this.showJSON, this);
            this.prescription.get('drugs')
                .on('add remove change', this.showJSON, this);
            this.prescription.get('assigmentIntervals')
                .on('add remove change', this.showJSON, this);
        },
        showJSON: function () {
            this.$el.find('#debug')
                .html(JSON.stringify(this.prescription.toJSON(), null, 4));
        },
        events: {
            'click .add-drug': 'onClickAddDrug'
        },
        onSave: function () {
            //console.log('onClickSave');
            var self = this;

            this.saveButton(false, 'Сохраняем...');
            this.prescription.save({}, {
                success: function (m, r) {
                    if (r.data) {
                        pubsub.trigger('prescription:saved', self.prescription)
                        console.log('saved', arguments);
                        self.close();
                        // self.collection.fetch({
                        //     data: {
                        //         eventId: self.appealId
                        //     }
                        // });
                    } else {
                        console.log('error', r.message);
                    }
                }
            });
        },

        onClickAddDrug: function () {
            var popup = new AddDrugPopupView({
                prescription: this.prescription,
                appeal: this.options.appeal
            });

            popup.render();
            popup.open();
        },

        data: function () {
            return {
                administration: this.administration.toJSON(),
                moa: this.prescription.get('moa')
            };
        },

        render: function () {
            var self = this;
            BaseView.prototype.render.call(self);

            function datetimeRange(input) {
                var id = input.id.split('-');
                var cid = id[0];
                var type = id[1];

                return {
                    minDatetime: (type == 'to' ? moment($('#' + cid + '-from').datetimeEntry('getDatetime')).add('m', 1).toDate() : null),
                    maxDatetime: (type == 'from' ? moment($('#' + cid + '-to').datetimeEntry('getDatetime')).subtract('m', 1).toDate() : null)
                };
            }

            if (this.prescription.get('assigmentIntervals')) {
                this.prescription.get('assigmentIntervals')
                    .on('add remove', function () {
                        setTimeout(function () {
                            $('.datetime_entry')
                                .datetimeEntry({
                                    datetimeFormat: 'D.O.Y H:M',
                                    beforeShow: datetimeRange
                                });
                        }, 100);

                    }, this);
            }

            if (this.prescription.get('drugs')) {
                this.prescription.get('drugs')
                    .on('add remove', function () {
                        console.log('add drug', arguments)
                        setTimeout(function () {
                            self.$el.find('select').select2();


                        }, 100);
                    });

            }

            rivets.formatters.datetime = {
                read: function (value) { //вывод в хтмл
                    var v;
                    if (value) {
                        v = moment(value)
                            .format('DD.MM.YYYY HH:mm');
                    } else {
                        v = '';
                    }
                    return v;
                },
                publish: function (value) { //в модель
                    var v;
                    if (value) {
                        v = moment(value, 'DD.MM.YYYY HH:mm')
                            .valueOf();
                    } else {
                        v = null;
                    }
                    return v;
                }
            };

            rivets.formatters.add = function (value, string) {
                return value + string;
            };

            rivets.bind(self.el, {
                prescription: self.prescription
            });

            var drugs = this.prescription.get('drugs');
            drugs.each(function (drug) {
                drug.trigger('change:unit');
            });

            $('.datetime_entry')
                .datetimeEntry({
                    datetimeFormat: 'D.O.Y H:M',
                    beforeShow: datetimeRange
                });


            this.$el.find('button').button();
            this.$el.find('select').select2();
            this.validateForm();

        }

    }).mixin([popupMixin]);


});
