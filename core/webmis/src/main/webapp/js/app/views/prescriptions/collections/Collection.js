define(function(require){
    var Collection = Backbone.Collection.extend({
        initialize: function () {
            this._params = {
//                filter: {},
//                sortingField: "id",
//                sortingMethod: "asc",
//                limit: 10,
//                page: 1,
//                recordsCount: 0
            };
        },

        sync: function (method, model, options) {
            options.dataType = options.dataType ? options.dataType : "jsonp";

            return Backbone.sync(method, model, options);
        },

        getParams: function () {
            return this._params
        },
        setParams: function (obj) {
            this._params = $.extend(this._params, obj);

            return this._params
        },


        parse: function (data) {
            checkForWarnings(data.requestData, "requestData was not found in the JSON");
            this.requestData = data.requestData || {};
            this.requestData.filter = this.requestData.filter || {};

//            if (data.requestData && this.requestData.coreVersion) {
//                CORE_VERSION = data.requestData.coreVersion;
//                VersionInfo.show();
//            }

            return data.data
        },

        fetch: function (options) {
            options = options || {};

            this.trigger("fetch", this);

            var data = $.extend(this.getParams(), options.data);

            var errorHandler = $.extend(function (model, xhr) {
                // TODO Отрабатывать ошибки
                if (xhr && xhr.responseText && xhr.responseText.length) {
                    var json;
                    try {
                        json = JSON.parse(xhr.responseText);
                    } catch (e) {

                    }
                    if (json && json.errorCode == 3) {
                        Core.Cookies.clear();
                        window.location.href = "/auth/";
                        return;
                    }
                    showError(xhr.responseText);
                }
            }, options.error);


            return Backbone.Collection.prototype.fetch.call(this, $.extend(options, {
                data: data,
                error: errorHandler
            }));
        }
    });

    return Collection;
});