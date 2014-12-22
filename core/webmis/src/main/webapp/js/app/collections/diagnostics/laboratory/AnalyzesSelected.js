define(function(require) {

	var SelectedTests = Collection.extend({
		initialize: function(models, options) {
			this.options = options;
			if (!this.options.appeal) {
				throw new Error('SelectedTests: Нет appeal')
			}
		},
		url: function() {
			return DATA_PATH + 'appeals/' + this.options.appeal.get('id') + '/diagnostics/laboratory'
		},
		getPicked: function() {
			return this.filter(function(model) {
				return model.get('picked');
			});
		},
		updateAll: function() {
			var collection = this;
			options = {
				dataType: "jsonp",
				contentType: 'application/json',
				success: function(data, status) {
					//console.log('updateAll success', arguments);
					if (status == 'success') {
						collection.trigger('updateAll:success', arguments);
					} else {
						//collection.trigger('updateAll:error',status);
					}
				},
				error: function(x, status) {

					var response = $.parseJSON(x.responseText);
					collection.trigger('updateAll:error', response);
					//console.log('updateAll error', response.exception, response.errorCode, response.errorMessage, arguments);
				},
				data: JSON.stringify({
					data: collection.toJSON()
				})
			};

			return Backbone.sync('create', this, options);
		}

	});


	return SelectedTests;

});
