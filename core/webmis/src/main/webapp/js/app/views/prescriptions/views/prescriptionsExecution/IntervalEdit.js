define(function (require) {
    var BaseView = require('views/prescriptions/views/BaseView');
    var popupMixin = require('mixins/PopupMixin');
    var template = require('text!views/prescriptions/templates/prescriptionsExecution/interval-edit.html');
    var rivets = require('rivets');
    var Interval = require('views/prescriptions/models/Interval');
    var Intervals = require('views/prescriptions/collections/Intervals');
    require('moment');
    require('twix');
    require('datetimeEntry');

    return BaseView.extend({
        template: template,

        events: {
            'change .radio :checkbox': 'onButtonClick'
        },

        initialize: function () {
            this.options.title = 'Редактирование интервала';
            this.options.width = '66em';

            this.initialStatus = this.model.get('status');
            this.collection = new Intervals();

            this.form = new Model({
                interval: this.model,
                intervals: this.collection
            });

            this.listenTo(this.collection, 'add remove', function () {
                this.form.set('hideAddPause', this.hideAddPauseButtonPredicat());
            });

            this.collection.add(this.model);
            this.listenTo(this.collection, 'change add remove reset', this.validateForm);
            // console.log('init interval edit', this);
        },

        hideAddPauseButtonPredicat: function () {
            return ((this.collection.length > 1) || !(this.model.get('endDateTime')) ||
                (this.model.get('status') === 3)
            );
        },

        data: function () {
            var data = {};
            // console.log('intervals', this.collection);

            data.interval = this.options.model.toJSON();
            return data;
        },

        onButtonClick: function (e) {
            var $target = this.$(e.target);
            var status = this.initialStatus;

            $target.siblings().prop('checked', false);
            this.ui.$buttonset.buttonset('refresh');

            if ($target.prop('checked')) {
                status = $target.val();
            }
            this.model.set('status', status);
        },

        onSave: function () {
            var self = this;

            this.normilizeCollection();

            this.saveButton(false, 'Сохраняем...');

            this.collection.update({
                success: this.onUpdate
            });

        },

        onUpdate: function () {
            pubsub.trigger('intervals:updated', this.model);
            this.close();
        },

        //если добавлен стоп, то ...
        normilizeCollection: function () {
            var interval = this.model;
            if (this.collection.length < 2) {
                return;
            }
            var pause = this.getPause();

            var pauseStart = moment(pause.get('beginDateTime'));
            var pauseEnd = moment(pause.get('endDateTime'));
            var intervalStart = moment(interval.get('beginDateTime'));
            var intervalEnd = moment(interval.get('endDateTime'));
            // console.log('diff', pauseStart.diff(intervalStart, 'minutes'),  intervalEnd.diff(pauseEnd,'minutes'));

            if (pauseStart.diff(intervalStart, 'minutes') < 3) {
                pause.set('beginDateTime', interval.get('beginDateTime'));
                pauseStart = moment(pause.get('beginDateTime'));
            }

            if (pauseStart.isSame(intervalStart)) {
                interval.set('beginDateTime', pause.get('endDateTime'));
            }

            if (pauseEnd.isSame(intervalEnd)) {
                interval.set('endDateTime', pause.get('beginDateTime'));
            }

            if (pauseStart.isAfter(intervalStart) && pauseEnd.isBefore(intervalEnd)) {
                var endInterval = new Interval();
                endInterval.set({
                    status: 0,
                    beginDateTime: pauseEnd.valueOf(),
                    endDateTime: intervalEnd.valueOf(),
                    actionId: this.model.get('actionId'),
                    masterId: this.model.get('masterId')
                });

                this.collection.add(endInterval);

                interval.set('endDateTime', pause.get('beginDateTime'));
            }

            // console.log('n', this.collection.toJSON(), pause.toJSON(), interval.toJSON());

        },

        addPause: function () {
            var pause = new Interval();
            var actionId = this.model.get('actionId');
            var masterId = this.model.get('masterId');
            var beginDateTime = this.model.get('beginDateTime');
            var endDateTime = moment(beginDateTime).add('minutes', 5).valueOf();

            pause.set('actionId', actionId);
            pause.set('status', 3);
            pause.set('masterId', masterId);
            pause.set('beginDateTime', beginDateTime);
            pause.set('endDateTime', endDateTime);

            this.collection.add(pause);
            // console.log('addPause', pause.toJSON(), this.collection.toJSON());
        },

        getPause: function () {
            var pause = this.collection.find(function (model) {
                return model.get('status') === 3;
            });

            return pause;
        },

        validateForm: function () {
            var errors = this.isValid();
            this.saveButton(!errors.length);
            this.showErrors(errors);
        },

        showErrors: function (errors) {
            this.ui.$errors.html('').hide();
            // console.log('errors', errors)
            if (errors) {
                _.each(errors, function (error) {
                    this.ui.$errors.append(error);
                }, this);

                this.ui.$errors.show();
            }
        },

        isValid: function () {
            var errors = [];
            var pause = this.getPause();
            if (pause) {
                var intervalStart = this.model.get('beginDateTime');
                var intervalEnd = this.model.get('endDateTime');
                var pauseStart = pause.get('beginDateTime');
                var pauseEnd = pause.get('endDateTime');
                var pauseErrors = pause.validateModel();

                if (!pauseEnd) {
                    pauseErrors.push('Не указано окончание интервала.');
                }

                if (pauseStart && pauseEnd && intervalEnd && intervalStart) {
                    var pauseRange = moment(pauseStart).twix(pauseEnd);
                    var intervalRange = moment(intervalStart).twix(intervalEnd);
                    if (pauseStart < intervalStart) {
                        pauseErrors.push('Начало паузы раньше начала интервала.');
                    }

                    if (pauseEnd > intervalEnd) {
                        pauseErrors.push('Окончание паузы позже окончания интервала.');
                    }

                    if (intervalRange.equals(pauseRange)) {
                        pauseErrors.push('Пауза равна интервалу');
                    }
                }

                errors = errors.concat(pauseErrors);
            }

            return errors;
        },

        saveButton: function (enabled, msg) {
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


        render: function () {
            BaseView.prototype.render.call(this);
            this.ui = {};
            this.ui.$buttonset = this.$el.find('.radio');
            this.ui.$errors = this.$('#errors');

            this.$el.find('.add-pause').button();



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

            rivets.binders.bc = function (el, value) {
                el.style['background-color'] = value;
            };

            this.listenTo(this.collection, 'add remove', function () {
                setTimeout(this.initDateMask, 100);
            });



            rivets.bind(this.el, {
                interval: this.model,
                form: this.form,
                view: this
            });

            this.ui.$buttonset.buttonset();
            this.validateForm();
        },

        initDateMask: function () {
            $('.datetime_entry').each(function () {
                $(this).datetimeEntry({
                    datetimeFormat: 'D.O.Y H:M'
                });
            });
        }
    }).mixin([popupMixin]);

});
