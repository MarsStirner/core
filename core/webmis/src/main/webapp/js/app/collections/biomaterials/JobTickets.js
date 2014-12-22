define(function(require) {

	var JobTicket = Model.extend({
		idAttribute: "id"

	});

	var JobTickets = Collection.extend({
		model: JobTicket,

		url: function() {
			return DATA_PATH + "jobTickets/status";
		},

		updateAll: function() {
			var collection = this;
			options = {
				dataType: "jsonp",
				contentType: 'application/json',
				success: function(status) {
					if (status) {
						collection.trigger('updateAll:success');
					} else {
						collection.trigger('updateAll:error');
					}
				},
				data: JSON.stringify({
					data: collection.toJSON()
				})
			};

			return Backbone.sync('update', this, options);
		}

	});

	return JobTickets;
});
