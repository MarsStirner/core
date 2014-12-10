define(function(require) {
	var shared = require('views/appeal/edit/pages/monitoring/shared');

	var expressAnalysesItemTmpl = require('text!templates/appeal/edit/pages/monitoring/express-analyses-item2.tmpl');
	var expressAnalysesTmpl = require('text!templates/appeal/edit/pages/monitoring/express-analyses2.tmpl');

	var ClientSortableGrid = require('views/appeal/edit/pages/monitoring/views/ClientSortableGrid');
	var ExpressAnalyses = require('views/appeal/edit/pages/monitoring/collections/ExpressAnalyses');


	var ExpressAnalysesView = ClientSortableGrid.extend({
		template: expressAnalysesTmpl,
		itemTemplate: expressAnalysesItemTmpl,
		events: _.extend({
			"click .toggle": "toggle"
		}, ClientSortableGrid.prototype.events),
		toggle: function(event) {
			var $target = this.$(event.target);
			this.$('.toggle-icon').toggleClass('icon-chevron-down').toggleClass('icon-chevron-up');
			this.$('tbody tr').not('tbody tr:first-child').toggle();
		},

		data: function() {
			var result = this.collection.byKeys();
			console.log('result',result)
			return {
				result: result,
				collection: this.collection.models,//.slice(0, 5),
				appealId: shared.models.appeal.get('id'),
				showLabsLink: this.showLabsLink
			};
		},

		initialize: function(options) {
			if ((Core.Cookies.get("currentRole") === 'nurse-department') || (Core.Cookies.get("currentRole") === 'nurse-receptionist')) {
				this.showLabsLink = false;
			} else {
				this.showLabsLink = true;
			}

			this.collection = new ExpressAnalyses();
			ClientSortableGrid.prototype.initialize.apply(this);
		}
	});

	return ExpressAnalysesView;
});
