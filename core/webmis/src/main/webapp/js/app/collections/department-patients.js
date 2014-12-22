/**
 * User: FKurilov
 * Date: 28.05.12
 */
define(["models/department-patient"], function() {
	App.Collections.DepartmentPatients = Collection.extend({
		model: App.Models.DepartmentPatient,

		initialize: function(options) {
			Collection.prototype.initialize.call(this);


		},
		url: function() {
			return DATA_PATH + "departments/patients/";
		},
		parse: function(raw) {
			checkForWarnings(raw.requestData, "requestData was not found in the JSON");
			this.requestData = raw.requestData || {};
			this.requestData.filter = this.requestData.filter || {};

			if (raw.data && raw.data.length > 0) {
				_.each(raw.data, function(patient, index, list) {
					//нужно выводить целые числа
					if (patient.condition && patient.condition.breathingRate) {
						patient.condition.breathingRate = parseInt(patient.condition.breathingRate);
					}

					if (patient.condition && patient.condition.arterialBloodPressure && patient.condition.arterialBloodPressure.low) {
						patient.condition.arterialBloodPressure.low = parseInt(patient.condition.arterialBloodPressure.low);
					}

					if (patient.condition && patient.condition.arterialBloodPressure && patient.condition.arterialBloodPressure.high) {
						patient.condition.arterialBloodPressure.high = parseInt(patient.condition.arterialBloodPressure.high);
					}

				})
			}

			return raw.data
		}
	});
});
