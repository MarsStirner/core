define(function (require) {
    // var _ = require('underscore');

    var Template = Model.extend({});

    var Templates = Collection.extend({
        model: Template,
        _fields: ['id', 'name', 'hasPopApp'],
        _printContext: '',

        initialize: function (models, options) {
            options = options || {};

            if (options.fields) {
                this.setFields(options.fields);
            }
            if (options.printContext) {
                this.setPrintContext(options.printContext);
            }
        },

        setFields: function (fields) {
            if (!_.isArray(fields)) {
                throw new Error('коллекция Templates: параметр fields должен быть массивом');
            }
            this._fields = fields;

            return this;
        },

        setPrintContext: function (printContext) {
            this._printContext = printContext;

            return this;
        },

        getFields: function () {
            return this._fields;
        },

        getPrintContext: function () {
            return this._printContext;
        },

        getFieldsFilter: function () {
            return this.getFields().join(',');
        },

        url: function () {
            var params = [];

            if (_.isEmpty(this.getPrintContext())) {
                throw new Error('коллекция Templates: printContext обязательный параметр');
            } else {
                params.push('context=' + this.getPrintContext());
            }

            if (!_.isEmpty(this.getFields())) {
                params.push('fields=' + this.getFieldsFilter());
            }

            // params.push('filter[render]=1');

            return DATA_PATH + 'printTemplate/byContexts/?' + params.join('&');
        },

        parse: function(data){
            return data; 
        }
    });

    return {
        Model: Template,
        Collection: Templates
    };

});
