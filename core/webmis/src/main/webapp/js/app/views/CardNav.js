define(function (require) {
    var template = require('text!templates/card-nav.html');
    //    var _ = require('underscore');

    var CardNav = Backbone.View.extend({
        template: _.template(template),
        initialize: function () {
            _.bindAll(this, 'render');
            if (!this.options.patient) {
                throw new Error('patient option required');
            }
            if (!this.options.structure) {
                throw new Error('structure option required');
            }

        },
        filterByPermissions: function (itemPermissions, permissions) {
            var difference = _.difference(itemPermissions, permissions);
            return !difference.length;
        },
        data: function () {
            var data = {};
            var patient = this.options.patient;
            var patientName = patient.get('name');

            data.patientName = patientName.get('raw');
            data.patientAge = Core.Date.getAgeString(patient.get('birthDate'));
            data.patient = this.options.patient.toJSON();
            data.structure = _.filter(this.options.structure, function (item) {
                return this.filterByPermissions(item.permissions, this.options.permissions);
            }, this);
            //console.log('data', data);

            return data;
        },
        render: function () {
            this.$el.html(this.template(this.data()));
            return this;
        }
    });

    return CardNav;
});
