/**
 * User: FKurilov
 * Date: 28.05.12
 */
define(function () {
	App.Models.Department = Model.extend({
		defaults: {
			name: ""
		}
	});

	return App.Models.Department;
});
