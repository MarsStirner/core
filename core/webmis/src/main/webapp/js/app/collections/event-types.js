/**
 * User: FKurilov
 * Date: 26.11.12
 */
define(function () {
	App.Models.EventType = Model.extend({
		defaults: {
			value: ""
		}
	});

	App.Collections.EventTypes = Collection.extend({
		model: App.Models.EventType,

		url: function () {
			return DATA_PATH + "dir/eventTypes";
		}
	});
});