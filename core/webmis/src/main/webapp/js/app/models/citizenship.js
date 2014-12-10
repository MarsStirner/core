define(function () {
	var SocStatusType = Model.extend({
		defaults: {
			name: ""
		}
	});

	var CitizenshipType = Model.extend ({
		defaults: {
			//id: -1,
			comment: "",
			socStatusType: {}
		},

		relations: [
			{
				type: Backbone.HasOne,
				key: "socStatusType",
				relatedModel: SocStatusType
			}
		]
	});

	App.Models.Citizenship = Model.extend ({
		defaults: {
			first: {},
			second: {}
		},

		relations: [
			{
				type: Backbone.HasOne,
				key: "first",
				relatedModel: CitizenshipType
			},
			{
				type: Backbone.HasOne,
				key: "second",
				relatedModel: CitizenshipType
			}
		]
	});
});