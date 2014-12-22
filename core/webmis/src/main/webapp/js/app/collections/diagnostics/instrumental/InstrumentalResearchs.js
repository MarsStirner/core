define(function(require) {

	var InstrumentalResearch = require('models/diagnostics/instrumental/InstrumentalResearch');

	var InstrumentalResearchs = Collection.extend({
		model: InstrumentalResearch,
		initialize: function(models, options) {
			console.log('init', arguments);
			if (!options.appealId) {
				throw new Error("Нет appealId для InstrumentalResearchs");
			} else {
				this.appealId = options.appealId;
			}
		},

		url: function() {
			return DATA_PATH + 'appeals/' + this.appealId + '/diagnostics/instrumental';
		}

		,saveAll: function(opt) {
			var collection = this;
			var options = opt || {};
            console.log('save all')
			options = _.extend(options, {
				dataType: "jsonp",
				contentType: 'application/json',
				data: JSON.stringify({
					data: collection.toJSON()
				})
			});

			return Backbone.sync('create', this, options);
		}

	});


	return InstrumentalResearchs;

});
