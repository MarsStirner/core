define(function(require) {
	// var shared = require('views/appeal/edit/pages/monitoring/shared');

	/**
	 * Модель диагноза пациента
	 * @type {*}
	 */
	var PatientDiagnosis = Model.extend({
		defaults: {
			"diagnosticId": "",
			"diagnosisKind": "",
			"datetime": "",
			"description": "",
			"injury": "",
			"doctor": {
				"name": {
					"first": "",
					"last": "",
					"middle": "",
					"raw": ""
				}
			},
			"mkb": {
				"id": "",
				"code": "",
				"diagnosis": ""
			}
		}
	});

	/**
	 * Коллекция диагнозов пациента
	 * @type {*}
	 */

	var PatientDiagnoses = Collection.extend({
		initialize: function(models, options) {
			this.appealId = options.appealId;
		},

		model: PatientDiagnosis,

		diagKinds: {
			"finalMkb": {
				priority: 0,
				title: "Заключительный"
			},

			"mainDiagMkb": {
				priority: 1,
				title: "Клинический"
			},
			"assocDiagMkb": {
				priority: 2,
				title: "Сопутствующий к клиническому"
			},
			"diagComplMkb": {
				priority: 3,
				title: "Осложнения к клиническому"
			},

			"admissionMkb": {
				priority: 4,
				title: "Диагноз при поступлении"
			},

			"diagReceivedMkb": {
				priority: 5,
				title: "Направительный диагноз"
			},

			"aftereffectMkb": {
				priority: 6,
				title: "Осложнения к направительному"
			},
			"attendantMkb": {
				priority: 7,
				title: "Сопутствующий к направительному"
			},
			"mainDiagMkbPat": {
				priority: 8,
				title: "Патологоанатомический диагноз. Основное заболевание по МКБ"
			},
			"complDi1_1_01agMkbPat": {
				priority: 9,
				title: "Патологоанатомический диагноз. Осложнения по МКБ"
			},
			"assocDiagMkbPat": {
				priority: 10,
				title: "Патологоанатомический диагноз. Сопутствующие заболевания по МКБ."
			},
			"aftereffectFinalMkb": {
				priority: 11,
				title: "Сопутствующий к заключительному клиническому"
			},
			"attendantFinalMkb": {
				priority: 12,
				title: "Осложнения к заключительному клиническому"
			}
		},

		url: function() {
			return DATA_PATH + "appeals/" + this.appealId + "/diagnoses/";
		},

		comparator: function(a, b) {
			var apr = this.diagKinds[a.get("diagnosisKind")].priority;
			var bpr = this.diagKinds[b.get("diagnosisKind")].priority;

			if (apr > bpr) {
				return 1;
			} else if (apr < bpr) {
				return -1;
			} else {
				return 0;
			}
		},

		parse: function(raw) {
			var data = Collection.prototype.parse.call(this, raw);

			_.each(data, function(diag) {
				diag.diagnosisKindLabel = this.diagKinds[diag.diagnosisKind].title;

				if (_.contains(["attendantMkb", "aftereffectMkb", "diagReceivedMkb"], diag.diagnosisKind)) {
					diag.doctor = {};
				}

			}, this);

			if (!data.length) {
				//getting first move data
				var Moves = require('collections/moves/moves');
				var moves = new Moves();
				var diagnoses = [];
				var diagnosesAttrs = [];
				var diagReceived = {};
				var attendant = {};
				var aftereffect = {};
				moves.appealId = this.appealId;
				$.ajaxSetup({async: false});
				moves.fetch();
				if (moves.models.length) {
					$.getJSON(DATA_PATH + "appeals/" + moves.appealId + "/documents/" + moves.models[0].get('id') + "?callback=?", function (response) {
						firstMoveDatetime = $.grep(response.data[0].group[0].attribute, function(e){ return e.name == 'assessmentBeginDate'; })[0].value;
						var firstMoveAttrs = response.data[0].group[1].attribute;
						var moveDate = $.grep(response.data[0].group[0].attribute, function(e){ return e.name == 'assessmentBeginDate'; })[0].properties[0].value;
						moveDate = new Date(moveDate).valueOf();
						//getting diagnoses from first move attributes
						$.each(firstMoveAttrs, function(i, attr){
							if (attr.code === 'diagReceivedMkb') {
								var diagReceivedAttr = $.grep(attr.properties, function(e){ return e.name == 'value'; });
								var diagReceivedId = $.grep(attr.properties, function(e){ return e.name == 'valueId'; });
								if (diagReceivedAttr.length > 0) {
									diagReceived.id = diagReceivedId[0].value;
									diagReceived.diagnosis = diagReceivedAttr[0].value;
									diagReceived.label = attr.name;
									diagReceived.kind = 'diagReceivedMkb';
									diagReceived.datetime = moveDate;
									diagnosesAttrs.push(diagReceived);
								}
							} else if (attr.code === 'diagAttendantMkb') {
								var attendantAttr  = $.grep(attr.properties, function(e){ return e.name == 'value'; });
								var attendantId = $.grep(attr.properties, function(e){ return e.name == 'valueId'; });
								if (attendantAttr.length > 0) {
									attendant.id = attendantId[0].value;
									attendant.diagnosis = attendantAttr[0].value;
									attendant.label = attr.name;
									attendant.kind = 'attendantMkb';
									attendant.datetime = moveDate;
									diagnosesAttrs.push(attendant);
								}
							} else if (attr.code === 'diagAttendantDescriptionMkb') {
								var attendantDescriptionAttr  = $.grep(attr.properties, function(e){ return e.name == 'value'; });
								if (attendantDescriptionAttr.length > 0) {
									attendant.description = $(attendantDescriptionAttr[0].value).text();
								}
							} else if (attr.code === 'diagReceivedDescriptionMkb') {
								var diagReceivedDescriptionAttr  = $.grep(attr.properties, function(e){ return e.name == 'value'; });
								if (diagReceivedDescriptionAttr.length > 0) {
									diagReceived.description = $(diagReceivedDescriptionAttr[0].value).text();
								}
							} else if (attr.code === 'diagAftereffectMkb') {
								var aftereffectAttr  = $.grep(attr.properties, function(e){ return e.name == 'value'; });
								var aftereffectId = $.grep(attr.properties, function(e){ return e.name == 'valueId'; });
								if (aftereffectAttr.length > 0) {
									aftereffect.id = aftereffectId[0].value;
									aftereffect.diagnosis = aftereffectAttr[0].value;
									aftereffect.label = attr.name;
									aftereffect.kind = 'aftereffectMkb';
									aftereffect.datetime = moveDate;
									diagnosesAttrs.push(aftereffect);
								}
							} else if (attr.code === 'diagAftereffectDescriptionMkb') {
								var aftereffectDescriptionAttr  = $.grep(attr.properties, function(e){ return e.name == 'value'; });
								if (aftereffectDescriptionAttr.length > 0) {
									aftereffect.description = $(aftereffectDescriptionAttr[0].value).text();
								}
							}
						});

						$.each(diagnosesAttrs, function(i, item){
							diagnoses.push({
								datetime: item.datetime,
								description: item.description,
								diagnosisKind: item.kind,
								diagnosisKindLabel: item.label,
								doctor: {},
								mkb: {
									code: '',
									diagnosis: item.diagnosis,
									id: item.id
								}
							});
						});	
					});
				};
				$.ajaxSetup({async: true});	
				return diagnoses;
			} else {
				return data;
			}
		}
	});


	return PatientDiagnoses;
});
