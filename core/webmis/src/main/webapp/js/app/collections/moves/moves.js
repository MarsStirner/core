/**
 * Author: FKurilov
 * Date: 23.05.12
 */
define(function(require) {


	var Moves = Collection.extend({
		model: Model.extend({
			defaults: {
				unitId: "",
				unit: "",
				admission: "",
				leave: "",
				days: "",
				bedDays: "",
				chamber: "",
				bed: ""
			}
		}),

		parse: function(raw) {
			return raw.data.moves;
		},

		url: function() {
			return DATA_PATH + "appeals/" + this.appealId + "/hospitalbed/"
		}
	});

	return Moves;
});
