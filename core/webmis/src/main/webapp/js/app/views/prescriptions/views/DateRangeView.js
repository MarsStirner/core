define(function (require) {
    var template = require('text!views/prescriptions/templates/date-range.html');
    var BaseView = require('views/prescriptions/views/BaseView');
    var rivets = require('rivets');

    return BaseView.extend({
        template: template,
        initialize: function () {

            if (!this.model.get('dateRangeMin')) {
                this.model.set('dateRangeMin', moment()
                    .startOf('day')
                    .add('hours',12)
                    .format('X'));

                this.model.set('dateRangeMax', moment().startOf('day').add('days', 1).add('hours', 12).format('X'));
            }
            this.listenTo(this.model, 'change:dateRangeMin', this.setDataRangeMax);
        },
        setDataRangeMax: function (model, dateRangeMin) {
            var dateRangeMax = moment(dateRangeMin * 1000)
                .add('days', 1)
                .format('X');
            this.model.set('dateRangeMax', dateRangeMax);
            // console.log('setDataRangeMax', arguments);
        },
        data: function () {
            // console.log('dateRange data', this.model.toJSON());
            return this.model.toJSON();
        },
        afterRender: function () {
            this.ui = {};
            this.ui.$dateRangeMin = this.$el.find("#dateRangeMin");
            this.ui.$dateRangeMin.datepicker();

            rivets.formatters.date = {
                read: function (value) {
                    var output = moment(value, 'X')
                        .format('DD.MM.YYYY');
                    return output;
                },
                publish: function (value) {
                    var output = moment(value, 'DD.MM.YYYY').add('hours', 12).format('X');
                    return output;
                }
            };
            rivets.formatters.dateTime = function (value) {
                return moment(value,'X')
                    .format('DD.MM.YYYY HH:mm');
            };
            rivets.formatters.time = function (value) {
                return moment(value,'X')
                    .format('HH:mm');
            };


            rivets.bind(this.el, {
                filter: this.model
            });
        }

    });

});
