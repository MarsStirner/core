define([], function () {
	App.Views.GridRow = View.extend({
		tagName: "tr",

		events: {
			"click": "updateUrl",
			"dblclick": "onDbClick"
		},

		onDbClick: function (event) {
			if (this.options.triggerOnly) {
				this.trigger("row:dbclick", this.model, event);
			}
		},

		updateUrl: function (event) {
			if (this.options.triggerOnly) {
				this.trigger("row:click", this.model, event);
			} else {
				var $target = $(event.currentTarget),
					href = $target.closest("tr").find("a").attr("href");

				if (href) {
					App.Router.navigate(href, {trigger: true});
				}
			}
		},

		render: function () {
			var resultObject = {
				requestData: this.collection.requestData,
				_index: this.options._index
			};

			if (this.model) {
				resultObject = $.extend(this.model.toJSON(), resultObject);
			}
			if(this.collection.extra){
				resultObject = $.extend(resultObject, this.collection.extra);
			}


			this.$el.html($(this.options.rowTemplateId).tmpl(resultObject));

			return this
		}
	});

	return App.Views.GridRow
});
