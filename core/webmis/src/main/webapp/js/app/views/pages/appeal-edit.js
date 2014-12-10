define([
    "text!templates/pages/representative-item.tmpl",
    "collections/contracts",
    "views/widget",
    "permissions",
    "views/CardNav",
    "models/appeal",
    "models/flat-directory",
    "views/form/abstract-line",
    "collections/patient-appeals",
    "collections/dictionary-values",
    "collections/event-types",
    "collections/departments",
    "views/breadcrumbs",
    "views/mkb-directory",
    "views/pages/appeal-representative",
    "views/appeals-history"
], function (representativeTmpl, Contracts, Widget, userPermissions, CardNav) {

    var mkbDirView;

    App.Views.AppealEdit = Form.extend({
        model: App.Models.Appeal,

        events: {
            "click .Actions.Save": "onSave",
            "click .Actions.Cancel": "onCancel",
            "click .AddRepresentative": "onAddRepresentativeClick",
            "change [name='contract']": "onChangeContract"
        },

        initialize: function () {
            this.clearAll();

            pubsub.trigger('noty_clear');

            var view = this;
            Cache.Patient = this.model.get("patient");
            Cache.Patient.fetch({
                success: function () {
                    view.loadTemplate("pages/appeal-edit");
                    mkbDirView = (new App.Views.MkbDirectory()).render();
                }
            });

            this.modelIsNew = this.model.isNew();

            this.errorToolTip = new UI.ErrorTooltip();

            this.on("template:loaded", function () {
                var appealDicts = [{
                        name: "appealTypes",
                        id: 25,
                        fd: true
                    }, {
                        name: "agreedTypes",
                        id: 26,
                        fd: true
                    }, {
                        name: "hospitalizationTypes",
                        id: 18,
                        fd: true
                    }, {
                        name: "hospitalizationPointTypes",
                        id: 19,
                        fd: true
                    },
                    //{name: "hospitalizationChannelTypes", id: 20, fd: true},
                    {
                        name: "stateTypes",
                        id: 23,
                        fd: true
                    }, {
                        name: "assigmentTypes",
                        pathPart: "valueDomain&filter[typeIs]=hospitalization&filter[capId]=1"
                    }, {
                        name: "deliveredTypes",
                        pathPart: "valueDomain&filter[typeIs]=hospitalization&filter[capId]=3"
                    }, {
                        name: "movingTypes",
                        pathPart: "valueDomain&filter[typeIs]=hospitalization&filter[capId]=10"
                    }, {
                        name: "refuseAppealReasons",
                        pathPart: "valueDomain&filter[typeIs]=hospitalization&filter[capId]=7"
                    }, {
                        name: "injuryTypes",
                        pathPart: "valueDomain&filter[typeIs]=hospitalization&filter[capId]=23"
                    }, {
                        name: "deliveredAfterTypes",
                        pathPart: "valueDomain&filter[typeIs]=hospitalization&filter[capId]=5"
                    }, {
                        name: "financeTypes",
                        pathPart: "finance"
                    }, {
                        name: "requestTypes",
                        pathPart: "requestTypes"
                    }, {
                        name: "relationTypes",
                        pathPart: "relationships"
                    }, {
                        name: "departments",
                        collection: App.Collections.Departments
                    }
                ];

                this.initWithDictionaries(appealDicts, this.ready, this, true);
                //this.ready();

            }, this);

            this.model.on("sync", function () {
                pubsub.trigger('noty', {
                    text: 'Обращение ' + (view.modelIsNew ? 'создано' : 'изменено')
                });
                App.Router.navigate("/appeals/" + this.model.id + "/", {
                    trigger: true
                });
            }, this);


            this.contracts = new Contracts();
            this.contracts.on('reset', this.showContracts, this);
        },

        logModel: function () {
            console.log("appeal-edit model", this.model.toJSON());
        },

        onSave: function (event) {
            var self = this;
            // this.logModel();
            self.$(".Save").attr("disabled", true);
                
            var readyToSave = this.save(event, {
                error: function () {
                    console.log('error', arguments);
                    self.$(".Save").attr("disabled", false);
                },
                success: function(){
                    console.log('success', arguments);
                    // self.$(".Save").attr("disabled", false);
                }
            });
            console.log('readyToSave', readyToSave);
            this.$(".Save").attr("disabled", readyToSave);
        },

        onCancel: function (event) {
            event.preventDefault();

            if (this.model.isNew()) {
                App.Router.navigate("patients/" + this.model.get("patient").get("id") + "/", {
                    trigger: true
                });
            } else {
                App.Router.navigate("appeals/" + this.model.get("id") + "/", {
                    trigger: true
                });
            }
        },

        onAddRepresentativeClick: function () {
            this.openRepresentativeWindow();
            /*this.appealRepresentativeWindow = new App.Views.AppealRepresentative().render();
			 this.appealRepresentativeWindow.on("representative:selected", this.addRepresentative, this);
			 this.appealRepresentativeWindow.open();*/
        },

        onEditRepresentativeClick: function (model) {
            var self = this;
            var relativePatient = new App.Models.Patient({
                id: model.get("relative").get("id")
            });
            relativePatient.fetch({
                success: function () {
                    self.openRepresentativeWindow(relativePatient);
                }
            });
            relativePatient.on("sync", function () {
                model.set("relative", {
                    id: relativePatient.get("id"),
                    name: relativePatient.get("name").get("raw"),
                    birthDate: relativePatient.get("birthDate")
                });
            });

            /*this.appealRepresentativeWindow = new App.Views.AppealRepresentative().render();
			 this.appealRepresentativeWindow.on("representative:selected", this.addRepresentative, this);
			 this.appealRepresentativeWindow.openWithEdit(model);*/
        },

        openRepresentativeWindow: function (model) {
            this.appealRepresentativeWindow = new App.Views.AppealRepresentative().render();
            this.appealRepresentativeWindow.on("representative:selected", this.addRepresentative, this);
            this.appealRepresentativeWindow.on("close", this.onRepresentativeWindowClose, this);

            if (model) {
                this.appealRepresentativeWindow.openWithEdit(model);
            } else {
                this.appealRepresentativeWindow.open();
            }
        },

        onRepresentativeWindowClose: function () {
            this.appealRepresentativeWindow.off(null, null, this);
        },

        addRepresentative: function (patient) {
            this.appealRepresentativeWindow.off(null, null, this);

            var alreadyAdded = this.model.get("hospitalizationWith").find(function (p) {
                return p.get("relative").get("id") === patient.get("id");
            });

            if (!alreadyAdded) {
                if (Core.Date.getAge(patient.get("birthDate")) < 18) {
                    //alert("Нельзя добавить несовершеннолетнего представителя.");
                    this.$nonAdultAlert.dialog("open");
                } else if (this.model.get("patient").get("id") == patient.get("id")) {
                    this.$samePatinetAlert.dialog("open");
                } else {
                    this.model.get("hospitalizationWith").add({
                        relative: {
                            id: patient.get("id"),
                            name: patient.get("name").get("raw"),
                            birthDate: patient.get("birthDate")
                        }
                    });

                    /*this.model.get("hospitalizationWith").add({
					 id: patient.get("id"),
					 name: new App.Models.Name(patient.get("name").toJSON())
					 });*/
                }
            }
        },

        createDiagnosis: function (model) {
            var diagnosisView = new DiagnosisLine({
                model: model //,
                //collection: diagnoses
            });

            if (model.get("diagnosisKind") == "diagReceivedMkb") {
                diagnosisView.on("diagnosis:change", function (event) {
                    this.$(".Injury .ComboWrapper, .Injury .Combo").toggleClass("Mandatory", event.isInjury);
                }, this);
            }

            this.depended(diagnosisView);

            var selectors = {
                diagReceivedMkb: "#diagnosis-assignments",
                aftereffectMkb: "#diagnosis-aftereffects",
                attendantMkb: "#diagnosis-attendants"
            };

            this.$el.find(selectors[model.get("diagnosisKind")]).append(diagnosisView.render().el);
        },

        toggleInputs: function (enable) {
            var view = this;

            view.$(":input:not(.NotEditable),.DDSelect,.RichText")
                .attr("disabled", !Boolean(enable))
                .toggleClass("Disabled", !Boolean(enable));
        },

        getContracts: function () {
            var self = this;

            this.resetContract();
            var eventType = this.model.get("appealType").get("eventType");
            this.contracts.eventTypeId = eventType.get('id');

            this.$("[name='contract']").html('');
            if (this.contracts.eventTypeId) {
                this.contracts.fetch();//.done(this.showContracts.bind(this));
            }
        },

        showContracts: function () {
            this.$("[name='contract']").html('').append(this.contracts.map(function (contract) {
                return $("<option value='" + contract.get("id") + "'>" + contract.get("number") + "</option>");
            })).trigger('change').prop("disabled", this.contracts.length === 1);
            
            UIInitialize(this.$("[name='contract']").parent());
            if(!this.contracts.length){
                this.resetContract();
            }

        },

        resetContract: function(){
            this.model.set('contract', {}); 
        },

        onChangeContract: function(e){
            var $target = this.$(e.target);
            var contractId = $target.val();

            if(contractId){
                contract = this.contracts.find(function(model){
                    return model.get('id') == contractId;
                }); 
                this.model.set('contract', contract);
            }

        },

        ready: function (dicts) {
            var view = this;

            var result = this.model.toJSON();
            result.patient = this.model.get("patient").toJSON();
            result.dicts = dicts;
            result.isNew = this.model.isNew();


            result.dicts.requestTypes = _(result.dicts.requestTypes).filter(function (rType) {
                return ["clinic", "hospital", "1", "2"].indexOf(rType.code) !== -1;
            });

            //console.log("MODEL", dicts);

            this.$el.html($("#appeal-edit-common").tmpl(result));

            // view.model.on("change:appealWithDeseaseThisYear", function () {
            //     console.log("change:appealWithDeseaseThisYear", view.model.get("appealWithDeseaseThisYear"));
            // }, view);

            /*var Appeals = new App.Collections.PatientAppeals;
			 Appeals.patient = this.model.get("patient");
			 Appeals.fetch();*/

            var Patient = this.model.get("patient");

            var patientAppeals = new App.Collections.PatientAppeals;
            patientAppeals.patient = Patient;
            patientAppeals.setParams({
                //limit: 5,
                sortingField: "beginDate",
                sortingMethod: "desc"
            });
            patientAppeals.on("reset", function () {
                // Список последних обращений
                var History = new App.Views.AppealsHistory({
                    appeals: patientAppeals
                });
                view.depended(History);
                view.$el.find(".History").html(History.render().el);

                if (view.model.isNew()) {
                    view.toggleInputs(true);
                    view.$(".Save").removeAttr("disabled");

                    if (patientAppeals.length > 0) {
                        var thisYearAppeal = patientAppeals.find(function (appeal) {
                            return ((new Date(appeal.get("rangeAppealDateTime").get("start"))).getYear() == (new Date()).getYear());
                        });

                        if (thisYearAppeal) {
                            view.model.set("appealWithDeseaseThisYear", "повторно");
                        }
                    }
                } else {
                    var isClosed = patientAppeals.find(function (a) {
                        return a.get("id") === view.model.get("id");
                    }).get("rangeAppealDateTime").get("end");

                    if (!isClosed) {
                        view.toggleInputs(true);
                    }
                }
            });

            patientAppeals.fetch();

            var Breadcrumbs = new App.Views.Breadcrumbs;
            this.$("#page-head").append(Breadcrumbs.render().el);

            var onBreadcrumbsReady = function () {
                if (view.model.isNew()) {
                    Breadcrumbs.setStructure([
                        App.Router.cachedBreadcrumbs.PATIENTS,
                        App.Router.compile(App.Router.cachedBreadcrumbs.PATIENT, Patient.toJSON()),
                        App.Router.cachedBreadcrumbs.APPEALS_NEW
                    ]);
                } else {
                    Breadcrumbs.setStructure([
                        App.Router.cachedBreadcrumbs.PATIENTS,
                        App.Router.compile(App.Router.cachedBreadcrumbs.PATIENT, Patient.toJSON()),
                        App.Router.compile(App.Router.cachedBreadcrumbs.APPEALS_EDIT, {
                            id: view.model.id
                        })
                    ]);
                }
            };

            if (!Breadcrumbs.templateLoadComplete) {
                // Скроем меню, пока не загрузился шаблон для хлебных крошек
                Breadcrumbs.on("template:loaded", onBreadcrumbsReady, this);
            } else {
                onBreadcrumbsReady();
            }


            this.model.get("hospitalizationWith")
                .on("add remove", function () {
                    // console.log(this.$(".AddRepresentative"));
                    this.$(".AddRepresentative").toggle(!Boolean(this.model.get("hospitalizationWith").length));
                }, view);

            var representativeList = new RepresentativeList({
                collection: this.model.get("hospitalizationWith"),
                relationTypes: dicts.relationTypes
            });

            representativeList.on("representative:edit", this.onEditRepresentativeClick, this);
            representativeList.render();

            this.$nonAdultAlert = this.$(".NonAdultAlert")
                .dialog({
                    modal: true,
                    autoOpen: false,
                    dialogClass: "webmis",
                    resizable: false,
                    width: "500px",

                    buttons: {
                        "Принять": function () {
                            $(this)
                                .dialog("close");
                        }
                    }
                });

            this.$samePatinetAlert = this.$(".SamePatientAlert")
                .dialog({
                    modal: true,
                    autoOpen: false,
                    dialogClass: "webmis",
                    resizable: false,
                    width: "500px",

                    buttons: {
                        "Принять": function () {
                            $(this)
                                .dialog("close");
                        }
                    }
                });

            this.eventTypes = new App.Collections.EventTypes();

            this.eventTypes._params = {};

            this.eventTypes.on("reset", function () {
                var eventType = {id:'', name:''};

                if (this.eventTypes.length) {
                    eventType = this.eventTypes.first().toJSON();
                }
                this.model.get("appealType").get("eventType").set(eventType);

                this.$("[name='event_type[id]']").html('');
                this.$("[name='event_type[id]']").append(this.eventTypes.map(function (eventType) {
                    return $("<option value='" + eventType.get("id") + "'>" + eventType.get("value") + "</option>");
                })).trigger('change').prop("disabled", this.eventTypes.length === 1);

                UIInitialize(this.$("[name='contract']").parent());

            }, this);

            var onAppealTypeChange = function () {
                var requestTypeId = view.model.get("appealType").get("requestType").get("id");
                var financeId = view.model.get("appealType").get("finance").get("id");

                if (requestTypeId && financeId) {
                    // console.log(requestTypeId, financeId);
                    view.eventTypes.setParams({
                        filter: {
                            requestType: view.model.get("appealType").get("requestType").get("id"),
                            finance: view.model.get("appealType").get("finance").get("id")
                        }
                    });

                    view.eventTypes.fetch();
                } else {
                    view.eventTypes.reset([]);
                }
            };

            this.model.get("appealType").get("requestType")
                .on("change", onAppealTypeChange, this);

            this.model.get("appealType").get("finance")
                .on("change", onAppealTypeChange, this);

            this.model.get("appealType").get("eventType")
                .on("change", function () {
                    // console.log('change eventType', this.model.get('appealType').get('eventType').toJSON());
                    this.getContracts();
                }, this);

            this.model.get("rangeAppealDateTime").connect("start", "appeal[date][start]", this.$el);
            this.model.get("rangeAppealDateTime").connect("start", "appeal[time][start]", this.$el);

            if (!this.model.get("rangeAppealDateTime").get("start")) {
                this.model.get("rangeAppealDateTime").set("start", new Date().getTime());
            }

            this.model.get("rangeAppealDateTime").connect("end", "appeal[date][end]", this.$el);
            this.model.get("rangeAppealDateTime").connect("end", "appeal[time][end]", this.$el);


            this.model.connect("number", "number", this.$el);
            //this.model.get("appealType").connect("id", "appeal_type[id]", this.$el );

            this.model.get("appealType").get("requestType").connect("id", "request_type[id]", this.$el);
            this.model.get("appealType").get("finance").connect("id", "finance[id]", this.$el);
            this.model.get("appealType").get("eventType").connect("id", "event_type[id]", this.$el);

            this.model.connect("urgent", "urgent", this.$el);

            this.model.connect("appealWithDeseaseThisYear", "appealWithDeseaseThisYear", this.$el);
            this.model.connect("refuseAppealReason", "refuse_appeal_reason", this.$el);

            this.model.connect("agreedDoctor", "agreed_doctor[name]", this.$el);
            this.model.get("agreedType").connect("id", "agreed_type[id]", this.$el);
            /*this.model.get("agreedDoctor" ).get("name").connect("raw", "agreed_doctor[name]", this.$el );*/

            this.model.get("assignment").connect("directed", "assignment[directed]", this.$el);
            this.model.get("assignment").connect("doctor", "assignment[doctor][name]", this.$el);

            this.model.get("assignment").connect("number", "assignment[number]", this.$el);
            this.model.get("assignment").connect("assignmentDate", "assignment[assignment_date]", this.$el);

            this.model.get("hospitalizationType").connect("id", "hospitalization_type[id]", this.$el);
            this.model.get("hospitalizationPointType").connect("id", "hospitalization_point_type[id]", this.$el);
            //this.model.get( "hospitalizationChannelType" ).connect("id", "hospitalization_channel_type[id]", this.$el );

            this.model.connect("movingType", "moving_type", this.$el);
            this.model.connect("reopening", "reopening", this.$el);

            this.model.connect("deliveredType", "delivered_type[id]", this.$el);
            this.model.connect("deliveredAfterType", "delivered_after_type[id]", this.$el);
            this.model.connect("stateType", "state_type", this.$el);
            //this.model.get( "drugsType" ).connect("id", "drugs_type[id]", this.$el );
            this.model.connect("ambulanceNumber", "ambulance_number", this.$el);

            /*this.model.get( "physicalParameters" ).get("bloodPressure").connect("left", "physical_parameters[blood_pressure][left]", this.$el );
			 this.model.get( "physicalParameters" ).get("bloodPressure").connect("right", "physical_parameters[blood_pressure][right]", this.$el );*/
            this.model.get("physicalParameters")
                .get("bloodPressure")
                .get("left")
                .connect("syst", "physical_parameters[blood_pressure][left][syst]", this.$el);
            this.model.get("physicalParameters")
                .get("bloodPressure")
                .get("left")
                .connect("diast", "physical_parameters[blood_pressure][left][diast]", this.$el);
            this.model.get("physicalParameters")
                .get("bloodPressure")
                .get("right")
                .connect("syst", "physical_parameters[blood_pressure][right][syst]", this.$el);
            this.model.get("physicalParameters")
                .get("bloodPressure")
                .get("right")
                .connect("diast", "physical_parameters[blood_pressure][right][diast]", this.$el);
            this.model.get("physicalParameters")
                .connect("temperature", "physical_parameters[temperature]", this.$el);
            this.model.get("physicalParameters")
                .connect("weight", "physical_parameters[weight]", this.$el);
            this.model.get("physicalParameters")
                .connect("height", "physical_parameters[height]", this.$el);

            this.model.connect("injury", "injury", this.$el);

            //Приемное отделение по умолчанию
            if (!this.model.get("orgStructStay")) this.model.set("orgStructStay", 28);
            this.model.connect("orgStructStay", "org_struct_stay", this.$el);

            this.model.connect("orgStructDirectedFrom", "org_struct_directed_from", this.$el);

            // Ограничение ввода для полей формата Double
            this.$('.RestrictFloat').keypress(function (eve) {
                if ((eve.which != 46 || $(this).val().indexOf('.') != -1) && (eve.which < 48 || eve.which > 57) || (eve.which == 46 && $(this).caret().start == 0)) {
                    eve.preventDefault();
                }
                // this part is when left part of number is deleted and leaves a . in the leftmost position. For example, 33.25, then 33 is deleted
                view.$('.RestrictFloat').keyup(function (eve) {
                    if ($(this).val().indexOf('.') == 0) {
                        $(this).val($(this).val().substring(1));
                    }
                });
            });

            var Diagnoses = this.model.get("diagnoses");

            var DiagnosisModel;

            if (Diagnoses.getAssignments() < 1) {
                DiagnosisModel = new App.Models.Diagnosis;
                DiagnosisModel.set({
                    diagnosisKind: "diagReceivedMkb"
                });
                Diagnoses.add(DiagnosisModel);
            }

            if (Diagnoses.getAftereffects() < 1) {
                DiagnosisModel = new App.Models.Diagnosis;
                DiagnosisModel.set({
                    diagnosisKind: "aftereffectMkb"
                });
                Diagnoses.add(DiagnosisModel);
            }

            if (Diagnoses.getAttendants() < 1) {
                DiagnosisModel = new App.Models.Diagnosis;
                DiagnosisModel.set({
                    diagnosisKind: "attendantMkb"
                });
                Diagnoses.add(DiagnosisModel);
            }


            Diagnoses.getAssignments().forEach(function (model) {
                view.createDiagnosis(model);
            });

            Diagnoses.getAftereffects().forEach(function (model) {
                view.createDiagnosis(model);
            });

            Diagnoses.getAttendants().forEach(function (model) {
                view.createDiagnosis(model);
            });

            UIInitialize(this.el);

            this.$(".Save").button();
            this.$(".MKBLauncher").button({
                icons: {
                    primary: "icon-book"
                }
            });
            this.$(".AddRepresentative")
                .button({
                    icons: {
                        primary: "icon-plus icon-color-green"
                    }
                });

            var patient = this.model.get('patient');
            var patientId = patient.get('id');

            this.cardNav = new CardNav({
                permissions: userPermissions,
                patient: patient,
                structure: [{
                    link: '/patients/' + patientId + '/',
                    name: 'Карточка пациента',
                    permissions: ['see_patient_card']
                }, {
                    link: '/patients/' + patientId + '/appeals/',
                    name: 'Госпитализации',
                    permissions: ['see_patient_appeals'],
                    active: true
                }, {
                    link: '/patients/' + patientId + '/summary',
                    name: 'Сводная информация',
                    permissions: ['see_patient_summary']
                }]
            });
            this.$el.find('.CardNav').html(this.cardNav.render().el);

            view.toggleInputs(false);
        },

        render: function () {
            $("body").append(this.errorToolTip.render().el);

            return this;
        }
    });

    var Representative = View.extend({
        tagName: "li",

        events: {
            "click .RemoveIcon": "removeModel",
            "click .EditIcon": "editModel"
            /*,
			 "change [name='relation_type']": "onRelationTypeChange",
			 "change [name='relation_note']": "onRelationNoteChange"*/
        },

        template: representativeTmpl,

        initialize: function () {
            //this.model.on("change", this.onRepresentativeChange, this);
            //this.model.on("sync", this.onRepresentativeSync, this);
        },

        onRepresentativeChange: function (event) {
            if (Core.Date.getAge(this.model.get("birthDate")) < 18) {
                //alert("Нельзя добавить несовершеннолетнего представителя.");
                this.$nonAdultAlert.dialog("open");
                this.removeModel();
            } else {
                this.render();
            }
        },

        /*onRelationTypeChange: function (event) {

		 },

		 onRelationNoteChange: function (event) {

		 },*/

        render: function () {
            if (this.model.get("relative")) {
                this.$el.html($.tmpl(this.template, {
                    model: this.model.toJSON(),
                    relationTypes: this.options.relationTypes
                }));
                this.delegateEvents();

                if (!this.model.get("relativeType")) this.model.set("relativeType", {});

                this.model.get("relativeType")
                    .connect("id", "relative_type", this.$el);
                this.model.connect("note", "relative_note", this.$el);

                UIInitialize(this.el);
            }

            return this;
        },

        removeModel: function () {
            this.model.collection.remove(this.model);
            this.model.off(null, null, this);
            this.off(null, null, this);
            this.remove();
        },

        editModel: function () {
            this.trigger("representative:edit");
        }
    });

    var RepresentativeList = View.extend({
        el: ".representative-list",

        initialize: function () {
            this.collection.on("add", this.addOne, this);
            this.collection.on("remove", this.removedOne, this);
        },

        addOne: function (model) {
            this.$el.parent()
                .removeClass("Hidden");

            var rep = new Representative({
                model: model,
                relationTypes: this.options.relationTypes
            });

            rep.on("representative:edit", function () {
                this.trigger("representative:edit", rep.model);
            }, this);

            this.$el.append(rep.render()
                .el);
        },

        removedOne: function (e) {
            if (this.collection.isEmpty())
                this.$el.parent()
                    .addClass("Hidden");
        },

        render: function () {
            this.collection.each(function (model) {
                console.log(model);
                this.addOne(model);
            }, this);

            return this;
        }
    });

    var DiagnosisLine = View.extend({
        events: {
            "click .MKBLauncher": "launchMKB",
            "keyup input[name='diagnosis[mkb][code]']": "onMKBCodeKeyUp"
        },

        initialize: function () {
            var mkb = this.model.get("mkb");
            mkb.on("change", function () {
                this.$("input[name='diagnosis[mkb][code]']")
                    .val(mkb.get("code"));
                this.$("input[name='diagnosis[mkb][diagnosis]']")
                    .val(mkb.get("diagnosis"));

                this.onDiagnosisChange();

                //this.trigger("diagnosis:change", {isInjury: mkb.get("code") && mkb.get("code")[0].toUpperCase() === "S"});


                //this.$("input[name='injury']").toggleClass("Mandatory", mkb.get("code") && mkb.get("code")[0].toUpperCase() === "S");

            }, this);
            this.model.on("selectionConfirmed", this.onSelectionConfirmed, this);
        },

        launchMKB: function () {
            mkbDirView.open({
                diagnosis: this.model
            });
        },

        onSelectionConfirmed: function (event) {
            this.model.get("mkb")
                .set({
                    code: event.selectedDiagnosis.get("code") || event.selectedDiagnosis.get("id"),
                    diagnosis: event.selectedDiagnosis.get("diagnosis")
                });
        },

        onMKBCodeKeyUp: function (event) {
            var str = $(event.currentTarget)
                .val();

            str = Core.Strings.toLatin(str);
            $(event.currentTarget)
                .val(str);
        },

        onDiagnosisChange: function () {
            this.trigger("diagnosis:change", {
                isInjury: this.model.get("mkb")
                    .get("code") && this.model.get("mkb")
                    .get("code")[0].toUpperCase() === "S"
            });
        },

        render: function () {
            var self = this;

            var patientSex = Cache.Patient.get("sex")
                .length ? (Cache.Patient.get("sex") == "male" ? 1 : 2) : 0;

            this.$el.html($("#appeal-edit-diagnosis")
                .tmpl({
                    mkb: this.model.get("mkb")
                        .toJSON()
                }));

            //this.mkbDirView.render();

            this.model.connect("description", "diagnosis[description]", this.$el);

            this.$("input[name='diagnosis[mkb][code]']")
                .autocomplete({
                    source: function (request, response) {
                        $.ajax({
                            url: "/data/dir/mkbs/",
                            dataType: "jsonp",
                            data: {
                                filter: {
                                    view: "mkb",
                                    code: request.term,
                                    sex: patientSex
                                }
                            },
                            success: function (raw) {
                                response($.map(raw.data, function (item) {
                                    return {
                                        label: item.code + " " + item.diagnosis,
                                        value: item.code,
                                        id: item.id,
                                        diagnosis: item.diagnosis
                                    }
                                }));
                            }
                        });
                    },
                    minLength: 2,
                    select: function (event, ui) {
                        self.model.get("mkb")
                            .set({
                                id: ui.item.id,
                                code: ui.item.value,
                                diagnosis: ui.item.diagnosis
                            }, {
                                silent: true
                            });

                        self.onDiagnosisChange();

                        self.$("input[name='diagnosis[mkb][diagnosis]']")
                            .val(ui.item.diagnosis);

                        console.log(ui.item ?
                            "Selected: " + ui.item.label :
                            "Nothing selected, input was " + this.value);
                    }
                })
                .on("keyup", function () {
                    if (!$(this)
                        .val()
                        .length) {
                        self.model.get("mkb")
                            .set({
                                id: "",
                                code: "",
                                diagnosis: ""
                            }, {
                                silent: true
                            });

                        self.onDiagnosisChange();

                        self.$("input[name='diagnosis[mkb][diagnosis]']")
                            .val("");
                    }
                });

            return this;
        }
    });
});
