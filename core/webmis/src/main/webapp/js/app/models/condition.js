/**
 * User: FKurilov
 * Date: 28.05.12
 */
define(function () {
	var ArterialBloodPressure = Model.extend({
		low: "",
		high: ""
	});

	App.Models.Condition = Model.extend({
		defaults: {
			state: "",
			breathingRate: "",
			arterialBloodPressure: {}
		},
		relations: [
			{
				type: Backbone.HasOne,
				key: "arterialBloodPressure",
				relatedModel: ArterialBloodPressure
			}
		]
	});

	//return App.Models.Condition;
});
