define(function(require) {
    var template = require('text!templates/diagnostics/instrumental/instrumental-result.html');

    var Result = require('models/diagnostics/instrumental/InstrumentalResearch');
    var PrintView = require('views/print');
    var ContextPrintButton = require('views/ContextPrintButton');

    var InstrumentalResultView = Backbone.View.extend({
        template: template,
        events: {
            "click .buck-to-list": "openInstrumental",
            "click .print": "print"
        },
        getResult: function(success, error) {
            var self = this;
            //console.log('getResult',this);
            this.result = new Result({},{
                appealId: this.options.appealId
            });
            //this.result.eventId = this.options.appealId;
            this.result.id = this.options.modelId ? this.options.modelId : this.options.subId[0];

            return this.result.fetch({
                success: function(model, response, options) {
                    success(model, response, options);
                },
                error: function() {

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

        openInstrumental: function() {
            this.trigger("change:viewState", {
                type: "diagnostics-instrumental"
            });
            App.Router.updateUrl("/appeals/" + this.options.appealId + "/diagnostics-instrumental/");

        },
        printData: function(){

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
                doctorName: [result.getProperty('doctorFirstName'),result.getProperty('doctorMiddleName'),result.getProperty('doctorLastName')].join(" "),
                doctorSpecs: result.getProperty('doctorSpecs'),
                assignerName: [result.getProperty('assignerFirstName'),result.getProperty('assignerMiddleName'),result.getProperty('assignerLastName')].join(" "),
                assignerSpecs: result.getProperty('assignerSpecs'),
                attributes:result.getMarkupFreeFlattenedDetails()
            };

            // console.log('printData',data);

            return [data];
        },
        print: function(){

             new PrintView({
                data: {documents: this.printData()},
                template: "documentsToPrintSeparately"
            });

        },
        resultData: function() {
            var self = this;
            var json = this.result.toJSON();

            function emptyFalse(val) {
                if (!val) {
                    val = '';
                }
                return val;
            }

            var doctorSpecs = this.result.getProperty('doctorSpecs');
            var doctorFirstName = this.result.getProperty('doctorFirstName');
            var doctorMiddleName = this.result.getProperty('doctorMiddleName');
            var doctorLastName = this.result.getProperty('doctorLastName');
            json.plannedEndDate = this.result.getProperty('plannedEndDate');

            if(doctorLastName){
                var doctor = doctorFirstName + ' ' + doctorMiddleName + ' ' + doctorLastName;
                if(doctorSpecs){
                    var doctor = doctor + ', ' + doctorSpecs;
                }
            }

            json.doctor = doctor;
            //json.doctor = this.result.getProperty('Исполнитель');


            if (this.result.get('group')) {
                var group_1 = (this.result.get('group'))[1].attribute;
                json.tests = [];
                _.each(group_1, function(item) {
                    if (true){//item.type == 'String') {

                        json.tests.push({
                            name: item.name,
                            value: emptyFalse(self.result.getProperty(item.name, 'value')),
                            unit: emptyFalse(self.result.getProperty(item.name, 'unit')),
                            norm: emptyFalse(self.result.getProperty(item.name, 'norm'))
                        });

                    }
                });
            }

            return json;

        },

        render: function() {
            var self = this;
            self.getResult(function() {
                console.log('render instrumentalResultView', self, self.resultData());
                self.$el.html(_.template(self.template, self.resultData(), {
                    variable: 'data'
                }));

                self.renderContextPrintButton();
                self.$('.actions button').button();

            });

            return self;
        }
    });

    return InstrumentalResultView;

});
