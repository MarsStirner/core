define(function (require) {
    var Model = require('./Model');
    var Drugs = require('../collections/Drugs');
    var Intervals = require('../collections/Intervals');
    var Collection = require('../collections/Collection');
    var PrescriptionTemplate = require('./PrescriptionTemplate');

    var Prescription = Model.extend({
        initialize: function (model, options) {
            var self = this;
            this.actionTypeId = options.actionTypeId || false;
            this.initialized().then(function () {
                if (self.getPropertyByCode('moa')) {
                    self.set('moaId', self.getPropertyByCode('moa').get('valueId'));
                    self.set('moa', self.getPropertyByCode('moa').get('value'));
                }
                if (self.getPropertyByCode('voa')) {
                    self.set('voa', self.getPropertyByCode('voa').get('value'));
                }


            });
        },
        urlRoot: function () {
            return '/api/v1/prescriptions/';
        },
        initialized: function () {
            var dfd = $.Deferred();
            var self = this;
            if (self.actionTypeId) {
                self.template = new PrescriptionTemplate({}, {
                    actionTypeId: self.actionTypeId
                });

                self.template.fetch()
                    .done(function () {
                        self.set(self.template.toJSON());
                        self.initCollections(dfd.resolve);
                    });

            } else {
                self.initCollections(dfd.resolve);
            }

            return dfd.promise();
        },
        initCollections: function (callback) {
            if (this.get('drugs')) {
                var drugs = new Drugs(this.get('drugs'));

                drugs.on('add remove change', this.triggerChange, this);
                this.set('drugs', drugs);
            }

            if (this.get('assigmentIntervals')) {
                var assigmentIntervals = new Intervals(this.get('assigmentIntervals'));
                assigmentIntervals.on('add remove change', this.triggerChange, this);
                assigmentIntervals.each(function (interval) {
                    if (interval.get('executionIntervals')) {
                        var executionIntervals = new Intervals(interval.get('executionIntervals'));
                        executionIntervals.on('add remove change', this.triggerChange, this);
                        interval.set('executionIntervals', executionIntervals);
                    }
                });

                this.set('assigmentIntervals', assigmentIntervals);
            }

            if (this.get('properties')) {
                var properties = new Collection(this.get('properties'));
                this.set('properties', properties);
            }
            if (callback) {
                callback();
            }
        },
        getPatientFio: function () {
            var client = this.get('client');
            var fio = client.lastName + ' ' + client.firstName + ' ' + client.middleName;
            return fio;
        },
        getPatientBirthDate: function () {
            var client = this.get('client');
            var birthDate = client.birthDate;

            return birthDate;
        },
        getDepartment: function(){
        
        },
        triggerChange: function () {
            this.trigger('change');
        },

        toJSON: function () {
            var attributes = _.clone(this.attributes);

            _.each(attributes, function (value, key) {
                if (value && _.isFunction(value.toJSON)) {
                    attributes[key] = value.toJSON();
                }
            });

            return attributes;
        },

        getProperty: function (key, value) {
            var model;
            if (this.get('properties')) {
                model = this.get('properties')
                    .find(function (model) {
                        return model.get(key) === value;
                    });
            }

            return model;
        },
        getPropertyByCode: function (value) {
            return this.getProperty('code', value);
        },
        getPropertyByName: function (value) {
            return this.getProperty('name', value);
        },

        addInterval: function () {
            this.get('assigmentIntervals')
                .addInterval();
        },

        set: function (key, value, options) {
            var attr;
            // Normalize the key-value into an object
            if (_.isObject(key) || key === null) {
                attrs = key;
                options = value;
            } else {
                attrs = {};
                attrs[key] = value;
            }

            // Go over all the set attributes and make your changes
            for (attr in attrs) {
                if (!this.get('properties')) {
                    break;
                }

                if (attr === 'moa') {
                    var moaModel = this.getPropertyByCode('moa');
                    if (moaModel) {
                        moaModel.set('value', attrs[attr]);
                    }
                }

                if (attr === 'moaId') {
                    var moaIdModel = this.getPropertyByCode('moa');
                    if (moaIdModel) {
                        moaIdModel.set('valueId', parseInt(attrs[attr], 10));
                    }
                }


                if (attr === 'voa') {
                    var voaModel = this.getPropertyByCode('voa');
                    if (voaModel) {
                        voaModel.set('value', attrs[attr]);
                    }
                }

            }

            return Backbone.Model.prototype.set.call(this, attrs, options);
        }

    });

    return Prescription;
});
