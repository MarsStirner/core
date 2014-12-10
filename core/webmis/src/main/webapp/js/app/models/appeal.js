define([
	"models/date",
	"models/patient",
	"models/doctor",
	"collections/relations",
	"collections/diagnoses"
], function () {

	var AppealTypePart = Model.extend({
		defaults: {
			"id": "",
			"name": ""
		}
	});

	var AppealType = Model.extend({
		/*defaults: {
		 id: 168,
		 name: "Дневной стационар"
		 }*/
		defaults: {
			"eventType": {},
			"requestType": {},
			"finance": {}
		},

		relations: [
			{
				type: Backbone.HasOne,
				key: "eventType",
				relatedModel: AppealTypePart
			},
			{
				type: Backbone.HasOne,
				key: "requestType",
				relatedModel: AppealTypePart
			},
			{
				type: Backbone.HasOne,
				key: "finance",
				relatedModel: AppealTypePart
			}
		]
	});
	var HospitalizationType = Model.extend({
		defaults: {
			id: -1,
			name: ""
		}
	});
	var HospitalizationPointType = Model.extend({
		defaults: {
			id: -1,
			name: ""
		}
	});
	/*var HospitalizationChannelType = Model.extend({
	 defaults: {
	 id: -1,
	 name: ""
	 }
	 });*/
	/*var DeliveredType = Model.extend({
	 defaults: {
	 id: -1,
	 name: ""
	 }
	 });*/
	/*var DeliveredAfterType = Model.extend({
	 defaults: {
	 id: -1,
	 name: ""
	 }
	 });*/
	/*var StateType = Model.extend({
	 defaults: {
	 id: -1,
	 name: ""
	 }
	 });*/
	//TODO: что ето было?
	/*var DrugsType = Model.extend({
	 defaults: {
	 id: -1,
	 name: ""
	 }
	 });*/
	var BranchType = Model.extend({
		defaults: {
			id: -1,
			name: ""
		}
	});
	var PlaceType = Model.extend({
		defaults: {
			id: -1,
			name: ""
		}
	});

	var BloodPressureValues = Model.extend({
		defaults: {
			syst: "",
			diast: ""
		},
		clean: function () {
			this.set({
				syst: this.get("syst").toString().replace(/\D/gi, ""), //.replace("\\", "/"),
				diast: this.get("diast").toString().replace(/\D/gi, "") //.replace("\\", "/")
			})
		}
	});
	var PhysicalParametersBloodPressure = Model.extend({
		defaults: {
			left: {},
			right: {}
		},
		/*defaults: {
		 left: "",
		 right: ""
		 },*/
		relations: [
			{
				type: Backbone.HasOne,
				key: "left",
				relatedModel: BloodPressureValues
			},
			{
				type: Backbone.HasOne,
				key: "right",
				relatedModel: BloodPressureValues
			}
		]
	});
	var PhysicalParameters = Model.extend({
		defaults: {
			bloodPressure: {},
			temperature: "",
			weight: "",
			height: ""
		},
		relations: [
			{
				type: Backbone.HasOne,
				key: "bloodPressure",
				relatedModel: PhysicalParametersBloodPressure
			}
		]/*,
		 clean: function () {
		 this.set({
		 //temperature: this.get("temperature").toString().replace(/\D/, ""),
		 weight: this.get("weight").toString().replace(/\D/, ""),
		 height: this.get("height").toString().replace(/\D/, "")
		 });
		 }*/
	});


	/*var AssignmentDirected = Model.extend({
	 defaults: {
	 id: -1,
	 name: ""
	 }
	 });*/
	var Assignment = Model.extend({
		defaults: {
			"directed": "",
			"doctor": "",
			"assignmentDate": "",
			"number": ""
		}/*,
		 relations: [
		 {
		 type: Backbone.HasOne,
		 key: "directed",
		 relatedModel: AssignmentDirected
		 },
		 {
		 type: Backbone.HasOne,
		 key: "doctor",
		 relatedModel: "App.Models.Doctor"
		 }
		 ]*/
	});

	var AgreedType = Model.extend({
		defaults: {
			//id: -1,
			//name: ""
		}
	});

	var Relative = Model.extend({
		defaults: {
			id: "",
			name: "",
			birthDate: ""
		}
	});

	var RelativeType = Model.extend({
		defaults: {
			id: "",
			name: ""
		}
	});

	var Representative = Model.extend({
		defaults: {
			relative: {},
			relativeType: {},
			note: ""
		},

		relations: [
			{
				type: Backbone.HasOne,
				key: "relative",
				relatedModel: Relative
			},
			{
				type: Backbone.HasOne,
				key: "relativeType",
				relatedModel: RelativeType
			}
		]
	});

	var Representatives = Collection.extend({
		model: Representative
	});

	var SetPerson = Model.extend({
		defaults: {
			doctor: {},
			department: {}
		},

		relations: [
			{
				type: Backbone.HasOne,
				key: "doctor",
				relatedModel: "App.Models.Doctor"
			},
			{
				type: Backbone.HasOne,
				key: "department",
				relatedModel: "App.Models.Department"
			}
		]
	});

	var ContractFinance = Model.extend({
		defaults: {
			name: ""
		}
	});

	var Contract = Model.extend({
		defaults: {
			number: "",
			begDate: "",
			finance: {}
		},

		relations: [
			{
				type: Backbone.HasOne,
				key: "finance",
				relatedModel: ContractFinance
			}
		]
	});

	App.Models.Appeal = Model.extend({
		idAttribute: "id",

		/*initialize: function () {

		 },*/

		isClosed: function () {
			//return Boolean(this.get("rangeAppealDateTime").get("end"));
			return Boolean(this.closed);
		},

		urlRoot: function () {
			return this.isNew() ?
				(DATA_PATH + "patients/" + this.get("patient").get("id") + "/appeals/") :
				(DATA_PATH + "appeals/");
		},

		/*url: function () {
			//return this.isNew() ? (DATA_PATH + "patients/" + this.get("patient").get("id") + "/appeals/") :
			//return ((this.isNew() || this.isSaving) ? (DATA_PATH + "patients/" + this.get("patient").get("id") + "/appeals/") : (this.urlRoot() + this.get("id")));
		},*/

		save: function () {
			this.isSaving = true;

			var emptyDiags = [];

			/*this.get("diagnoses").each(function (diag) {
			 if (!_.isUndefined(diag.get("mkb").get("id")) &&
			 diag.get("mkb").get("id").toString() &&
			 diag.get("mkb").get("id") === 0 //&&
			 //!diag.get("description")
			 ) {
			 //emptyDiags.push(diag);
			 diag.get("mkb").unset("id");
			 }
			 });*/

			if (emptyDiags.length) {
				this.get("diagnoses").remove(emptyDiags);
			}

			if (!this.get("rangeAppealDateTime").get("end")) {
				this.get("rangeAppealDateTime").set("end", new Date().getTime());
			}

			return Model.prototype.save.call(this);
		},

		defaults: {
			version: "",
			number: "",
			urgent: false,
			ambulanceNumber: "",
			rangeAppealDateTime: {},
			refuseAppealReason: "",
			appealWithDeseaseThisYear: "первично",
			patient: {},
			appealType: {},
			//relations: [{}],
			assignment: {},
			hospitalizationType: {},
			hospitalizationPointType: {},
			//hospitalizationChannelType: {},
			deliveredType: "",
			deliveredAfterType: "",
			movingType: "может идти",
			stateType: "",
			//drugsType: {},
			physicalParameters: {},
			branchType: {},
			placeType: {},
			diagnoses: [
				{}
			],
			agreedDoctor: "",
			agreedType: {},
			injury: "",
			hospitalizationWith: [],
			//bold
			havePrimary: false,
			setPerson: {},
			contract: {},
			leaved: {
				nextHospDate: null,
				nextHospDepartment: null,
				nextHospFinanceType: null
			},
			orgStructStay: null,
			orgStructDirectedFrom: null,
            reopening: null
		},
		relations: [
			{
				type: Backbone.HasOne,
				key: "appealType",
				relatedModel: AppealType
			},
			{
				type: Backbone.HasOne,
				key: "assignment",
				relatedModel: Assignment
			},
			{
				type: Backbone.HasOne,
				key: "physicalParameters",
				relatedModel: PhysicalParameters
			},
			{
				type: Backbone.HasOne,
				key: "hospitalizationType",
				relatedModel: HospitalizationType
			},
			{
				type: Backbone.HasOne,
				key: "hospitalizationPointType",
				relatedModel: HospitalizationPointType
			},
			/*{
			 type: Backbone.HasOne,
			 key: "hospitalizationChannelType",
			 relatedModel: HospitalizationChannelType
			 },*/
			/*{
			 type: Backbone.HasOne,
			 key: "deliveredType",
			 relatedModel: DeliveredType
			 },*/
			/*{
			 type: Backbone.HasOne,
			 key: "deliveredAfterType",
			 relatedModel: DeliveredAfterType
			 },*/
			/*{
			 type: Backbone.HasOne,
			 key: "stateType",
			 relatedModel: StateType
			 },*/
			/*{
			 type: Backbone.HasOne,
			 key: "drugsType",
			 relatedModel: DrugsType
			 },*/
			{
				type: Backbone.HasOne,
				key: "branchType",
				relatedModel: BranchType
			},
			{
				type: Backbone.HasOne,
				key: "placeType",
				relatedModel: PlaceType
			},
			{
				type: Backbone.HasOne,
				key: "rangeAppealDateTime",
				relatedModel: "App.Models.Date"
			},
			/*{
			 type: Backbone.HasMany,
			 key: "relations",
			 relatedModel: "App.Models.Relation",
			 collectionType: "App.Collections.Relations"
			 },*/
			{
				type: Backbone.HasMany,
				key: "diagnoses",
				relatedModel: "App.Models.Diagnosis",
				collectionType: "App.Collections.Diagnoses"
			},
			/*{
			 type: Backbone.HasOne,
			 key: "agreedDoctor",
			 relatedModel: "App.Models.Doctor"
			 },*/
			{
				type: Backbone.HasOne,
				key: "patient",
				relatedModel: "App.Models.Patient"
			},
			{
				type: Backbone.HasOne,
				key: "agreedType",
				relatedModel: AgreedType
			},
			{
				type: Backbone.HasMany,
				key: "hospitalizationWith",
				relatedModel: Representative,
				collectionType: Representatives
			},
			{
				type: Backbone.HasOne,
				key: "setPerson",
				relatedModel: SetPerson
			},
			{
				type: Backbone.HasOne,
				key: "contract",
				relatedModel: Contract
			}
		],

		getAge: function(){
			return Core.Date.getAgeString(this.get('patient').get('birthDate'));
		},

		/***
		 *
		 * @returns {*} false или модель диагноза admission, assignment, в соответствии с приоритетом.
		 */
		getDiagnosis: function () {
			var diagnosesCollection = this.get('diagnoses');
			if (diagnosesCollection.length) {
				//console.log('есть диагнозы',view.appeal.get('diagnoses').toJSON());
				var model = false;
//                        var priorities = ['final','clinical','admission','assignment'];
//                        var diagnosesModels = [];
//
//                        _.each(priorities,function(priority){
//                            var diagnosis = diagnosesCollection.find(function(model){
//                                return model.get('diagnosisKind') == priority;
//                            });
//
//                            if(diagnosis){
//                                var obj = {};
//                                obj[priority]=diagnosis;
//                                diagnosesModels.push(obj);
//                            }
//
//                        });
//                        console.log('diagnosesModels',diagnosesModels)

				var admission = diagnosesCollection.find(function (model) {
					return model.get('diagnosisKind') == 'admission';
				});
				var assignment = diagnosesCollection.find(function (model) {
					return model.get('diagnosisKind') == 'assignment';
				});

				if (assignment && assignment.get('mkb') && assignment.get('mkb').get('diagnosis')) {
					model = assignment;
				}

				if (admission && admission.get('mkb') && admission.get('mkb').get('diagnosis')) {
					model = admission;
				}

				//console.log('getDiagnosis',model);


			}
			return model;
		},

		parse: function (data) {
			//var newAppeal = new App.Models.Appeal;
			data = data.data ? data.data : data;

			//return Core.Objects.mergeAll( newAppeal.toJSON(), data )

			_(data).each(function (prop, key) {
				if (_.isNull(prop)) data[key] = this.defaults[key];

				/*if (key === "diagnoses" && data[key].length) {
				 _(data[key]).each(function (diagnosis) {
				 if (diagnosis.mkb && (diagnosis.mkb.id === 0)) {
				 delete diagnosis.mkb.id;
				 }
				 });
				 }*/
			}, this);

			return Model.prototype.parse.call(this, data);
		}

	});

	return App.Models.Appeal;
});
