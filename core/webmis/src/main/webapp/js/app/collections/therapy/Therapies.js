define(function(require) {


	var Therapy = Backbone.Model.extend({
		getCurrentPhases: function() {
			return _.filter(this.get('phases'), function(phase){
				return !phase.endDate;
			});
		},

		getClosedPhases: function() {
			return _.filter(this.get('phases'), function(phase){
				return phase.endDate
			});
		}
	});

	var Therapies = Collection.extend({
		model: Therapy,

		initialize: function(models, options) {
			this.patientId = options.patientId;
		},

		url: function() {
			return '/api/v1/patients/' + this.patientId + '/therapies';
		},

		parse: function (raw) {
			var temp = Collection.prototype.parse.call(this, raw);
			_.each(temp, function (ther) {
				if (ther.endDate < 0) {
					ther.endDate = null;
				}
				_.each(ther.phases, function (phase) {
					if (phase.endDate < 0) {
						phase.endDate = null;
					}
				});
			});
			return temp;
		}
	});

	return Therapies;

})
