define(["models/base-address"], function () {

	App.Models.Address = Model.extend ({
		defaults: {
			registered: {},
			residential: {}
		},

		initialize: function () {
			this.addressesEqual = false;
		},

		getAddressesEqual: function () {
			return this.addressesEqual;
		},

		setAddressesEqual: function (value) {
			this.addressesEqual = Boolean(value);
		},

		getIsEqual: function () {
			var reg = this.get("registered").toJSON();
			var res = this.get("residential").toJSON();

			return (_.isEqual(reg, res) && (reg.fullAddress === res.fullAddress));
		},

		relations: [
			{
				type: Backbone.HasOne,
				key: "registered",
				relatedModel: "App.Models.BaseAddress"
			},
			{
				type: Backbone.HasOne,
				key: "residential",
				relatedModel: "App.Models.BaseAddress"
			}
		]
	});
});