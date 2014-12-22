define(function (require) {
    var Model = require('./Model');

    var PrescriptionTemplate = Model.extend({
        initialize: function(a, options){
            console.log('pt', arguments)
            this.actionTypeId = options.actionTypeId;
        },
        url: function(){
            return '/api/v1/prescriptions/template/'+this.actionTypeId;
        }


    });

    return PrescriptionTemplate;
});
