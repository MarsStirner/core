/**
 * User: FKurilov
 * Date: 28.05.12
 */
define(function (require) {

	var HospitalBed = Model.extend({
		defaults: {
			"clientId": "",
			"bedId": null,
			"moveDatetime": "",
			"movedFromUnitId": null,
			"patronage": "Нет"
		},


		url: function (isSave) {

			return DATA_PATH + "appeals/" + this.appealId + "/hospitalbed/";
		},

		sync: function (method, model, options) {
			console.log('sync hospitalbed',method);
			options.dataType = "jsonp";
			options.contentType = 'application/json';
			options.url = model.url(method === "create");

			if (method == "create" || method == "update") {
				options.data = JSON.stringify({
					//requestData: {},
					data: model.toJSON()
				});
			}

			return Backbone.sync(method, model, options);
		},

		toJSON: function () {
			return {
				"bedRegistration": Model.prototype.toJSON.call(this)
			}
		},

		parse: function (raw) {
			return raw.data.registrationForm ? raw.data.registrationForm : raw.data;
		}
	});

	return HospitalBed;
});
