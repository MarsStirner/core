define(function(require){
    var Model = Backbone.Model.extend({

        parse: function (data) {
            return data.data ? data.data : data
        },

        fetch: function (options) {
            options = options || {};
            this.trigger("fetch", this);

            return Backbone.Model.prototype.fetch.call(this, options);
        },



        sync: function (method, model, options) {
            options.dataType = "jsonp";
            options.url = model.url();
            options.contentType = 'application/json';

            if (method == "create" || method == "update") {
                options.data = JSON.stringify({
                    requestData: {},
                    data: model.toJSON()
                });
            }
            return Backbone.sync(method, model, options);
        }//,
//        errorHandler: function (model, xhr) {
//            if (xhr.responseText && xhr.responseText.length) {
//                try {
//                    var json = JSON.parse(xhr.responseText);
//
//                    if (json.errorCode == 3) {
//                        Core.Cookies.clear();
//                        //window.location.href = "/auth/";
//                        return;
//                    } else if (json.errorCode == 261) {
//                        json.errorMessage += ", обновите страницу."
//                    }
//
//                    if (json && json.errorMessage) {
//
//                        showError(json.errorMessage);
//                        return true;
//                    }
//                } catch (e) {
//                }
//
//                showError(xhr.responseText);
//            }
//        },
//        initialize: function () {
//            this.on("error", this.errorHandler, this);
//        }
    });



    return Model;
});