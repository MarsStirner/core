define(['text!templates/pages/reports.tmpl'],function (template){

	var ReportsMainView = View.extend({
		template: template,

		initialize: function () {
			var view = this;
		},

		render: function (){
			var view = this;

			view.$el.html($.tmpl(view.template));
			return view;
		}

	});

	return ReportsMainView;

});
