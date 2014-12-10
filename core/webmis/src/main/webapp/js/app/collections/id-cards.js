define(["models/id-card"], function () {
	var IdCards = App.Collections.IdCards = Collection.extend ({
		model: App.Models.IdCard,

		dictionaries: {
			docTypes: []
		},

		setDictionaries: function (dicts) {
			this.dictionaries = dicts;

			this.trigger("dictionaries-change");

			return this;
		},

		getDictionaries: function () {
			return this.dictionaries;
		}
	});

	return IdCards;
});