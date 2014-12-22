define(function (require) {
    var template = require('text!views/prescriptions/templates/appeal/drug-ballance.html');
    var BaseView = require('views/prescriptions/views/BaseView');

    return BaseView.extend({
        template: template,
        initialize: function () {
            this.collection.on('reset', function () {
                if (this.collection.length) {
                    this.render();
                } else {
                    this.renderNoResults();
                }
            }, this);
            this.collection.on('fetch', this.renderOnFetch, this);
        },
        renderNoResults: function () {
            this.$el.html('Ничего не нашли.');
        },
        renderOnFetch: function () {
            //console.log('onFetch', this.cid, this.$el.html());

            this.$el.html('Ищем...');
        },


        inHospital: function () {
            var filtered = [];

            filtered = this.collection.filter(function (model) {
                return 1 === model.get('storageOrgstructureId');
            });

            return filtered;
        },


        inCurrentDepartment: function () {
            var filtered = [];
            var currentDepartment = this.options.appeal.get('currentDepartment');
            if (currentDepartment) {
                var currentDepartmentId = currentDepartment.id;

                filtered = this.collection.filter(function (model) {
                    return currentDepartmentId === model.get('storageOrgstructureId');
                });
            }

            return filtered;
        },

        inOtherDepartments: function () {
            var filtered = [];

            var currentDepartment = this.options.appeal.get('currentDepartment');
            if (currentDepartment) {
                var currentDepartmentId = currentDepartment.id;

                filtered = this.collection.filter(function (model) {
                    var storageOrgstructureId = model.get('storageOrgstructureId');

                    return (currentDepartmentId != storageOrgstructureId) && (1 != storageOrgstructureId);
                });
            }


            return filtered;
        },

        data: function () {
            var data = {};
            var inCurrentDepartment = this.inCurrentDepartment();
            var inOtherDepartments = this.inOtherDepartments();
            var inHospital = this.inHospital();
            var items = [];

            // data.items = this.collection.toJSON();
            data.where = false;

            if (inHospital.length) {
                data.where = 'inHospital';
                items = _.map(inOtherDepartments, function (model) {
                    return model.toJSON();
                });
                data.item = items[0];
            }


            if (inOtherDepartments.length) {
                data.where = 'inOtherDepartments';
                items = _.map(inOtherDepartments, function (model) {
                    return model.toJSON();
                });
                data.item = items[0];
            }

            if (inCurrentDepartment.length) {
                data.where = 'inCurrentDepartment';
                items = _.map(inCurrentDepartment, function (model) {
                    return model.toJSON();
                });
                data.item = items[0];
            }
            return data;
        }
    });

});
