define(function(require) {

	var commonData = require('mixins/commonData');

	return Model.extend({
		initialize: function(attr, options) {

			if (!options || !options.code) {
				throw new Error("Нет кода исследования для InstrumentalResearchTemplate");
			} else {
				this.code = options.code;
			}

			if (!options || !options.patientId) {
				throw new Error("Нет айди пациента для InstrumentalResearchTemplate");
			} else {
				this.patientId = options.patientId;
			}

		},
		parse: function(raw){
			return raw.data[0];
		},

		url: function() {
			return DATA_PATH + "dir/actionTypes?filter[mnem]=DIAG&filter[code]=" + this.code + "&patientId=" + this.patientId;
		}
	}).mixin([commonData]);

});
