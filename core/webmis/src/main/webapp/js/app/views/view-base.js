define(function () {
	return Backbone.View.extend({
		template: _.template(""),

		initialize: function () {
			//console.log('initialize ' + this + ' ' + this.cid, arguments);
		},

		toString: function () {
			return 'Base';
		},

		data: function () {
			return {};
		},

		assign: function (subViews) {
			this.subViews = _.extend(this.subViews || {}, subViews);
			Backbone.View.prototype.assign.call(this, subViews);
		},

		render: function (subViews) {
			this.$el.html(this.template(this.data()));

			if (subViews) {
				this.subViews = {};
				this.assign(subViews);
			}

			this.$("button,[data-display-as=button]").each(function () {
				var $this = $(this);
				var icons = {};
				if ($this.data("icon-primary")) {
					icons.primary = $this.data("icon-primary");
				}
				if ($this.data("icon-secondary")) {
					icons.secondary = $this.data("icon-secondary");
				}
				$this.button({
					icons: icons,
					text: !$this.data("notext")
				});
			});

			return this;
		},

		tearDown: function () {
			this.tearDownSubviews();
			this.stopListening();
			this.undelegateEvents();
			this.remove();
		},

		tearDownSubviews: function () {
			if (this.subViews) {
				_.each(this.subViews, function (subView) {
					subView.tearDown();
				});
			}
		}
	});
});
