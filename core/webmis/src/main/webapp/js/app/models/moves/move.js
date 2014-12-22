/**
 * User: FKurilov
 * Date: 18.10.12
 */
define(function () {

	App.Models.Move = Model.extend({
		defaults: {
			"clientId": "",
			"bedId": "",
			"moveDatetime": "",
			"unitId": ""
		},

		appealId: null,

		parse: function (raw) {
			return raw.move;
		},

		toJSON: function () {
			return {move: Model.prototype.toJSON.call(this)};
		},

		url: function () {
			if (this.appealId) {
				return DATA_PATH + "appeals/" + this.appealId + "/hospitalbed/moving/";
			} else {
				throw new Error("No appeal id to move");
			}
		}
	});

	return App.Models.Move;
});
