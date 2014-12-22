define(function(require) {
    var shared = require('views/appeal/edit/pages/monitoring/shared');

    var monitoringTmpl = require('text!templates/appeal/edit/pages/monitoring/layout.tmpl');

    var Card = require('views/appeal/edit/pages/card');

    var ChemotherapyInfo = require('views/appeal/edit/pages/monitoring/views/ChemotherapyInfo');
    var TherapiesCollection = require('collections/therapy/Therapies');


    var ExpressAnalyses = require('views/appeal/edit/pages/monitoring/views/ExpressAnalysesView');
    var Header = require('views/appeal/edit/pages/monitoring/views/Header');
    var MonitoringInfoGrid = require('views/appeal/edit/pages/monitoring/views/MonitoringInfoGrid');

    var PatientDiagnosesList = require('views/appeal/edit/pages/monitoring/views/PatientDiagnosesList');
    var PatientDiagnoses = require('views/appeal/edit/pages/monitoring/collections/PatientDiagnoses');

    var PatientInfo = require('views/appeal/edit/pages/monitoring/views/PatientInfo');
    var PatientBloodTypeHistoryRow = require('views/appeal/edit/pages/monitoring/views/PatientBloodTypeHistoryRow');
    var PatientBloodTypeRow = require('views/appeal/edit/pages/monitoring/views/PatientBloodTypeRow');
    var PatientBsaRow = require('views/appeal/edit/pages/monitoring/views/PatientBsaRow');

    var DictionaryValues = require('collections/dictionary-values');
    var PatientBloodTypes = require('views/appeal/edit/pages/monitoring/collections/PatientBloodTypes');

    var SignalInfo = require('views/appeal/edit/pages/monitoring/views/SignalInfo');
    var ExecPersonAssignmentDialog = require('views/appeal/edit/pages/monitoring/views/ExecPersonAssignmentDialog')
    var Persons = require('views/appeal/edit/pages/monitoring/collections/Persons');
    var AppealExecPerson = require('views/appeal/edit/pages/monitoring/models/AppealExecPerson');

    var Moves = require('collections/moves/moves');

    var MonitoringInfos = require('views/appeal/edit/pages/monitoring/collections/MonitoringInfos');


    var Layout = Card.extend({
        className: "monitoring-layout",

        template: monitoringTmpl,

        initialize: function(options) {
            shared.models.appeal = this.model = this.options.appeal;
            shared.appealJSON = shared.models.appeal.toJSON();
            this.canPrint = false;

            //pubsub.on('appeal:closed', function() { //когда закрыли историю болезни
                //shared.models.appeal.fetch();
            //});

            shared.models.appeal.on('change reset', function() {
                //console.log('appeal change', shared.models.appeal);
                this.render();
            }, this);

            var eventId = shared.models.appeal.get('id');
            var patientId = shared.models.appeal.get('patient').get('id');

            //collections

            this.therapiesCollection = new TherapiesCollection(null, {
                eventId: eventId,
                patientId: patientId
            });

            this.patientDiagnoses = new PatientDiagnoses(null, {
                appealId: eventId
            });

            this.patientBloodTypes = new PatientBloodTypes([], {
                patientId: patientId
            });

            this.bloodTypesDict = new DictionaryValues([], {
                name: "bloodTypes"
            });

            this.moves = new Moves();
            this.moves.appealId = options.appeal.get("id");

            this.monitoringInfos = new MonitoringInfos(null, {
                appealId: eventId
            });

            this.allPersons = new Persons();
            this.departmentPersons = new Persons();
            this.appealExecPerson = new AppealExecPerson();

            //views

            this.chemotherapyInfo = new ChemotherapyInfo({
                collection: this.therapiesCollection
            });

            this.patientDiagnosesList = new PatientDiagnosesList({
                appeal: options.appeal,
                appealId: eventId,
                collection: this.patientDiagnoses
            });

            this.execPersonAssignmentDialog = new ExecPersonAssignmentDialog({
                appeal: options.appeal,
                appealExecPerson: this.appealExecPerson,
                allPersons: this.allPersons,
                departmentPersons: this.departmentPersons
            });

            this.signalInfo = new SignalInfo({
                moves: this.moves,
                appeal: options.appeal,
                appealJSON: options.appeal.toJSON(),
                execPersonAssignmentDialog: this.execPersonAssignmentDialog
            });


            this.patientBloodTypeRow = new PatientBloodTypeRow({
                appeal: options.appeal,
                patientBloodTypes: this.patientBloodTypes,
                bloodTypesDict: this.bloodTypesDict
            });


            this.patientBloodTypeHistoryRow = new PatientBloodTypeHistoryRow({
                collection: this.patientBloodTypes
            });

            this.patientBsaRow = new PatientBsaRow({
                collection: this.monitoringInfos
            });


            this.patientInfo = new PatientInfo({
                appealJSON: options.appeal.toJSON(),
                patientBsaRow: this.patientBsaRow,
                patientBloodTypeRow: this.patientBloodTypeRow,
                patientBloodTypeHistoryRow: this.patientBloodTypeHistoryRow
            });



            this.header = new Header({
                appeal: options.appeal
            });

        },

        render: function() {
            this.trigger("change:printState");

            //console.time("layout render time");

            this.$el.html(_.template(this.template, shared.appealJSON));

            this.assign({
                ".monitoring-layout-header": this.header,
                ".patient-info": this.patientInfo,
                ".signal-info": this.signalInfo,
                ".patient-diagnoses-list": this.patientDiagnosesList,
                ".chemotherapy-info": this.chemotherapyInfo
                // ,".monitoring-info": new MonitoringInfoGrid()
                // ,".express-analyses": new ExpressAnalyses()
            });

            this.patientDiagnoses.fetch();
            this.moves.fetch();
            this.bloodTypesDict.fetch();

            //console.timeEnd("layout render time");

            return this;
        }
    });

    return Layout;

});
