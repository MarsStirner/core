/**
 * Author: FKurilov
 * Date: 23.05.12
 */
define(["models/examination"], function ()
{
	App.Collections.Examinations = Collection.extend({
		model: App.Models.Examination,
		url: function () {
			return DATA_PATH + "appeals/" + this.appealId + "/documents/?filter[mnem]=EXAM"
		}
	});

	return App.Collections.Examinations;
} );