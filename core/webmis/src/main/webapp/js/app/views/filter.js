define([],function(){

App.Views.Filter = View.extend({
	events:{
		"keyup :input":"onInputKeyup",
		"change :input":"onInputChange",
		"submit form":"submit"
	},

	submit:function () {
		return false;
	},

	updateUrl:function () {
		App.Router.updateUrl(this.options.path, this.collection.getParams());
	},

	onInputKeyup: function (event) {
		var onEnterOnly = $(event.currentTarget).hasClass("NewLineAllowed");

		if ((onEnterOnly && event.keyCode == 13) || !onEnterOnly) {
			this.refresh($(event.currentTarget));
		}
	},

	onInputChange: function (event) {
		this.lastChangedInput = $(event.currentTarget);

		if (!$(event.currentTarget).hasClass("NewLineAllowed"))
			this.refresh($(event.currentTarget));
	},

	refresh:function ($target) {
		$target = $target || this.lastChangedInput;

		if (_.isUndefined($target.data("old-value")) || ($target.data("old-value") != $target.val())) {
			$target.data("old-value", $target.val());

			var filter = Core.Forms.serializeToObject($target.closest("form"));
			console.log('filter',filter);

			this.collection.setParams({
				filter:filter,
				page: 1
			});

			if (_.size(filter)) {
				this.collection.fetch();
			} else {
				this.collection.requestData = {};
				this.collection.reset();
			}
		}
	},

	initialize:function () {
		var params = Core.Url.extractUrlParameters();

		this.collection.on("reset", this.updateUrl, this);

		if (!_.isEmpty(params)) {
			this.collection.setParams(params);
			this.collection.fetch();
		}

		if (this.options.template) {
			this.on("template:loaded", this.ready, this);
			this.loadTemplate(this.options.template);
		} else {
			this.ready();
		}
	},

	ready:function () {
		var resultObject = {
			requestData:this.collection.getParams() || {}
		};

		this.$el.html($(this.options.templateId).tmpl(resultObject));
	},

	render:function () {
		UIInitialize(this.el);
		return this;
	}
});

	return App.Views.Filter;

});
