/**
 * User: FKurilov
 * Date: 29.05.12
 */
define(function () {
	// Используется простая модель для быстродействия
	var Doctor = Backbone.Model.extend({
		defaults: {
			name: {
				first: "",
				last: "",
				middle: "",
				raw: ""
			},
			specs: {
				name: ""
			},
			department: {
				name: ""
			}
		}
	});

	App.Collections.Doctors = Collection.extend({
		model: Doctor,

		url: function () {
			return DATA_PATH + "dir/persons/"
		}
	});

	return App.Collections.Doctors;
});