define(function () {

	var SurgicalOperations = Collection.extend ({
		initialize: function(models, options) {
			this.appealId = options.appealId;
			Collection.prototype.initialize.call(this, models, options)
		},
		url: function () {
			return DATA_PATH + "appeals/" + this.appealId + "/surgical/";
		},
		model: Backbone.Model.extend({})
	});

	return SurgicalOperations;
});
