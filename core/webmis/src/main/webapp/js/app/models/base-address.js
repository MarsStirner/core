(function () {
	var BaseAddressPart = Model.extend({
		defaults: {
			code: "",
			name: "",
			socr: "",
			index: ""
		}
	});

	App.Models.BaseAddress = Model.extend ({
		defaults: {
			localityType: "",
			kladr: "",
			house: "",
			building: "",
			flat: "",
			fullAddress: "",
			district: {},
			locality: {},
			street: {},
			city: {},
			republic: {}
		},

		relations: [
			{
				type: Backbone.HasOne,
				key: "district",
				relatedModel: BaseAddressPart
			},
			{
				type: Backbone.HasOne,
				key: "locality",
				relatedModel: BaseAddressPart
			},
			{
				type: Backbone.HasOne,
				key: "street",
				relatedModel: BaseAddressPart
			},
			{
				type: Backbone.HasOne,
				key: "city",
				relatedModel: BaseAddressPart
			},
			{
				type: Backbone.HasOne,
				key: "republic",
				relatedModel: BaseAddressPart
			}
		]
	});
}).call();
