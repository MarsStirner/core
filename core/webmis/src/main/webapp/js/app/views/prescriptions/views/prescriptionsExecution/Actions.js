define(function (require) {
    var BaseView = require('views/prescriptions/views/BaseView');
    var template = require('text!views/prescriptions/templates/prescriptionsExecution/actions.html');

    var Intervals = require('views/prescriptions/collections/Intervals');
    var Prescriptions = require('views/prescriptions/collections/Prescriptions');
    var PrintButton = require('views/prescriptions/views/PrintPrescriptionButton');
    // var PrintButton = require('views/prescriptions/views/TreeButton');

    return BaseView.extend({
        template: template,
        events: {
            'click .execute-intervals': 'executeIntervals',
            'click .execute-not-executed-intervals': 'executeNotExecutedIntervals'
        },

        initialize: function () {
            this.listenTo(this.collection, 'change reset', this.updateButtons);
            this.listenTo(this.collection, 'fetch', this.disableButtons);

            this.printButton = new PrintButton({
                collection: this.collection
            });

            this.addSubViews({
                '.print-button': this.printButton
            });
// console.log('init actions', this);

        },

        updateButtons: function () {
            var start = this.collection._filter.dateRangeMin * 1000;
            var end = this.collection._filter.dateRangeMax * 1000;

            var selected = this.collection.getSelected();
            selected = this.collection.getSelectedWithNotExecutedIntervals(start, end);
            if (selected.length) {
                this.$el.find('.execute-intervals').button().button('enable');
            } else {
                this.$el.find('.execute-intervals').button().button('disable');
            }

            var notExecuted = this.collection.getNotExecutedIntervals(start, end);
            if (notExecuted.length) {
                this.$el.find('.execute-not-executed-intervals').button().button('enable');
            } else {
                this.$el.find('.execute-not-executed-intervals').button().button('disable');
            }
        },

        disableButtons: function () {
            this.$el.find('.execute-not-executed-intervals').button().button('disable');
            this.$el.find('.execute-intervals').button().button('disable');
        },

        executeIntervals: function () {
            var self = this;
            var start = this.collection._filter.dateRangeMin * 1000;
            var end = this.collection._filter.dateRangeMax * 1000;
            var selected = new Prescriptions(this.collection.getSelected());

            var intervals = new Intervals(selected.getNotExecutedIntervals(start, end));

            intervals.execute({
                success: function () {
                    pubsub.trigger('intervals:executed', intervals);
                },
                error: function () {
                    console.log('error');
                }
            });
        },

        executeNotExecutedIntervals: function () {
            var self = this;
            var start = this.collection._filter.dateRangeMin * 1000;
            var end = this.collection._filter.dateRangeMax * 1000;

            var notExecutedIntervals = new Intervals(this.collection.getNotExecutedIntervals(start, end));

            notExecutedIntervals.execute({
                success: function () {
                    pubsub.trigger('intervals:executed', notExecutedIntervals);
                },
                error: function () {
                    // console.log('error');
                }
            });

            // console.log('notExecutedIntervals', notExecutedIntervals);

        },

        afterRender: function () {

            this.$el.find('button').button();
            this.updateButtons();
        }
    });
});
