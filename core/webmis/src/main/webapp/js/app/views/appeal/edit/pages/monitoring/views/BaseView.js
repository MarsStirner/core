define(function(require) {

	/**
	 * Базовый класс для простых вьюшек
	 * @type {*}
	 */
	var BaseView = Backbone.View.extend({
		template: "",

		data: function() {
			return {};
		},

		initialize: function() {
			this._template = _.template(this.template);
		},

		render: function() {
			this.$el.empty().append(this._template(this.data()));
			return this;
		}
	});

	return BaseView;
});
