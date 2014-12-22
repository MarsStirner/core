define(["text!templates/reports/beds.html"], function(tmpl){

	return View.extend({
		template: tmpl,
		cleanUp: function() {
		},
		render: function () {
			this.$el.html($.tmpl(this.template));
			return this;
		}

	});
});
