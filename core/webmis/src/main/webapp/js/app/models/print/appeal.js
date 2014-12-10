define([
	"models/appeal"
], function () {

	App.Models.PrintAppeal = App.Models.Appeal.extend({
		url: function () {
			return DATA_PATH + "appeals/" + this.get("id") + "/print/";
		},

		collectTextNodes: function (element, texts) {
			for (var child = element.firstChild; child !== null; child = child.nextSibling) {
				if (child.nodeType === 3)
					texts.push(child);
				else if (child.nodeType === 1)
					this.collectTextNodes(child, texts);
			}
		},

		getTextWithSpaces: function (element) {
			var texts = [];
			this.collectTextNodes(element, texts);
			for (var i = texts.length; i-- > 0;)
				texts[i] = texts[i].data;
			return texts.join(' ');
		},

		toJSON: function () {
			var json = App.Models.Appeal.prototype.toJSON.apply(this);
			if (json.diagnoses.length) {
				_.each(json.diagnoses, function (d) {
					d.description = Core.Strings.cleanTextMarkup(d.description);
				}, this);
			}
			console.log(json);
			return json;
		}
	});

	return App.Models.PrintAppeal;
});