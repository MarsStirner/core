define(function(require) {
    // var shared = require('views/appeal/edit/pages/monitoring/shared');

    var patientBloodTypeHistoryRowTmpl = require('text!templates/appeal/edit/pages/monitoring/patient-blood-type-history-row.tmpl');

    var BaseView = require('views/appeal/edit/pages/monitoring/views/BaseView');

    var PatientBloodTypeHistoryRow = BaseView.extend({
        template: patientBloodTypeHistoryRowTmpl,

        data: function() {
            return {
                bloodTypeHistory: this.collection
            };
        },

        events: {

        },

        initialize: function(options) {
            BaseView.prototype.initialize.apply(this);

            this.collection
                .on("request:show", this.toggleVisible, this)
                .on("request:hide", this.toggleVisible, this)
            //.on("add", this.render, this)
            .on("reset", this.render, this)
                .fetch();
        },

        toggleVisible: function(event) {
            this.$el.toggle();
        }
    });

    return PatientBloodTypeHistoryRow;


});
