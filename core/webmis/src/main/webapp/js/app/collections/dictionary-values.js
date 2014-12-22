/**
 * User: FKurilov
 * Date: 13.08.12
 */
define(["models/dictionary-value"], function () {
	App.Collections.DictionaryValues = Collection.extend({
		model: App.Models.DictionaryValue,
		initialize: function (models, options) {
			this.name = options.name;
		},
		url: function () {
			return DATA_PATH + "dir?dictName=" + this.name;
		}
	});

	return App.Collections.DictionaryValues;
});