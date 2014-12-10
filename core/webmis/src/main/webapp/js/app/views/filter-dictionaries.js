define(["views/filter"], function () {
	App.Views.FilterDictionaries = App.Views.Filter.extend({
		preselectedValue: undefined,

		ready: function () {
			App.Views.Filter.prototype.ready.call(this);

			if (this.options.dictionaries) {
				_.each(this.options.dictionaries, this.loadDictionary, this);
			}
		},

		loadDictionary: function (dictionary) {
			dictionary.collection.on("reset", this.renderDictionary, dictionary);
			dictionary.collection.fetch();
		},

		renderDictionary: function () {
			var dictionary = this;
			var options = {allowClear:true};
			var element = $("#" + dictionary.elementId);

			dictionary.collection.each(function (m) {
				$("<option/>")
					.attr("value", dictionary.getValue(m))
					.text(dictionary.getText(m))
					.appendTo(element);
			});

			if(dictionary.matcher){
				options.matcher = dictionary.matcher;
			}

			element.select2(options)

			//UIInitialize(element.parent());


			if (this.preselectedValue) {
				element.select2("val", this.preselectedValue);//.change();
			}
		}
	});
});
