define(function(require) {

    /**
     * Группа крови пациента
     * @type {*}
     */
	var PatientBloodType = Model.extend({
		/*idAttribute: "id",

         defaults: {
         "id": -1,
         "bloodDate": "",
         "bloodType": {
         "id": "",
         "name": ""
         },
         "person": null
         }*/
	});

    /**
     * История изменения группы крови пациента
     * @type {*}
     */
	var PatientBloodTypes = Collection.extend({
		model: PatientBloodType,

		initialize: function(models, options) {
			this.patientId = options.patientId;
		},

		comparator: function(a, b) {
			var aDatetime = parseInt(a.get("id"));
			var bDatetime = parseInt(b.get("id"));

			if (aDatetime < bDatetime) {
				return 1;
			} else if (aDatetime > bDatetime) {
				return -1;
			} else {
				return 0;
			}
		},

		url: function() {
			return DATA_PATH + "patients/" + this.patientId + "/bloodtypes";
		}
	});



	return PatientBloodTypes;

})
