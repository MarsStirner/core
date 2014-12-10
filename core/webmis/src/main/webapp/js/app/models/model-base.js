define(function () {
	return Backbone.Model.extend({
		sync: function (method, model, options) {
			options.dataType = "jsonp";
			options.url = model.url();
			options.contentType = 'application/json';

			if (method == "create" || method == "update") {
				options.data = JSON.stringify({
					requestData: {},
					data: model.toJSON()
				});
			}
			return Backbone.sync(method, model, options);
		}
	});
});