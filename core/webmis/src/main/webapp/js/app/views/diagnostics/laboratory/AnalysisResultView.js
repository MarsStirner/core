//страница просмотра результатов лабораторного исследования

define(function (require) {
    var template = require('text!templates/diagnostics/laboratory/laboratory-result.html');
    var Result = require('models/diagnostics/laboratory/laboratory-diag-form');
    var Biomaterials = require('collections/biomaterials/Biomaterials');
    var ContextPrintButton = require('views/ContextPrintButton');
    var BakResult = require('models/diagnostics/laboratory/bak-result');

    require('views/print');


    var LaboratoryResultView = Backbone.View.extend({
        template: template,
        initialize: function () {
            console.log('init lab results', arguments);

            var self = this;
            this.result = new Result();
            this.result.eventId = this.options.appealId;
            this.result.id = this.options.modelId ? this.options.modelId : this.options.subId[0];

            this.bakResult = new BakResult();
            this.bakResult.diagnosticId = this.result.id;


            this.result.fetch().then(function () {
                // self.result.set('mnem', 'BAK_LAB')
                var mnem = self.result.get('mnem');
                if (mnem && mnem.toString().toUpperCase() === "BAK_LAB") {
                    self.bakResult.fetch({
                        success: function(){
                            self.render2();
                        },
                        error: function(){
                            self.render2();
                        }
                    })
                } else {
                    self.render2();
                }
            });


        },

        renderContextPrintButton: function(){
            this.contextPrintButton = new ContextPrintButton({
                context: this.result.get('context'),
                data: {
                    action_id:this.result.get('id'),
                    event_id: this.options.appeal.get('id'),
                    context_type: 'action',
                    client_id: this.options.appeal.get('patient').get('id'),
                    additional_context: {
                        currentOrgStructure: '',
                        currentOrganisation: 3479,
                        currentPerson: Core.Cookies.get('userId')
                    }
                }
            });

            this.contextPrintButton.setElement(this.$el.find('.context-print-button'));
            this.contextPrintButton.render();


        },
        events: {
            // "click .first": "first",
            // "click .prev": "prev",
            // "click .next": "next",
            // "click .last": "last",
            "click .extra": "extra",
            // "click .print": "print",
            "click .all": "openLabs"
        },

        printOptions: function () {
            var self = this;

            return {
                label: "Печать",
                scope: self,
                handler: self.printResultOfLaboratory,
                dropDownItems: [{
                    label: "Результат исследования",
                    handler: self.printResultOfLaboratory
                }, {
                    label: "Направление на исследование",
                    handler: self.printDirectionForLaboratory
                }, {
                    label: "Направление на исследование (без показателей)",
                    handler: self.printDirectionForLaboratorySimple
                }]
            }
        },

        getJobTicket: function () {
            var jobTicketId = this.result.getProperty('Время забора');
            var beginDate = moment(this.result.getProperty('plannedEndDate'), 'YYYY-MM-DD HH:mm:ss').valueOf();
            var departmentId = this.options.appeal.get('currentDepartment').id;

            var filter = {
                departmentId: departmentId,
                beginDate: beginDate,
                endDate: beginDate + (60 * 60 * 24 * 1000)
            };

            if (jobTicketId) {
                filter.jobTicketId = jobTicketId;
            }

            this.biomaterials = new Biomaterials();
            this.biomaterials.setParams({
                filter: filter
            });
            return this.biomaterials.fetch();
        },

        printResultOfLaboratory: function () {
            var self = this;
            var result = this.resultData();

            this.getJobTicket().done(function () {
                var biomaterial = self.biomaterials.first();
                var actions = biomaterial ? biomaterial.get('actions') : [];
                var action = _.find(actions, function (action) {
                    return action.id === result.id
                });

                var rbTissueTypeName = '';
                var rbTestTubeTypeNamе = '';
                var jobTicketDatetime = '';
                var takenTissueJournal = '';

                if (action) {
                    if (action.tubeType && action.tubeType.name) {
                        rbTestTubeTypeNamе = action.tubeType.name;
                    }
                    if (action.biomaterial && action.biomaterial.tissueType && action.biomaterial.tissueType.name) {
                        rbTissueTypeName = action.biomaterial.tissueType.name;
                    }
                    if (action.jobTicket && action.jobTicket.date) {
                        jobTicketDatetime = moment(action.jobTicket.date).format('DD.MM.YYYY HH:mm:ss');
                    }
                    if (action.takenTissueJournal) {
                        takenTissueJournal = action.takenTissueJournal
                    }
                }
                var plannedEndDate = moment(self.result.getProperty('plannedEndDate'), 'YYYY-MM-DD HH:mm:ss').format('DD.MM.YYYY HH:mm:ss');

                new App.Views.Print({
                    data: {
                        age: result.age2,
                        appealNumber: result.appealNumber,
                        assignDoctor: result.assigner,
                        department: result.department,
                        executorDoctor: result.executor,
                        id: result.id,
                        jobTicketDatetime: jobTicketDatetime,
                        mkb: result.mkb,
                        name: result.name,
                        patientBirthday: result.patientBirthday,
                        patientName: result.patientName,
                        patientSex: result.patientSex,
                        payments: result.payments,
                        plannedEndDate: plannedEndDate,
                        rbTestTubeTypeNamе: rbTestTubeTypeNamе,
                        rbTissueTypeName: rbTissueTypeName,
                        takenTissueJournal: takenTissueJournal,
                        tests: result.tests
                    },
                    template: "resultOfLaboratory"
                });
            });
        },

        printDirectionForLaboratory: function () {
            var self = this;
            var result = this.resultData();

            this.getJobTicket().done(function () {
                var biomaterial = self.biomaterials.first();
                var actions = biomaterial ? biomaterial.get('actions') : [];
                var action = _.find(actions, function (action) {
                    return action.id === result.id
                });

                var rbTissueTypeName = '';
                var rbTestTubeTypeNamе = '';
                var jobTicketDatetime = '';
                var takenTissueJournal = '';

                if (action) {
                    if (action.tubeType && action.tubeType.name) {
                        rbTestTubeTypeNamе = action.tubeType.name;
                    }
                    if (action.biomaterial && action.biomaterial.tissueType && action.biomaterial.tissueType.name) {
                        rbTissueTypeName = action.biomaterial.tissueType.name;
                    }
                    if (action.jobTicket && action.jobTicket.date) {
                        jobTicketDatetime = moment(action.jobTicket.date).format('DD.MM.YYYY HH:mm:ss');
                    }
                    if (action.takenTissueJournal) {
                        takenTissueJournal = action.takenTissueJournal
                    }
                }

                var plannedEndDate = moment(self.result.getProperty('plannedEndDate'), 'YYYY-MM-DD HH:mm:ss').format('DD.MM.YYYY HH:mm:ss');

                new App.Views.Print({
                    data: {
                        age: result.age2,
                        appealNumber: result.appealNumber,
                        assignDoctor: result.assigner,
                        department: result.department,
                        executorDoctor: result.executor,
                        id: result.id,
                        jobTicketDatetime: jobTicketDatetime,
                        mkb: result.mkb,
                        name: result.name,
                        patientBirthday: result.patientBirthday,
                        patientName: result.patientName,
                        patientSex: result.patientSex,
                        payments: result.payments,
                        plannedEndDate: plannedEndDate,
                        rbTestTubeTypeNamе: rbTestTubeTypeNamе,
                        rbTissueTypeName: rbTissueTypeName,
                        takenTissueJournal: takenTissueJournal,
                        tests: result.tests
                    },
                    template: "directionForLaboratory"
                });
            });
        },

        printDirectionForLaboratorySimple: function () {
            var self = this;
            var result = this.resultData();

            this.getJobTicket().done(function () {
                var biomaterial = self.biomaterials.first();
                var actions = biomaterial ? biomaterial.get('actions') : [];
                var action = _.find(actions, function (action) {
                    return action.id === result.id
                });

                var rbTissueTypeName = '';
                var rbTestTubeTypeNamе = '';
                var jobTicketDatetime = '';
                var takenTissueJournal = '';
                if (action) {
                    if (action.tubeType && action.tubeType.name) {
                        rbTestTubeTypeNamе = action.tubeType.name;
                    }
                    if (action.biomaterial && action.biomaterial.tissueType && action.biomaterial.tissueType.name) {
                        rbTissueTypeName = action.biomaterial.tissueType.name;
                    }
                    if (action.jobTicket && action.jobTicket.date) {
                        jobTicketDatetime = moment(action.jobTicket.date).format('DD.MM.YYYY HH:mm:ss');
                    }
                    if (action.takenTissueJournal) {
                        takenTissueJournal = action.takenTissueJournal
                    }
                }

                var plannedEndDate = moment(self.result.getProperty('plannedEndDate'), 'YYYY-MM-DD HH:mm:ss').format('DD.MM.YYYY HH:mm:ss');

                new App.Views.Print({
                    data: {
                        age: result.age2,
                        appealNumber: result.appealNumber,
                        assignDoctor: result.assigner,
                        department: result.department,
                        executorDoctor: result.executor,
                        id: result.id,
                        jobTicketDatetime: jobTicketDatetime,
                        mkb: result.mkb,
                        name: result.name,
                        patientBirthday: result.patientBirthday,
                        patientName: result.patientName,
                        patientSex: result.patientSex,
                        payments: result.payments,
                        plannedEndDate: plannedEndDate,
                        rbTestTubeTypeNamе: rbTestTubeTypeNamе,
                        rbTissueTypeName: rbTissueTypeName,
                        takenTissueJournal: takenTissueJournal,
                        tests: result.tests
                    },
                    template: "directionForLaboratorySimple"
                });
            });
        },

        showPrintBtn: function (options) {
            if (options) {
                var $printBtnHolder = $("<div/>");
                var $printBtn = $('<button class="PrintBtn"/>');

                $printBtn.button({
                    label: options.label
                }).click(function (event) {
                    event.preventDefault();
                    options.handler.apply(options.scope);
                });

                $printBtnHolder.append($printBtn);

                if (options.dropDownItems && options.dropDownItems.length) {
                    var $dropDown = $(
                        '<div class="DDList" style="display: block; left: -200px;">' +
                        '<div class="Content ButtonContent" style="top: 0; max-height: 30em; width: 20em;">' +
                        '<ul></ul>' +
                        '</div>');
                    var $list = $dropDown.find("ul");

                    _(options.dropDownItems).each(function (ddi) {
                        $list.append($("<li><a href='#' class='SubPrint'>" + ddi.label + "</a></li>").click(function () {
                            event.preventDefault();
                            ddi.handler.apply(options.scope);
                        }));
                    });

                    var $dropDownTrigger = $("<button/>").button({
                        text: false,
                        label: "Выбор формы",
                        icons: {
                            primary: "ui-icon-triangle-1-s"
                        }
                    }).click(function () {
                        $dropDown.position({
                            my: "right top",
                            at: "left bottom",
                            of: $(this).parent().parent()
                        }).toggleClass("Active");

                        return false;
                    });

                    $printBtnHolder.append($dropDownTrigger).buttonset();
                    $printBtnHolder.after($dropDown);
                }

                this.$("#print-button").empty().append($printBtnHolder).show();
            } else {

            }
        },

        openLabs: function () {
            this.trigger("change:viewState", {
                type: "diagnostics-laboratory"
            });
            App.Router.updateUrl("/appeals/" + this.options.appealId + "/diagnostics-laboratory/");

        },
        printData: function () {
            var self = this;
            // var result = this.resultData();

            // this.getJobTicket().done(function() {
            //     var biomaterial = self.biomaterials.first();
            //     var actions = biomaterial? biomaterial.get('actions') : [];
            //     var action = _.find(actions, function(action) {
            //         return action.id === result.id
            //     })

            //     var rbTissueTypeName = '';
            //     var rbTestTubeTypeNamе = '';
            //     var jobTicketDatetime = '';
            //     if(action){
            //         if(action.tubeType && action.tubeType.name){
            //             rbTestTubeTypeNamе = action.tubeType.name;
            //         }
            //         if(action.biomaterial && action.biomaterial.tissueType && action.biomaterial.tissueType.name){
            //             rbTissueTypeName = action.biomaterial.tissueType.name;
            //         }
            //         if(action.jobTicket && action.jobTicket.date){
            //             jobTicketDatetime = moment(action.jobTicket.date).format('DD.MM.YYYY');
            //         }
            //     }

            //     var data = {
            //             id: result.id,
            //             name: result.name,
            //             mkb: result.mkb,
            //             patientSex: result.patientSex,
            //             patientName: result.patientName,
            //             patientBirthday: result.patientBirthday,
            //             age: result.age2,
            //             appealNumber: result.appealNumber,
            //             payments: result.payments,
            //             department: result.department,
            //             rbTissueTypeName: rbTissueTypeName,
            //             rbTestTubeTypeNamе: rbTestTubeTypeNamе,
            //             tests: result.tests,
            //             executorDoctor: result.executor,
            //             assignDoctor: result.assigner,
            //             jobTicketDatetime: jobTicketDatetime
            //         };

            //         return data;

            // }
        },

        resultData: function () {
            var appeal = this.options.appeal;

            var self = this;
            var json = this.result.toJSON();

            json.mkb = this.result.getProperty('Направительный диагноз') || '';

            var executorSpecs = this.result.getProperty('doctorSpecs');
            var executorFirstName = this.result.getProperty('doctorFirstName');
            var executorMiddleName = this.result.getProperty('doctorMiddleName');
            var executorLastName = this.result.getProperty('doctorLastName');

            json.executor = executorFirstName + ' ' + executorMiddleName + ' ' + executorLastName + ', ' + executorSpecs;

            var assignerSpecs = this.result.getProperty('assignerSpecs');
            var assignerFirstName = this.result.getProperty('assignerFirstName');
            var assignerMiddleName = this.result.getProperty('assignerMiddleName');
            var assignerLastName = this.result.getProperty('assignerLastName');

            json.assigner = assignerFirstName + ' ' + assignerMiddleName + ' ' + assignerLastName + ', ' + assignerSpecs;

            json.payments = appeal.get('patient').get('payments').toJSON();

            json.department = appeal.get('currentDepartment').name;

            json.patientName = appeal.get('patient').get('name').get('raw');


            json.appealNumber = appeal.get('number');

            var sex = appeal.get('patient').get('sex');
            if (sex === 'female') {
                json.patientSex = 'Ж';
            }
            if (sex === 'male') {
                json.patientSex = 'М';
            }

            var birthDate = appeal.get('patient').get('birthDate');
            //ageString
            json.patientBirthday = Core.Date.format(birthDate);
            json.age = Core.Date.format(birthDate) + ' ' + Core.Date.getAgeString(birthDate);
            json.age2 = Core.Date.getAgeString(birthDate);

            json.assessmentBeginDate = this.result.getProperty('assessmentBeginDate');

            json.plannedEndDate = this.result.getProperty('plannedEndDate');

            if (this.result.getProperty('urgent') === 'false') {
                json.urgent = 'нет';
            } else {
                json.urgent = 'да';
            }

            function emptyFalse(val) {
                if (!val) {
                    val = '';
                }
                return val;
            }

            if (this.result.get('group')) {
                var group_1 = (this.result.get('group'))[1].attribute;
                json.tests = [];
                _.each(group_1, function (item) {
                    if (item.type == 'String') {

                        json.tests.push({
                            name: item.name,
                            value: emptyFalse(self.result.getProperty(item.name, 'value')),
                            unit: emptyFalse(self.result.getProperty(item.name, 'unit')),
                            norm: emptyFalse(self.result.getProperty(item.name, 'norm')),
                            normState: self.checkNorm(self.result.getProperty(item.name, 'value'), self.result.getProperty(item.name, 'norm'))
                        });

                    }
                });
            }

            var mnem = this.result.get('mnem');
            json.bak = false;
            if (mnem && mnem.toString().toUpperCase() === "BAK_LAB") {
                json.bak = this.bakResult.toJSON();
                json.bak.table = this.bakResult.getTable();
            }


            return json;
        },

        checkNorm: function (value, norm) {
            var normState;
            //	console.log('checkNorm', value, norm);
            value = this.parseValue(value);
            norm = this.parseNorm(norm);

            if (!value || !norm[0] || !norm[1]) normState = false;

            if (value >= norm[0] && value <= norm[1]) normState = 'ok'; //в норме

            if (value < norm[0]) normState = 'above'; //выше нормы

            if (value > norm[1]) normState = 'below'; //ниже нормы

            return normState;
        },

        parseValue: function (value) {
            if (!value) {
                return false;
            }
            var num = parseFloat(value.replace(/,/g, '.'));

            if (isNaN(num)) num = false;

            return num;
        },

        parseNorm: function (norm) {
            var min, max;
            var minmax = norm.split('-');

            if (minmax.length < 2) {
                min = max = false;
            } else {
                min = this.parseValue(minmax[0]);
                max = this.parseValue(minmax[1]);
            }

            return [min, max];
        },

        navigate: function (id) {

            this.trigger("change:viewState", {
                type: "diagnostics-laboratory",
                mode: "SUB_REVIEW",
                options: {
                    modelId: id,
                    force: true
                }
            });
            App.Router.navigate("/appeals/" + this.options.appealId + "/diagnostics-laboratory/" + id, {
                trigger: false
            });
        },
        // (ಥ_ಥ)
        first: function () {
            this.navigate(335108);
        },
        prev: function (id) {
            this.navigate(335110);
        },
        next: function () {
            this.navigate(335111);
        },
        last: function () {
            this.navigate(335112);
        },
        extra: function () {
            this.$('.extra-info').toggle();

        },
        print: function () {

            //alert(JSON.stringify(this.resultData()));
        },

        render: function () {
            return this;
        },

        render2: function () {
            var self = this;
            var resultData = self.resultData();


            console.log('render LaboratoryResultView', this.bakResult.toJSON(), resultData);
            self.$el.html(_.template(self.template, resultData, {
                variable: 'data'
            }));

            self.$('.actions button').button();
            self.showPrintBtn(self.printOptions());

            this.renderContextPrintButton();
            this.$el.find("table.anti").delegate('td', 'mouseover mouseleave', function (e) {
                if (e.type == 'mouseover') {
                    $(this).addClass("mouseover");
                    $(this).parent().addClass("hover");
                    $("colgroup").eq($(this).index()).addClass("hover");
                } else {
                    $(this).removeClass("mouseover");
                    $(this).parent().removeClass("hover");
                    $("colgroup").eq($(this).index()).removeClass("hover");
                }
            });
            return self;
        }

    });

    return LaboratoryResultView;

});
