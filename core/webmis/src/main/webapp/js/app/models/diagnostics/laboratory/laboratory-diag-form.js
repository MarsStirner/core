define(function (require) {
	var commonData = require('mixins/commonData');

	return  Model.extend({
		//idAttribute: "id",
		initialize: function () {
			
		},


		url: DATA_PATH + 'diagnostics/laboratory/',

		parse: function (raw) {
			var data = _(raw.data).isArray() ? _(raw.data).first() : raw.data;
			data.generalAttrs = data.group[0]["attribute"];
			return data;
		},

		destroy: function (options) {

			//console.log('destroy')
			options = options ? _.clone(options) : {};
			var model = this;
			var success = options.success;

			var destroy = function () {
				model.trigger('destroy', model, model.collection, options);
			};

			options.success = function (model, resp, options) {
				if (options.wait || model.isNew()) destroy();
				if (success) success(model, resp, options);
			};

//			if (this.isNew()) {
//				options.success(this, null, options);
//				return false;
//			}

			var xhr = this.sync('delete', this, options);
			if (!options.wait) destroy();
			return xhr;
		},

		sync: function (method, model, options) {
			//console.log('options1', options.success);
			options = options || {};
			options.dataType = "jsonp";
			options.contentType = 'application/json';

			//console.log('model',model);

			switch (method.toLowerCase()) {
				case 'read':
					options.url = DATA_PATH + 'appeals/' + model.eventId + '/diagnostics/laboratory/' + model.id;
					break;
				case 'create':
					options.url = DATA_PATH + 'appeals/' + model.eventId + '/diagnostics/laboratory';
					options.data = JSON.stringify({
						requestData: {},
						data: [model.toJSON()]
					});
					break;
				case 'update':
					options.url = DATA_PATH + 'appeals/' + model.eventId + '/diagnostics/laboratory/' + model.get('id');
					options.data = JSON.stringify({
						requestData: {},
						data: [model.toJSON()]
					});
					break;
				case 'delete':
					options.url = DATA_PATH + 'appeals/' + model.eventId + '/diagnostics/laboratory';
					options.type = 'DELETE';
					options.data = JSON.stringify({
						//requestData:{},
						data: [
							{'id': model.get('id')}
						]
					});

					break;
			}
			//console.log('options2', options.success)
			return Backbone.sync.call(Backbone, method, model, options);
		}

	}).mixin([commonData]);


});
