define(function (require) {
    BaseView = require('views/prescriptions/views/BaseView');
    var template = require('text!views/prescriptions/templates/prescriptionsExecution/prescription.html');
    var IntervalsView = require('views/prescriptions/views/prescriptionsExecution/Intervals');

    return BaseView.extend({
        template: template,
        events: {
            'change [data-prescription-select]': 'onSelectPrescription'
        },

        onSelectPrescription: function (e) {
            var $target = this.$(e.target);
            var selected = $target.prop('checked');

            this.model.set('selected', selected);
        },

        tagName: 'tr',

        initialize: function () {
            this.intervalsView = new IntervalsView({
                collection: this.model.get('assigmentIntervals'),
                mainCollection: this.options.mainCollection
            });

            this.addSubViews({
                '.intervals': this.intervalsView
            });
        },

        data: function () {
            var data = this.model.toJSON();
            // console.log('presc data', data);
            return data;
        }
    });


});
