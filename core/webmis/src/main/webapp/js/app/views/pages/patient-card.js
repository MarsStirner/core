define([
    "permissions",
    "views/CardNav",
    "models/patient",
    "models/print/form007",
    "models/flat-directory",
    "collections/patient-appeals",
    "views/breadcrumbs",
    "views/print",
    "views/appeals-history"
], function (userPermissions, CardNav) {

    App.Views.PatientCard = View.extend({
        canPrint: true,

        events: {
            "click .Actions.Print": "showPrint",
            "click .NewAppeal": "onNewAppealClick",
            "click .EditAppeal": "onEditAppealClick"
        },

        initialize: function () {
            this.clearAll();

            checkForErrors(this.options.path, "path is mandatory");
            checkForErrors(this.options.id, "id is mandatory");

            this.on("template:loaded", this.onTemplateLoaded, this);
            this.loadTemplate("pages/patient-card");
        },

        showPrint: function () {

            /*var Patient = new App.Models.Patient({
				id: this.options.id
			});
			new App.Views.Print({
				model: Patient,
				template: "processing-agreement"
			});
			Patient.fetch();*/

            var PrintForm007 = new App.Models.PrintForm007({
                id: 25
            });
            new App.Views.Print({
                model: PrintForm007,
                template: "007"
            });
            PrintForm007.fetch();

        },

        onTemplateLoaded: function () {
            this.initWithDictionaries([{
                name: "benefitCategoriesFederal",
                fd: true,
                id: 10
            }, {
                name: "benefitCategoriesRegional",
                fd: true,
                id: 11
            }, {
                name: "ranks",
                fd: true,
                id: 7
            }, {
                name: "forceBranches",
                fd: true,
                id: 6
            }], this.ready, this, true);
        },

        onNewAppealClick: function (event) {
            var self = this;

            if (this.haveUnclosedAppeals) {
                event.preventDefault();
                //alert("Создание госпитализации невозможно, имеется незакрытая история болезни.");
                var notClosedAlert = $("<div>" +
                    "<span style='font-size: 1.2em'>Имеется незакрытая история болезни.</span>" +
                    "<div style='margin-top: 1em;'><a href='/patients/" + self.model.get("id") + "/appeals/' style='font-size: 1.2em'>Перейти к списку госпитализаций</a></div>" +
                    "</div>")
                    .dialog({
                        modal: true,
                        resizable: false,
                        dialogClass: "webmis",
                        width: '50em',
                        buttons: {
                            "Игнорировать": function () {
                                App.Router.navigate("patients/" + self.model.get("id") + "/appeals/new/", {
                                    trigger: true
                                });
                                //App.Router.navigate("patients/" + self.model.get("id") + "/appeals/new/?ignored=true", {trigger: true});
                                $(this)
                                    .dialog("close");
                            },
                            "Отмена": function () {
                                $(this)
                                    .dialog("close");
                            }
                        },
                        title: "Новая госпитализация"
                    });
                notClosedAlert.dialog("open");
            } else {
                App.Router.navigate("patients/" + self.model.get("id") + "/appeals/new/", {
                    trigger: true
                });
            }
        },

        onEditAppealClick: function (event) {
            //if (!this.model.isClosed())
            App.Router.navigate("/patients/" + this.model.get("id") + "/edit/", {
                trigger: true
            });
        },

        // Загрузился шаблон карточки пациента
        ready: function (dicts) {
            //console.log(dicts)

            var view = this;

            view.model = new App.Models.Patient({
                id: this.options.id
            });

            view.model.on("change", function () {
                var json = view.model.toJSON();

                json.payments = {
                    dms: (new Collection(view.model.get("payments")
                        .getDms()))
                        .toJSON(),
                    oms: (new Collection(view.model.get("payments")
                        .getOms()))
                        .toJSON()
                };

                /*var dms = view.model.get("payments").getDms();
				var oms = view.model.get("payments").getOms();*/

                json.phones = {
                    blocks: [
                        []
                    ],
                    count: view.model.get("phones")
                        .length
                };

                view.model.get("phones")
                    .each(function (p, i) {
                        //Игнорим контакты с typeId = 0, это неверные записи из нтк
                        if (p.get("typeId") != 0) {
                            json.phones.blocks[json.phones.blocks.length - 1].push(p.toJSON());
                            if ((i + 1) % 3 === 0 && i < view.model.get("phones")
                                .length) {
                                json.phones.blocks.push([]);
                            }
                        }
                    });

                /*if (dms) json.payments.dms = dms.toJSON();
				if (oms) json.payments.oms = oms.toJSON();*/

                var snilsStr = json.snils.toString();
                if (snilsStr.length) {
                    json.snils = snilsStr.substr(0, 3) + "-" + snilsStr.substr(3, 3) + "-" + snilsStr.substr(6, 3) + " " + snilsStr.substr(9, 2);
                }

                if (json.disabilities.length && json.disabilities[0].benefitsCategory.id) {
                    var bcId = json.disabilities[0].benefitsCategory.id;

                    var bcFederal = _(dicts.benefitCategoriesFederal)
                        .find(function (bc) {
                            return bc.id === bcId;
                        });
                    if (bcFederal) {
                        json.disabilities[0].benefitsCategory.name = bcFederal["27"];
                    } else {
                        var bcRegional = _(dicts.benefitCategoriesRegional)
                            .find(function (bc) {
                                return bc.id === bcId;
                            });
                        json.disabilities[0].benefitsCategory.name = bcRegional["30"];
                    }
                }

                if (json.occupations.length && json.occupations[0].works && json.occupations[0].works.length) {
                    if (json.occupations[0].works[0].forceBranch && json.occupations[0].works[0].forceBranch.id) {
                        var branchId = json.occupations[0].works[0].forceBranch.id;
                        var branch = _(dicts.forceBranches)
                            .find(function (b) {
                                return b.id === branchId
                            });
                        if (branch) {
                            console.log(branch);
                            json.occupations[0].works[0].forceBranch.branch = branch["17"];
                        }
                    }
                    if (json.occupations[0].works[0].rank && json.occupations[0].works[0].rank.id) {
                        var rankId = json.occupations[0].works[0].rank.id;
                        var rank = _(dicts.ranks)
                            .find(function (r) {
                                return r.id === rankId
                            });
                        if (rank) {
                            console.log(rank);
                            json.occupations[0].works[0].rank.rank = rank["20"];
                        }
                    }
                }

                _(["residential", "registered"])
                    .each(function (addressType) {
                        if (json.address[addressType].kladr) {
                            var a = json.address[addressType];

                            _.forEach(["street", "republic", "district", "city", "locality", "street"], function (part) {
                                if (!a[part]) {
                                    a[part] = {
                                        socr: "",
                                        name: ""
                                    };
                                }
                            });

                            var kladrString = _([
                                a.street.index || a.locality.index || a.city.index || a.district.index || a.republic.index,
                                a.republic.socr + " " + a.republic.name,
                                a.district.socr + " " + a.district.name,
                                a.city.socr + " " + a.city.name,
                                a.locality.socr + " " + a.locality.name,
                                a.street.socr + " " + a.street.name
                            ])
                                .filter(function (e) {
                                    return e.trim()
                                        .length > 0;
                                })
                                .join(", ");

                            if (a.house) kladrString += ", дом " + a.house;
                            if (a.building) kladrString += ", корпус " + a.building;
                            if (a.flat) kladrString += ", кв. " + a.flat;

                            a.kladrString = kladrString;
                        }
                    });

                json.allowCreateAppeal = Core.Data.currentRole() === ROLES.NURSE_RECEPTIONIST;
                json.allowEditPatientCard = Core.Data.currentRole() === ROLES.NURSE_RECEPTIONIST;

                console.log(json);

                this.$el.html($("#patient-card-page")
                    .tmpl(json));

                view.$(".Save")
                    .attr("disabled", "disabled");
            }, this);

            view.model.fetch({
                success: function () {
                    window.document.title = view.model.get("name")
                        .get("raw");
                    var Appeals = new App.Collections.PatientAppeals;
                    Appeals.patient = view.model;
                    Appeals.setParams({
                        //limit: 5,
                        sortingField: "beginDate",
                        sortingMethod: "desc"
                    });
                    Appeals.on("reset", function () {
                        // Список последних обращений
                        var History = new App.Views.AppealsHistory({
                            appeals: Appeals
                        });
                        view.depended(History);
                        view.$el.find(".History")
                            .html(History.render()
                                .el);

                        var haveUnclosedAppeals = Appeals.find(function (a) {
                            return !a.get("rangeAppealDateTime")
                                .get("end");
                        });

                        if (!haveUnclosedAppeals) {
                            view.$(".Save")
                                .removeAttr("disabled");
                        }

                        view.haveUnclosedAppeals = haveUnclosedAppeals;
                    });
                    Appeals.fetch();

                    var Breadcrumbs = new App.Views.Breadcrumbs;
                    this.$("#page-head")
                        .append(Breadcrumbs.render()
                            .el);

                    /*Breadcrumbs.on("template:loaded", function () {

						Breadcrumbs.setStructure([
							App.Router.cachedBreadcrumbs.PATIENTS,
							App.Router.compile(App.Router.cachedBreadcrumbs.PATIENT, view.model.toJSON())
						]);
					});*/

                    var onBreadcrumbsReady = function () {
                        Breadcrumbs.setStructure([
                            App.Router.cachedBreadcrumbs.PATIENTS,
                            App.Router.compile(App.Router.cachedBreadcrumbs.PATIENT, view.model.toJSON())
                        ]);
                    };

                    if (!Breadcrumbs.templateLoadComplete) {
                        // Скроем меню, пока не загрузился шаблон для хлебных крошек
                        Breadcrumbs.on("template:loaded", onBreadcrumbsReady, this);
                    } else {
                        onBreadcrumbsReady();
                    }
                    var patient = view.model;
                    var patientId = patient.get('id');

                    view.cardNav = new CardNav({
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
                    view.$el.find('.CardNav')
                        .html(view.cardNav.render()
                            .el);


                    this.$("#page-head")
                        .html(Breadcrumbs.render()
                            .el);

                    this.$(".NewAppeal")
                        .button({
                            icons: {
                                primary: "icon-plus icon-color-green"
                            }
                        });
                    this.$(".EditAppeal")
                        .button();
                }
            });
        },
        render: function () {
            return this
        }
    });



});
