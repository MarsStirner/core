define(function (require) {
    var groups = require('text!views/prescriptions/templates/prescriptionsExecution/groups.html');
    var BaseView = require('views/prescriptions/views/BaseView');
    var GroupView = require('views/prescriptions/views/prescriptionsExecution/Group');


    return BaseView.extend({
        template: groups,

        initialize: function () {
            this.childs = [];

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
            this.$el.html('Нет данных.');
        },

        renderOnFetch: function () {
            this.$el.html('Загружаем...');
        },

        closeChilds: function () {
            _.invoke(this.childs, 'tearDown');
        },

        tearDown: function () {
            this.closeChilds();
            BaseView.prototype.tearDown.call(this);
        },

        renderChilds: function () {
            this.closeChilds();

            var data = this.data();
            var groups = data.groups;
            var $groupsEl = this.$el.find('.groups');

            _.each(groups, function (group, groupName) {
                var groupView = new GroupView({
                    group: group,
                    groupName: groupName,
                    mainCollection: this.collection
                });
                this.childs.push(groupView);
                $groupsEl.append(groupView.render().el);

            }, this);

        },

        render: function () {
            BaseView.prototype.render.call(this);
            this.renderChilds();
            return this;
        },
        group: function (model) {
            var groupName = this.collection._filter.groupBy;
            var grouper = '';

            switch (groupName) {
            case 'name':
            case 'moa':
                grouper = model.get(groupName);
                break;
            case 'client':
                var client = model.get('client');
                grouper = client.lastName + ' ' + client.firstName + ' ' + client.middleName;
                break;
            case 'doctor':
                var doctor = model.get('doctor');
                grouper = doctor.lastName + ' ' + doctor.firstName + ' ' + doctor.middleName;
                break;
            case 'createPerson':
                var createPerson = model.get('createPerson');
                grouper = createPerson.lastName + ' ' + createPerson.firstName + ' ' + createPerson.middleName;
                break;
            case 'interval':
                var ais = model.get('assigmentIntervals');
                var rangeType = ais.first().get('executionIntervals').first().get('endDateTime') ? 'Интервал':'Однократно';
                grouper = rangeType;
                break;



            }

            return grouper;

        },
        data: function () {
            var data = {};

            data.groups = this.collection.groupBy(this.group, this);

            // console.log('data', data);
            return data;
        }
    });

});
