/**
 * User: FKurilov
 * Date: 13.08.12
 */
define(function () {
	App.Models.DictionaryValue = Model.extend({
		defaults: {
			value: ""
		}
	});

	return App.Models.DictionaryValue;
});