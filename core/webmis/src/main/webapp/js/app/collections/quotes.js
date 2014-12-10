/**
 * User: FKurilov
 * Date: 13.12.12
 */
define(["models/quota"], function () {
	App.Collections.Quotes = Collection.extend({
		model: App.Models.Quota,

		url: function () {
			return DATA_PATH + "appeals/" + this.appealId + "/quotes";
		}
	});

	return App.Collections.Quotes;
});