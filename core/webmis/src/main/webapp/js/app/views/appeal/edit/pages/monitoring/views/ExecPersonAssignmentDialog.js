define(function (require) {
    var shared = require('views/appeal/edit/pages/monitoring/shared');

    var assignExecPersonDialogTmpl = require('text!templates/appeal/edit/pages/monitoring/assign-exec-person-dialog.tmpl');

    var BaseView = require('views/appeal/edit/pages/monitoring/views/BaseView');



    /**
     * Диалог назначения врача
     * @type {*}
     */
    var ExecPersonAssignmentDialog = BaseView.extend({
        template: assignExecPersonDialogTmpl,

        data: function () {
            return {
                assignMe: this.assignMe
            };
        },

        events: {
            "change input[name='filter-persons']": "onFilterPersonsChange",
            "click .assign-on-me": "onAssignOnMeClick"
        },

        initialize: function (options) {
            BaseView.prototype.initialize.apply(this);
            _.bindAll(this);
            this.appeal = options.appeal;
            this.allPersons = options.allPersons;
            this.departmentPersons = options.departmentPersons;
            this.appealExecPerson = options.appealExecPerson;
            this.collections = [];

            //Все врачи
            this.allPersons.setParams({
                limit: 999,
                sortingField: 'lastname'
            });
            //this.allPersons.on("reset", this.addAllPersons, this).fetch();

            this.getCollection('allPersons');
            // console.log('col', this.getCollection('allPersons'))

            //Врачи отделения
            this.departmentPersons.setParams({
                limit: 999,
                sortingField: 'lastname',
                filter: {
                    departmentId: this.appeal.get("currentDepartment").id
                }
            });
            this.getCollection('departmentPersons');
            // this.departmentPersons.on("reset", this.addDepartmentPersons, this).fetch();

            this.assignMe = true;

            if ((Core.Cookies.get("currentRole") === 'nurse-department') || (Core.Cookies.get("currentRole") === 'nurse-receptionist')) {
                this.assignMe = false;
            }

        },

        getCollection: function (collectionName) {
            if (!this.collections[collectionName]) {
                this[collectionName].fetch();
                this.collections[collectionName] = this[collectionName];
            }
            return this.collections[collectionName];
        },

        open: function () {
            this.$el.dialog({
                title: "Назначение лечащего врача",
                modal: true,
                width: "50em",
                dialogClass: "webmis",
                resizable: false,
                buttons: [{
                    text: "Назначить",
                    click: this.assignExecPerson,
                    "class": "button-color-green"
                }, {
                    text: "Отмена",
                    click: this.close
                }],
                close: this.close
            });

            this.$el.css({
                "min-height": "11em"
            });
        },

        close: function () {
            console.log('close')
            this.$el.dialog("close");
            this.allPersons.off(null, null, this);
            this.departmentPersons.off(null, null, this);
            // this.off(null, null, this).remove();
            // this.remove();
        },

        onFilterPersonsChange: function (event) {
            this.$(".all-persons,.department-persons").select2("val", "");

            var selectedFilter = this.getSelectedFilter();

            if (selectedFilter === "all") {
                this.$(".all-persons.select2-container").show();
                this.$(".department-persons.select2-container").hide();
            } else if (selectedFilter === "dep") {
                this.$(".all-persons.select2-container").hide();
                this.$(".department-persons.select2-container").show();
            }
        },

        onAssignOnMeClick: function (event) {
            event.preventDefault();

            var currentUserId = Core.Cookies.get("userId");

            this.$("input[name='filter-persons'][value='all']").prop("checked", true).change();
            this.$(".all-persons").select2("val", currentUserId).change();
        },

        getSelectedFilter: function () {
            return this.$("input[name='filter-persons']:checked").val();
        },

        assignExecPerson: function () {
            var selectedFilter = this.getSelectedFilter();

            var selectedExecPersonId;

            if (selectedFilter === "all") {
                selectedExecPersonId = this.$(".all-persons").select2("val");
            } else if (selectedFilter === "dep") {
                selectedExecPersonId = this.$(".department-persons").select2("val");
            }

            if (selectedExecPersonId) {
                var appealExecPerson = this.appealExecPerson;
                appealExecPerson.save({
                    id: selectedExecPersonId
                });

                this.appeal.set("execPerson", this.allPersons.get(selectedExecPersonId).toJSON());

                this.close();
            }
        },

        addAllPersons: function () {
            var allPersons = this.getCollection('allPersons');
            this.$(".all-persons").append(allPersons.map(function (person) {
                return "<option value='" + person.get('id') + "'>" + person.get("name").raw + "</option>";
            })).select2("enable");
        },

        addDepartmentPersons: function () {
            var departmentPersons = this.getCollection('departmentPersons');
            this.$(".department-persons").append(departmentPersons.map(function (person) {
                return "<option value='" + person.get('id') + "'>" + person.get("name").raw + "</option>";
            })).select2("enable");
        },

        render: function () {
            BaseView.prototype.render.apply(this);
            this.addAllPersons();
            this.addDepartmentPersons();

            this.$("#filter-persons-container").buttonset();
            this.$(".all-persons, .department-persons").select2({
                matcher: function (term, text, opt) {
                    return text.split(' ')[0].toUpperCase().indexOf(term.toUpperCase()) >= 0
                }
            }) //.select2("disable");
            this.$(".all-persons").hide();

            return this;
        }
    });

    return ExecPersonAssignmentDialog;

});
