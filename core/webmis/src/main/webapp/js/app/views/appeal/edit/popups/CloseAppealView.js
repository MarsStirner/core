define(function (require) {
    var popupMixin = require('mixins/PopupMixin');
    var Docs4closing = require('models/Docs4closing');
    var Results = require('collections/Results');
    var Moves = require('collections/moves/moves');

    var template = require('text!templates/appeal/edit/popups/close-appeal-popup.html');

    return View.extend({
        initialize: function () {
            this.appealId = this.options.appeal.get('id');

            //документы необходимые для закрытия иб
            this.docs4closing = new Docs4closing();
            this.docs4closing.appealId = this.appealId;

            //Список результатов госпитализации
            this.results = new Results();

            //Движения
            this.moves = new Moves();
            this.moves.appealId = this.appealId;

            //promises
            this.docsP = this.docs4closing.fetch();
            this.movesP = this.moves.fetch();
            this.resultsP = this.results.fetch({
                data: {
                    appealId: this.appealId
                }
            });

        },
        //template:'',
        events: {
            'click .save': 'onSave',
            'change #results': 'validate' //,
            //'change .appeal-close-date': 'validate',
            //'change .appeal-close-time': 'validate'
        },
        validate: function () {
            var result = this.getResult();
            var closeDate = this.getCloseDate();
            var errors = [];

            if (result.text === '...') {
                errors.push({
                    message: 'Не выбран результат госпитализации. '
                });
            }

            if (errors.length === 0) {
                this.ui.$saveButton.button('enable');
            } else {
                this.ui.$saveButton.button('disable');
            }
        },

        getCloseDate: function () {
            var hour = this.ui.$appealCloseTime.timepicker('getHour');
            var minute = this.ui.$appealCloseTime.timepicker('getMinute');
            var date = this.ui.$appealCloseDate.datepicker('getDate');
            var closeDate = moment(date).minutes(minute).hours(hour).valueOf();
            return closeDate;
        },

        getResult: function () {
            var resultId = this.ui.$results.val();
            var resultText = this.$('option:selected', this.ui.$results).text();

            return {
                id: resultId,
                text: resultText
            };
        },

        onSave: function () {
            var self = this;
            this.ui.$saveButton.button('disable');

            var appealId = this.appealId;
            var closeDate = this.getCloseDate();
            var result = this.getResult();
            var closeDocs = this.ui.$closeDocs.prop('checked');
            var actions = [closeAppeal()];

            if (closeDocs) {
                actions.push(closeAppealDocs());
            }

            $.when(actions).then(function () {
                pubsub.trigger('appeal:closed', {
                    closed: true,
                    closeDate: closeDate,
                    resultText: result.text
                });
                self.close();
            }, function () {
                console.log('ошибка при закрытии иб', arguments);
            });

            function closeAppealDocs() {
                //закрываем документы
                return $.ajax({
                    url: '/api/v1/appeals/' + appealId + '/docs/close?execDate=' + closeDate,
                    dataType: 'jsonp',
                    type: 'GET',
                    success: function () {
                        pubsub.trigger('noty', {
                            text: 'Закрыли документы',
                            type: 'success'
                        });
                        //console.log('success close docs', arguments);
                    }
                });
            }

            // function vacateBed() {
            //     //var closeBedDate = closeDate - (60 * 60 * 1000); //какой-то хак от Марины Владимировны, для поддержки НТК
            //     var closeBedDate = closeDate - (1000); //какой-то хак , для поддержки НТК
            //     //выписка с койки
            //     return $.ajax({
            //         url: '/api/v1/appeals/' + appealId + '/bed/vacate?execDate=' + closeBedDate,
            //         dataType: 'jsonp',
            //         type: 'GET',
            //         success: function (data) {
            //             if (data) {
            //                 pubsub.trigger('noty', {
            //                     text: 'Выписали с койки',
            //                     type: 'success'
            //                 });
            //             }
            //         }
            //     });
            // }

            function closeAppeal() {
                //выписка с истории болезни
                return $.ajax({
                    url: '/api/v1/appeals/' + appealId + '/close',
                    data: {
                        execDate: closeDate,
                        resultId: result.id
                    },
                    dataType: 'jsonp',
                    type: 'GET',
                    success: function () {
                        pubsub.trigger('noty', {
                            text: 'Закрыли историю болезни',
                            type: 'success'
                        });
                        //console.log('success close appeal', arguments);
                    }
                });
            }
        },

        data: function () {
            var data = {};

            data.doctorId = this.options.appeal.get('execPerson').id;
            data.docs = this.docs4closing.toJSON();
            data.appealId = this.appealId;
            data.results = this.results.toJSON();
            data.lastMoveClosed = this.moves.length && this.moves.last().get("admission") && this.moves.last().get("leave");

            return data;
        },

        render: function () {
            var self = this;

            $.when(this.movesP, this.docsP, this.resultsP).then(function () {
                self.$el.html(_.template(template, self.data(), {
                    variable: 'data'
                }));

                self.afterRender();
            });

            return this;
        },
        afterRender: function () {
            var self = this;

            this.ui = {};
            this.ui.$saveButton = this.$el.closest(".ui-dialog").find('.save');
            this.ui.$saveButton.button('disable');
            this.ui.$results = this.$el.find('#results');
            this.ui.$appealCloseDate = this.$('.appeal-close-date');
            this.ui.$appealCloseTime = this.$('.appeal-close-time');
            this.ui.$closeDocs = this.$('.close-docs');

            var datetime = new Date();

            this.ui.$appealCloseDate.datepicker({
                minDate: 0,
                onSelect: function (dateText, inst) {
                    var day = moment(self.$(this).datepicker('getDate')).startOf('day');
                    var currentDay = moment().startOf('day');
                    var currentHour = moment().hour();
                    var hour = self.ui.$appealCloseTime.timepicker('getHour');
                    //если выбрана текущая дата и время в таймпикере меньше текущего, то сбрасываем таймпикер
                    if (day.diff(currentDay, 'days') === 0) {
                        if (hour <= currentHour) {
                            self.ui.$appealCloseTime.timepicker('setTime', (new Date())).trigger('change');
                        }
                    }
                }
            }).datepicker('setDate', datetime);

            this.ui.$appealCloseTime.timepicker({
                showPeriodLabels: false,
                onHourShow: function (hour) {
                    var day = moment(self.ui.$appealCloseDate.datepicker('getDate')).startOf('day');
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
                    var day = moment(self.ui.$appealCloseDate.datepicker('getDate')).startOf('day');
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
            }).timepicker('setTime', datetime);

            this.ui.$results.select2();

        },

    }).mixin([popupMixin]);
});
