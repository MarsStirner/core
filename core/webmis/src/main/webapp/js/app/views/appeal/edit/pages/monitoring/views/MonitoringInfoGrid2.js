define(function(require) {
	var shared = require('views/appeal/edit/pages/monitoring/shared');

	var monitoringInfoGridTmpl = require('text!templates/appeal/edit/pages/monitoring/monitoring-info.tmpl');
	var monitoringInfoItemTmpl = require('text!templates/appeal/edit/pages/monitoring/monitoring-info-item.tmpl');

	var ClientSortableGrid = require('views/appeal/edit/pages/monitoring/views/ClientSortableGrid');
	var MonitoringInfos2 = require('views/appeal/edit/pages/monitoring/collections/MonitoringInfos2');


	/**
	 * Вью таблицы-виджета "Мониторинг"
	 * @type {*}
	 */
	var MonitoringInfoGrid = ClientSortableGrid.extend({
		template: monitoringInfoGridTmpl,
		itemTemplate: monitoringInfoItemTmpl,
		events: _.extend({
			"click .toggle": "toggle"
		}, ClientSortableGrid.prototype.events),
		toggle: function(event) {
			var $target = this.$(event.target);
			this.$('.toggle-icon').toggleClass('icon-chevron-down').toggleClass('icon-chevron-up');
			this.$('tbody tr').not('tbody tr:first-child').toggle();
		},

		data: function() {
			return {
				collection: this.collection.models.slice(0, 5)
			};
		},

		initialize: function(options) {
			this.collection = new MonitoringInfos2();
			ClientSortableGrid.prototype.initialize.apply(this);
		}
	});

	return MonitoringInfoGrid;

});
