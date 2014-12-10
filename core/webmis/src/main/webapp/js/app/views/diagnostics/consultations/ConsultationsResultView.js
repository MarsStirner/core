define(function(require) {
    var template = require('text!templates/diagnostics/consultations/consultations-result.html');

    var Result = require('models/diagnostics/consultations/Consultation');
    var PrintView = require('views/print');

    var InstrumentalResultView = Backbone.View.extend({
        template: template,
        events: {
            "click .buck-to-list": "openConsultations" //,
            // "click .print": "print"
        },

        getResult: function(success, error) {
            var self = this;
            //console.log('getResult', this);
            this.result = new Result();
            this.result.eventId = this.options.appealId

            this.result.id = this.options.modelId ? this.options.modelId : this.options.subId[0];

            return this.result.fetch({
                success: function(model, response, options) {
                    success(model, response, options);
                },
                error: function() {

                }
            });
        },

        openConsultations: function() {
            this.trigger("change:viewState", {
                type: "diagnostics-consultations"
            });
            App.Router.updateUrl("/appeals/" + this.options.appealId + "/diagnostics-consultations/");

        },

        printData: function() {

            var appeal = this.options.appeal;
            var patient = appeal.get('patient');
            var result = this.result;

            var data = {
                patientId: patient.get('id'),
                patientName: patient.get('name').toJSON(),
                appealId: appeal.get('id'),
                appealNumber: appeal.get('number'),
                id: result.get('id'),
                name: result.get('name'),
                endDate: moment(result.getProperty('plannedEndDate'), "YYYY-MM-DD HH:mm:ss").format("DD.MM.YYYY HH:ss"),
                doctorName: [result.getProperty('doctorFirstName'), result.getProperty('doctorMiddleName'), result.getProperty('doctorLastName')].join(" "),
                doctorSpecs: result.getProperty('doctorSpecs'),
                assignerName: [result.getProperty('assignerFirstName'), result.getProperty('assignerMiddleName'), result.getProperty('assignerLastName')].join(" "),
                assignerSpecs: result.getProperty('assignerSpecs'),
                attributes: result.getMarkupFreeFlattenedDetails()
            };

            // console.log('printData', data);

            return [data];
        },

        printDirectionData: function() {

            var appeal = this.options.appeal;
            var patient = appeal.get('patient');
            var result = this.result;

            var data = {
                patientId: patient.get('id'),
                patientName: patient.get('name').toJSON(),
                appealId: appeal.get('id'),
                appealNumber: appeal.get('number'),
                //id: result.get('id'),
                examName: result.get('name'),
                // endDate: moment(result.getProperty('plannedEndDate'), "YYYY-MM-DD HH:mm:ss").format("DD.MM.YYYY HH:ss"),
               /* examDoctorName: [result.getProperty('doctorFirstName'), result.getProperty('doctorMiddleName'), result.getProperty('doctorLastName')].join(" "),
                examDoctorSpecs: result.getProperty('doctorSpecs'),
                // assignerName: [result.getProperty('assignerFirstName'), result.getProperty('assignerMiddleName'), result.getProperty('assignerLastName')].join(" "),
                // assignerSpecs: result.getProperty('assignerSpecs'),*/
							  // TODO: это направивший врач. Название свойств оставил старое чтобы не менять шаблон.
								examDoctorName: [result.getProperty('assignerFirstName'), result.getProperty('assignerMiddleName'), result.getProperty('assignerLastName')].join(" "),
								examDoctorSpecs: result.getProperty('assignerSpecs'),
                attributes: result.getMarkupFreeFlattenedDetails()
            }

            // console.log('printData', data);

            return data;
        },

        printOptions: function() {
            var self = this;

            return {
                label: "Печать",
                scope: self,
                handler: self.printResult,
                dropDownItems: [{
                    label: "Результат исследования",
                    handler: self.printResult
                }, {
                    label: "Направление на исследование",
                    handler: self.printDirection
                }]
            }
        },

        printResult: function() {
            var self = this;

            new App.Views.Print({
                data: {
                    documents: this.printData()
                },
                template: "documentsToPrintSeparately"
            });


        },

        printDirection: function() {
            var self = this;

            new App.Views.Print({
                data: this.printDirectionData(),
                template: "directionForConsultation"
            });

        },

        showPrintBtn: function(options) {
            if (options) {
                var $printBtnHolder = $("<div/>");
                var $printBtn = $('<button class="PrintBtn"/>');

                $printBtn.button({
                    label: options.label
                }).click(function(event) {
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

                    _(options.dropDownItems).each(function(ddi) {
                        $list.append($("<li><a href='#' class='SubPrint'>" + ddi.label + "</a></li>").click(function() {
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
                    }).click(function() {
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


        resultData: function() {
            var self = this;
            var json = this.result.toJSON();

            var value;

            function emptyFalse(val) {
                if (!val || (val == '<br>') || (val == ' ')) {
                    val = '';
                }
                return val;
            }

            var doctorSpecs = this.result.getProperty('doctorSpecs');
            var doctorFirstName = this.result.getProperty('doctorFirstName');
            var doctorMiddleName = this.result.getProperty('doctorMiddleName');
            var doctorLastName = this.result.getProperty('doctorLastName');
            json.plannedEndDate = this.result.getProperty('plannedEndDate');

            json.doctor = doctorFirstName + ' ' + doctorMiddleName + ' ' + doctorLastName + ', ' + doctorSpecs;


            if (this.result.get('group')) {
                var group_1 = (this.result.get('group'))[1].attribute;
                json.tests = [];
                _.each(group_1, function(item) {


                    switch (item.type) {
                       // case 'Time':
                         //   value = moment(self.result.getProperty(item.name, 'value'),'YYYY-MM-DD HH:mm:ss').format('HH:mm');
                         //  break;
                        // case 'Date':
                        //     value = moment(self.result.getProperty(item.name, 'value'),'YYYY-MM-DD HH:mm:ss').format('YYYY-MM-DD');
                        //     break;
                        default: 
                            value = emptyFalse(self.result.getProperty(item.name, 'value'));
                        
                    }

                   // console.log(item.type, item.name, emptyFalse(self.result.getProperty(item.name, 'value')),value);
                    
                    json.tests.push({
                        name: item.name,
                        value: value //,
                        // unit: emptyFalse(self.result.getProperty(item.name, 'unit')),
                        // norm: emptyFalse(self.result.getProperty(item.name, 'norm'))
                    });

                });
            }
//console.log('json',json);
            return json;

        },

        render: function() {
            var self = this;
            self.getResult(function() {

                self.$el.html(_.template(self.template, self.resultData(), {
                    variable: 'data'
                }));


                self.$('.actions button').button();
                self.showPrintBtn(self.printOptions());

            });

            return self;
        }
    });

    return InstrumentalResultView;
});
