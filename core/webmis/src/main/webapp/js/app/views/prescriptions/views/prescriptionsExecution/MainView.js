define(function (require) {
    var template = require('text!views/prescriptions/templates/prescriptionsExecution/list-main.html');

    var BaseView = require('views/prescriptions/views/BaseView');

    var Prescriptions = require('views/prescriptions/collections/Prescriptions');
    var FilterView = require('views/prescriptions/views/prescriptionsExecution/Filter');
    var ActionsView = require('views/prescriptions/views/prescriptionsExecution/Actions');
    var GroupsView = require('views/prescriptions/views/prescriptionsExecution/Groups');
    // var gremlins = require('gremlins');


    return BaseView.extend({
        className: 'ContentHolder',
        template: template,
        initialize: function () {
            var self = this;

            this.collection = new Prescriptions();


            this.groupsView = new GroupsView({
                collection: this.collection
            });

            this.filterView = new FilterView({
                collection: this.collection,
                model: new Backbone.Model({
                    'groupBy': 'moa' 
                })
            });


            this.actionsView = new ActionsView({
                collection: this.collection
            });

            this.addSubViews({
                '.groups-el': this.groupsView,
                '.filter': this.filterView,
                '.actions': this.actionsView
            });

            this.filterView.filter();

            pubsub.on('intervals:executed intervals:updated', this.refresh, this);
        },

        refresh: function () {
            this.collection.fetch();
        },

        afterRender: function(){
            // gremlins.createHorde().unleash(); 
        }

    });

});
