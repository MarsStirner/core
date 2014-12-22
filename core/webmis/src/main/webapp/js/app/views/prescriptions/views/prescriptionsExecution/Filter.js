define(function (require) {
    var BaseView = require('views/prescriptions/views/BaseView');
    var DateRangeView = require('views/prescriptions/views/DateRangeView');
    var SelectView = require('views/prescriptions/views/SelectView');
    var rivets = require('rivets');
    var template = require('text!views/prescriptions/templates/prescriptionsExecution/filter.html');
    require('collections/departments');
    var AdministrationMethod = require('collections/AdministrationMethod');

    return BaseView.extend({
        template: template,
        events: {
            'keyup #doctor-name': 'onKeyUp',
            'keyup #drug-name': 'onKeyUp',
            'keyup #pacient-name': 'onKeyUp',
            'click #clear-filter': 'clearFilter'
        },

        initialize: function () {
            var self = this;
            this.listenTo(this.model, 'change', this.filter);
            // this.listenTo(this.model, 'change:groupBy', this.rerender);
            this.listenTo(this.model, 'change', this.setUrlParams);

            this.model.set(this.getUrlParams());

            this.dateRangeView = new DateRangeView({
                model: this.model
            });

            this.administration = new AdministrationMethod();

            this.administrationView = new SelectView({
                collection: this.administration,
                model: this.model,
                modelKey: 'administrationId'
            });


            this.departments = new App.Collections.Departments();
            this.departments.setParams({
                filter: {
                    hasBeds: true
                },
                limit: 0,
                sortingField: 'name',
                sortingMethod: 'asc'
            });

            this.departmentsView = new SelectView({
                collection: this.departments,
                model: this.model,
                modelKey: 'departmentId'
            });

            this.administration.fetch()
                .done(function () {});

            this.departments.fetch()
                .done(function () {});


            this.addSubViews({
                '#date-range': this.dateRangeView,
                '#departments': this.departmentsView,
                '#administration': this.administrationView
            });

        },

        clearFilter: function () {
            this.model.set({
                'pacientName': '',
                'setPersonName': '',
                'drugName': '',
                'departmentId': 'not-selected',
                'administrationId': 'not-selected',
                'groupBy': 'moa'
            });
        },

        onKeyUp: function (e) {
            // $target = this.$(e.target);
            // var value = $target.val();
            // if(value && value.length > 2){
            // 	$target.trigger('change');
            // }
            // console.log('keyup', $target.val());
        },

        getUrlParams: function () {
            return Core.Url.extractUrlParameters();
        },

        setUrlParams: function () {
            var params = $.param(this.model.toJSON());
            App.Router.navigate('prescriptions/?' + params);
        },

        filter: function (model, options) {
            var filter = this.model.toJSON();
            this.collection._filter = filter;

            if (options && options.changes && (_.keys(options.changes)).length === 1 && options.changes.groupBy) {
                // console.log('filter', options.changes);
                this.rerender();
            } else {
                if (_.isObject(this.lastXHR)) {
                    // прерываем предыдущий запрос если он не успел выполнится
                    if (this.lastXHR && this.lastXHR.readyState != 4) {
                        this.lastXHR.abort('stale');
                    }
                }

                this.lastXHR = this.collection.fetch();
            }

        },

        rerender: function () {
            var filter = this.model.toJSON();

            this.collection._filter = filter;
            this.collection.trigger('reset');
        },

        afterRender: function () {

            rivets.bind(this.el, {
                filter: this.model
            });

            var $groupBy = this.$el.find('#groupBy');
            $groupBy.select2();

            this.listenTo(this.model, 'change:groupBy', function () {
                $groupBy.trigger('change');
            });


            this.$('button').button();

        }
    });

});
