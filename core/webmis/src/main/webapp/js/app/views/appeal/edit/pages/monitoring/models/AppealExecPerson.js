define(function(require){
	var shared = require('views/appeal/edit/pages/monitoring/shared');

    var AppealExecPerson = Backbone.Model.extend({
        idAttribute: "id",

        sync: function (method, model, options) {
            options.dataType = "jsonp";
            options.url = model.url();
            options.contentType = 'application/json';

            /*if (method == "create" || method == "update") {
             options.data = JSON.stringify({
             requestData: {},
             data: model.toJSON()
             });
             }*/
            return Backbone.sync(method, model, options);
        },


        url: function () {
            return DATA_PATH + "appeals/" + shared.models.appeal.get("id") + "/execPerson"
        }
    });

    return  AppealExecPerson;

});
