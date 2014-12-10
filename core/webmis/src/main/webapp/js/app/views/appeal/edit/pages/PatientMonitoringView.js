define(function(require) {
	var shared = require('views/appeal/edit/pages/monitoring/shared');
	var template = require('text!templates/appeal/edit/pages/patient-monitoring.html');

	var MonitoringInfoGrid = require('views/appeal/edit/pages/monitoring/views/MonitoringInfoGrid');
	var MonitoringInfoGrid2 = require('views/appeal/edit/pages/monitoring/views/MonitoringInfoGrid2');
	var MonitoringInfoGrid3 = require('views/appeal/edit/pages/monitoring/views/MonitoringInfoGrid3');
	var ExpressAnalyses = require('views/appeal/edit/pages/monitoring/views/ExpressAnalysesView');
	var ExpressAnalyses2 = require('views/appeal/edit/pages/monitoring/views/ExpressAnalysesView2');

	return Backbone.View.extend({
		ui: {},
		events: {},
		className: "monitoring-layout",
		initialize: function() {
			shared.models.appeal = this.model = this.options.appeal;
			//shared.appealJSON = shared.models.appeal.toJSON();

		},
		render: function() {

			this.$el.html(_.template(template,this.options.appeal.toJSON()));

			this.assign({
				// ".monitoring-info": new MonitoringInfoGrid(),
				".monitoring-info2": new MonitoringInfoGrid2(),
				//".monitoring-info3": new MonitoringInfoGrid3(),
				//".express-analyses": new ExpressAnalyses(),
				".express-analyses2": new ExpressAnalyses2()
			});

			return this;
		}

	});

});
