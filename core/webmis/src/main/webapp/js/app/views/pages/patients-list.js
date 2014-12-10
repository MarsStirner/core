define(["collections/patients", "views/grid", "views/filter", "views/paginator"], function () {

    App.Views.PatientsList = View.extend({
        events: {
            "click .AddButton": "onAddButtonClick",
            "click .SearchButton": "onSearchButtonClick"
        },

        initialize: function () {
            this.clearAll();
            checkForErrors(this.options.path, "path is mandatory");
            //checkForErrors (this.options.params, "params is mandatory");

            this.on("template:loaded", this.ready, this);
            this.loadTemplate("pages/patients-list");
        },

        // Загрузился шаблон списка пациентов
        ready: function () {
            var view = this;

            var data = {
                allowCreateAppeal: Core.Data.currentRole() === ROLES.NURSE_RECEPTIONIST
            };

            this.$el.html($("#patients-list-page").tmpl(data));

            // Получим список пациентов
            var PatientsCollection = new App.Collections.Patients;
            this.collection = PatientsCollection;


            // Вставка блока с фильтрами
            var filter = new App.Views.Filter({
                collection: PatientsCollection,
                templateId: "#patients-list-filters",
                path: this.options.path
            });
            this.depended(filter);

            this.$el.prepend(filter.render().el);

            this.filter = filter;

            // Построим таблицу на основе списка
            var PatientsGrid = new App.Views.Grid({
                collection: PatientsCollection,
                template: "grids/patients",
                gridTemplateId: "#patients-grid",
                rowTemplateId: "#patients-grid-row",
                defaultTemplateId: "#patients-grid-row-default",
                errorTemplateId: "#patients-grid-row-error",
                popUpMode: true//this.options.popUpMode
            });
            this.depended(PatientsGrid);

            this.$el.find(".Container").html(PatientsGrid.render().el);

            // PatientsGrid.on("grid:rowClick", function (patient) {
            //     this.trigger("patient:selected", patient);
            // }, this);
            PatientsGrid.on('grid:rowClick', function (model, event) {
                if (event.target.localName != 'a') {
                    var url = '';
                    if (App.Router.currentPage == "appointments") {
                        window.location.href = '/appointments/' + model.get('id');
                    } else {
                        url = '/patients/' + model.get('id') + '/';
                        window.open(url);
                    }
                } else {
                    view.newSendToDepartment(model);
                }
            });

            // Пэйджинатор
            var Paginator = new App.Views.Paginator({
                collection: PatientsCollection
            });
            this.depended(Paginator);
            //this.depended(view.tooltip);

            this.$el.find(".Container").append(Paginator.render().el);

            UIInitialize(this.el);

            this.$("[name=birthDate]").datepicker().mask("99.99.9999");

            this.$(".AddButton").button({
                icons: {
                    primary: "icon-plus icon-color-green"
                }
            });

            this.$(".SearchButton").button({
                icons: {
                    primary: "icon-search"
                }
            });

            this.$("input[name='patientCode']").on("keypress", function (event) {
                if (event.which < 48 || event.which > 57) {
                    event.preventDefault();
                    event.stopPropagation();
                }
            });

        },

        onAddButtonClick: function (event) {
            if (this.options.popUpMode) {
                event.preventDefault();
                this.trigger("patient:newClick");
            } else {
                App.Router.navigate("/patients/new/", {
                    trigger: true
                });
            }
        },

        onSearchButtonClick: function (event) {
            this.filter.refresh();
        },

        render: function () {
            return this;
        }
    });


});
