define(["models/doctor", "models/date", "models/department", "models/diagnosis"], function ()
{
	var ExecPerson = Model.extend({
		defaults: {
			"doctor": {},
			"department": {}
		},

		relations: [
			{
				type: Backbone.HasOne,
				key: "doctor",
				relatedModel: App.Models.Doctor
			},
			{
				type: Backbone.HasOne,
				key: "department",
				relatedModel: App.Models.Department
			}
		]
	});

	App.Collections.PatientAppeals = Collection.extend({
		url: function () {
			return DATA_PATH + "patients/"+ this.patient.id + "/appeals/"
		},

		parse: function (raw) {
			var data = Collection.prototype.parse.call(this, raw);
			var diagKinds = {
				"assignment": {priority: 0, title: "Направительный диагноз"},
				"admission": {priority: 1, title: "Диагноз при поступлении"},
				"clinical": {priority: 2, title: "Клинический"},
				"final": {priority: 3, title: "Заключительный"},
				//NEW
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
					title: "Сопутствующий к направительному"
				},
				"attendantMkb": {
					priority: 7,
					title: "Осложнения к направительному"
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
			};

			_.each(data, function (patientAppeal) {
				patientAppeal.diagnoses = patientAppeal.diagnoses.sort(function (a, b) {
					var apr = diagKinds[a.diagnosisKind].priority;
					var bpr = diagKinds[b.diagnosisKind].priority;

					if (apr > bpr) {
						return 1;
					} else if (apr < bpr) {
						return -1;
					} else {
						return 0;
					}
				});
				_.each(patientAppeal.diagnoses, function (diag) {
					if (diagKinds[diag.diagnosisKind]) {
						diag.diagnosisKindLabel = diagKinds[diag.diagnosisKind].title;
					} else {
						console.warn("Unknown diagnosisKind:", diag.diagnosisKind);
						diag.diagnosisKindLabel = diag.diagnosisKind;
					}
				});
			});

			return data;
		},

		model: Model.extend({
			defaults: {
				"number": "",
				"rangeAppealDateTime": {},
				"execPerson": {},
				"department": {
					"name": ""
				},
				"diagnoses": []
			},

			relations: [
				{
					type: Backbone.HasOne,
					key: "rangeAppealDateTime",
					relatedModel: App.Models.Date
				},
				{
					type: Backbone.HasOne,
					key: "execPerson",
					relatedModel: ExecPerson
				},
				{
					type: Backbone.HasMany,
					key: "diagnoses",
					relatedModel: App.Models.Diagnosis,
					collectionType: App.Collections.Diagnoses
				}
			]
		})
	});

	return App.Collections.PatientAppeals;
});
