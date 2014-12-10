define(function (require) {
    BaseView = require('views/prescriptions/views/BaseView');
    var template = require('text!views/prescriptions/templates/prescriptionsExecution/intervals.html');
    var IntervalView = require('views/prescriptions/views/prescriptionsExecution/Interval');

    return BaseView.extend({
        template: template,
        initialize: function () {
            this.childs = [];
        },

        closeChilds: function () {
            _.invoke(this.childs, 'tearDown');
        },

        tearDown: function () {
            this.closeChilds();
            BaseView.prototype.tearDown.call(this);
        },

        renderChilds: function () {
            this.closeChilds();

            var $intervalsEl = this.$el;

            this.collection.each(function (ai) {
                var eic = ai.get('executionIntervals');
                eic.each(function (ei) {
                    var intervalView = new IntervalView({
                        model: ei,
                        ai: ai,
                        mainCollection: this.options.mainCollection
                    });

                    this.childs.push(intervalView);
                    $intervalsEl.append(intervalView.render().el);
                }, this);

            }, this);

        },

        render: function () {
            BaseView.prototype.render.call(this);
            this.renderChilds();
            return this;
        },


        data: function () {
            // console.log('data intervals', this.options)
        }
    });
});
