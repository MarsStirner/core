define(function (require) {

    //var require('text!templates/appeal/edit/pages/monitoring.tmpl');
    var cardTemplate = require('text!templates/appeal/edit/pages/card.tmpl');
    var Moves = require('collections/moves/moves');
    var PatientDiagnoses = require('views/appeal/edit/pages/monitoring/collections/PatientDiagnoses');
    var SurgicalOperations = require('collections/surgical-operations');
    var VmpTalon = require('models/VmpTalon');
    require('views/print');
    require('models/print/appeal');

    App.Views.Card = View.extend({
        events: {
            "click .EditAppeal": "onEditAppealClick"
        },

        attributes: {
            "style": "display: table;"
        },

        canPrint: true,

        printOptions: function () {
            var self = this;

            return {
                label: "Печать",
                scope: self,
                handler: this.printAppeal,
                dropDownItems: [{
                    label: "Титульный лист формы 003\\у",
                    handler: this.printAppeal
                }, {
                    label: "Статкарта (приемное отделение)",
                    handler: this.printStatisticCard
                }, {
                    label: "Статкарта (Полная версия)",
                    handler: this.printStatisticCardFull
                }, {
                    label: "Согласие на обследование ВИЧ",
                    handler: this.printConsentToExam
                }, {
                    label: "Согласие на обследование и лечение (представитель)",
                    handler: this.printConsentToTreatmentRepresent
                }, {
                    label: "Согласие пациента на обследование и лечение (пациент)",
                    handler: this.printConsentToTreatment
                }, {
                    label: "Согласие субъекта на обработку персональных данных (пациент)",
                    handler: this.printConsentToProcessing
                }, {
                    label: "Согласие субъекта на обработку персональных данных (представитель)",
                    handler: this.printConsentToProcessingRepresent
                }]
            }
        },

        initialize: function () {
            this.model = this.options.appeal;
            this.model.on("change", this.render, this);
        },

        printAppeal: function () {
            var self = this;

            var PrintAppeal = new App.Models.PrintAppeal({
                id: this.model.get("id")
            });

            var moves = new Moves();
            moves.appealId = this.model.get("id");

            $.when(PrintAppeal.fetch(), moves.fetch())
                .then(function () {
                    new App.Views.Print({
                        data: _.extend({
                            moves: moves.toJSON()
                        }, PrintAppeal.toJSON(), {
                            age: self.model.getAge()
                        }),
                        template: "f003"
                    });
                });
        },

        printConsentToExam: function () {
            new App.Views.Print({
                data: this.model.get("patient")
                    .toJSON(),
                template: "consent_to_the_examination"
            });
        },

        printConsentToTreatmentRepresent: function () {
            new App.Views.Print({
                data: this.model.get("patient")
                    .toJSON(),
                template: "consent_to_treatment_representative"
            });
        },

        printConsentToTreatment: function () {
            new App.Views.Print({
                data: this.model.get("patient")
                    .toJSON(),
                template: "consent_to_treatment"
            });
        },

        printConsentToProcessing: function () {
            new App.Views.Print({
                data: this.model.get("patient")
                    .toJSON(),
                template: "consent_to_the_processing"
            });
        },

        printConsentToProcessingRepresent: function () {
            new App.Views.Print({
                data: this.model.get("patient")
                    .toJSON(),
                template: "consent_to_the_processing_representative"
            });
        },

        printStatisticCard: function () {
            var self = this;
            var PrintAppeal = new App.Models.PrintAppeal({
                id: this.model.get("id")
            });

            $.when(PrintAppeal.fetch())
                .then(function () {
                    new App.Views.Print({
                        data: _.extend(PrintAppeal.toJSON(), {
                            age: self.model.getAge()
                        }),
                        template: "f066"
                    });
                });
        },

        printStatisticCardFull: function () {
            var self = this;
            var printAppeal = new App.Models.PrintAppeal({
                id: this.model.get("id")
            });
            var moves = new Moves();
            moves.appealId = this.model.get("id");

            var vmp = new VmpTalon();
            vmp.appealId = this.model.get("id");

            var diags = new PatientDiagnoses([], {
                appealId: this.model.get("id")
            });

            var surgical = new SurgicalOperations([], {
                appealId: this.model.get("id")
            });

            $.when(
                printAppeal.fetch(),
                moves.fetch(),
                vmp.fetch(),
                diags.fetch(),
                surgical.fetch()
            )
                .then(function () {
                    new App.Views.Print({
                        data: _.extend(printAppeal.toJSON(), {
                            age: self.model.getAge(),
                            moves: _.filter(moves.toJSON(), function (move) {
                                return move.unit !== "Приемное отделение";
                            }),
                            quoting: vmp.toJSON(),
                            diagnoses: diags.toJSON(),
                            operations: surgical.toJSON()
                        }),
                        template: "f066Full"
                    });
                });
        },

        onEditAppealClick: function (event) {
            if (!this.model.isClosed())
                App.Router.navigate("appeals/" + this.model.id + "/edit/", {
                    trigger: true
                });
        },

        /*showPrint: function (options) {
			var printModel;
			if (options.data === "appeal") {
				printModel = new App.Models.PrintAppeal({id: this.model.id});
			} else {
				printModel = new App.Models.Patient({id: this.model.get("patient").id});
			}
			*/
        /*var PrintAppeal = new App.Models.PrintAppeal({
				id: this.model.id
			});*/
        /*
			new App.Views.Print({
				model: printModel,
				template: options.template
			});
			printModel.fetch();
		},*/

        /*ready: function () {
			this._ready = true;
		},*/

        render: function () {
            this.initWithDictionaries([{
                name: "hospitalizationPointTypes",
                id: 19,
                fd: true
            }, {
                name: "hospitalizationTypes",
                id: 18,
                fd: true
            }], function (dicts) {
                //var template = cardTemplate;

                /*if (Core.Data.currentRole() == ROLES.DOCTOR_DEPARTMENT) {
					this.$el.html(_.template(cardMonitoringTemplate, _.extend({
						closed: this.model.closed,
						isClosed: this.model.isClosed(),
						allowEditAppeal: Core.Data.currentRole() === ROLES.NURSE_RECEPTIONIST,
						dicts: dicts
					}, this.model.toJSON())));
				} else {*/
                var closeDate = false;
                if (this.model.get('closeDateTime')) {
                    closeDate = this.model.get('closeDateTime');
                }

                this.$el.html($.tmpl(cardTemplate, _.extend({
                    closeDate: closeDate,
                    isClosed: this.model.get('closed'),
                    allowEditAppeal: Core.Data.currentRole() === ROLES.NURSE_RECEPTIONIST,
                    dicts: dicts
                }, this.model.toJSON())));
                //}

                /*this.separateRoles(ROLES.DOCTOR_DEPARTMENT, function () {
					template = cardMonitoringTemplate
				});*/

                this.$(".EditAppeal")
                    .button({
                        icons: {
                            primary: "icon-edit"
                        }
                    });

                this.delegateEvents();

                this.trigger("change:printState");

                return this;
            }, this, true);

            return this;
        }
    });



    return App.Views.Card;
});
