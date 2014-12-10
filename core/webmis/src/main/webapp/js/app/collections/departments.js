/**
 * User: FKurilov
 * Date: 29.05.12
 */
define(["models/department"], function ()
{
	App.Collections.Departments = Collection.extend({
		model: App.Models.Department,
		url: function () {
			return DATA_PATH + "dir/departments/"
		}
	});

	return App.Collections.Departments;
});
