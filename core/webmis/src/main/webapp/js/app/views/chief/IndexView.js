define(function(require) {
	var template = require('text!templates/chief/index.html');

	return View.extend({
		className: "ContentHolder",
		template: template,
		events: {
			'click #appeals':'openAppeals',
			'click #statements':'openStatements'
		},
		openAppeals: function(){
			App.Router.navigate("appeals/", {trigger: true});
		},
		openStatements: function(){
			$("#main")
        .height("100%")
        .html("<iframe src='http://10.128.225.86:8888/reports/' style='height: 84%; width: 100%;margin-top: 1em;border-radius: 5px;'></iframe>");
      App.Router.navigate("reports/", {trigger: false});
      //window.location = "http://10.128.225.86:8888/reports/";
		},
		render: function() {
			this.$el.html(_.template(this.template));
			this.ui = {};
			this.ui.$statmentsButton = this.$el.find('#statements');
			this.ui.$appealsButton = this.$el.find('#appeals');

			this.ui.$statmentsButton.button({ label: 'Общая информация о Центре на '+moment().format('DD.MM.YYYY') });
			this.ui.$appealsButton.button();
			return this;
		}
	});

});
