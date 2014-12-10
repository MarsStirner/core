define(["models/Name", "collections/phones"], function (Name) {

	var RelationType = Model.extend ({
		defaults: {
			id: "",
			name: ""
		}
	});

	App.Models.Relation = Model.extend({
		defaults: {
			//id: -1,
			name: {},
			phones: [{}],
			relationType: {}
		},

		validate: function (attrs) {
			if (!attrs) {
				var self = this;
				var errors = [];

				var conditionallyRequired = [
					this.get("name").get("first"),
					this.get("name").get("last"),
					this.get("name").get("middle"),
					this.get("relationType").get("id"),
					this.get("phones").first().get("number")
				];

				if (_(conditionallyRequired).any()) {
					if (!this.get("relationType").get("id")) {
						errors.push({property: "relations-"+self.cid+"-relationType-id", msg: "Кем приходится"});
					}
					if (!this.get("name").get("last")) {
						errors.push({property: "relations-"+self.cid+"-name-last", msg: "Фамилия родственника"});
					}
					if (!this.get("name").get("first")) {
						errors.push({property: "relations-"+self.cid+"-name-first", msg: "Имя родственника"});
					}
					if (!this.get("name").get("middle")) {
						errors.push({property: "relations-"+self.cid+"-name-middle", msg: "Отчество родственника"});
					}
					if (!this.get("phones").first().get("number")) {
						errors.push({property: "relations-"+self.cid+"-phones-"+this.get("phones").first().cid+"-number", msg: "Номер телефона родственника"});
					}

					if (errors.length) {
						return errors;
					}
				}
			}
		},

		relations: [
			{
				type: Backbone.HasOne,
				key: "name",
				relatedModel: Name
			},
			{
				type: Backbone.HasMany,
				key: "phones",
				relatedModel: "App.Models.Phone",
				collectionType: "App.Collections.Phones"
			},
			{
				type: Backbone.HasOne,
				key: "relationType",
				relatedModel: RelationType
			}
		]
	});
});
