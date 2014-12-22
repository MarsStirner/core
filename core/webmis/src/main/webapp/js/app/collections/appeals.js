define(["models/appeal"], function ()
{
	App.Collections.Appeals = Collection.extend({
		model: App.Models.Appeal,
		url: function () {
			return DATA_PATH + "appeals/"
		}
	});
} );