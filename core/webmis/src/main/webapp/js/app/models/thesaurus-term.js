/**
 * User: FKurilov
 * Date: 22.08.12
 */
define(["collections/thesaurus-terms"], function () {
	App.Models.ThesaurusTerm = Model.extend({
		defaults: {
			name: "",
			code: "",
			groupId: "",
			childrenTerms: []
		},

		relations: [
			{
				type: Backbone.HasMany,
				key: "childrenTerms",
				relatedModel: "App.Models.ThesaurusTerm",
				collectionType: "App.Collections.ThesaurusTerms",
				collectionOptions: function (model) {
					return {parentGroupId: model.get("id")};
				}
			}
		]
	});

	return App.Models.ThesaurusTerm;
});