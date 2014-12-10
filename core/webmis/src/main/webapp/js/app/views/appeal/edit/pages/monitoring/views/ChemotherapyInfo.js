define(function(require) {
	// var shared = require('views/appeal/edit/pages/monitoring/shared');

	var chemotherapyInfoTmpl = require('text!templates/appeal/edit/pages/monitoring/chemotherapy-info.html');
	var BaseView = require('views/appeal/edit/pages/monitoring/views/BaseView');

	var ChemotherapyInfo = BaseView.extend({
		template: chemotherapyInfoTmpl,

		events: {
			'click .therapy-body-toggle': 'inTherapyBodyToggleClick'
		},

		data: function() {
			console.log('this.collection',this.collection);
			return {
				therapies: this.collection.toJSON(),
				collection: this.collection,
				joinDocIds: this.joinDocIds
			};
		},

		joinDocIds: function(days){
			var docIds = (_.pluck(days, 'docId')).join(',');

			return docIds;
		},

		initialize: function(options) {
			var self = this;
			BaseView.prototype.initialize.apply(this);

			this.collection.fetch()
				.done(function() {
					self.render();
				});
		},

		inTherapyBodyToggleClick: function (event) {
			$(event.currentTarget).toggleClass('icon-plus icon-minus').closest('.therapy-header').next().slideToggle('fast');
		}
	});

	return ChemotherapyInfo;

});
