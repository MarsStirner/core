define(function(require) {

	var commonData = require('mixins/commonData');

	var InstrumentalResearch = Model.extend({
		idAttribute: "id",
		initialize: function(attr, options) {

			console.log('instrumental-research', arguments);
			var appealId = false;
			if (options.appealId) { //если модель создаётся самостоятельно
				appealId = options.appealId;
			}

			if (options.collection && options.collection.appealId) { //если модель создаётся коллекцией
				appealId = options.collection.appealId;
			}

			if (!appealId) { //для создания модели нужен appealId
				throw new Error("Нет appealId для InstrumentalResearch");
			} else {
				this.appealId = appealId;
			}
		},
		urlRoot: function() {
			return DATA_PATH + 'appeals/' + this.appealId + '/diagnostics/instrumental';
		},
		sync: function(method, model, options) {
			console.log('model', model);
			options = options || {};
			options.dataType = "jsonp";
			options.contentType = 'application/json';
			switch (method.toLowerCase()) {
				case 'update':
					options.data = JSON.stringify({
						requestData: {},
						data: [model.toJSON()]
					});
					break;
			}

			return Backbone.sync(method, model, options);
		},
		parse: function(raw) {
			return raw.data ? raw.data[0] : raw;
			//raw когда парсим модель в коллекции, raw.data[0] - когда получаем отдельную модель
		}

	}).mixin([commonData]);

	return InstrumentalResearch;
});
