define(function (require) {
    var template = require('text!views/prescriptions/templates/appeal/prescriptions-actions.html');
    var BaseView = require('views/prescriptions/views/BaseView');
    var PrescriptionNewView = require('views/prescriptions/views/appealPrescriptions/PrescriptionNewView');
    var CreatePrescriptionButton = require('views/prescriptions/views/appealPrescriptions/CreatePrescriptionButton');

    return BaseView.extend({
        template: template,
        initialize: function () {
            var createPrescriptionButtonView = new CreatePrescriptionButton({
                appeal: this.options.appeal
            });
            this.addSubViews({
                '.create-prescription-button': createPrescriptionButtonView
            });


        },
        data: function () {
            var data = {
                appeal: this.options.appeal.toJSON()
            };
            console.log('data', data)
            return data;
        },
        createPrescription: function () {
            var popup = new PrescriptionNewView({
                actionTypeId: 754,
                appeal: this.options.appeal,
                collection: this.options.collection
            });

            popup.render();
            popup.open();

        },
        afterRender: function () {
        //    this.$el.find('button').button();
        }
    });

});
