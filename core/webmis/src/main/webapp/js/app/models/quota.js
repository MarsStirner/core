/**
 * User: FKurilov
 * Date: 13.12.12
 */
define(["models/mkb", "models/department"], function () {
	App.Models.QuotaType = Model.extend({
		defaults: {
			"name": "",
			"code": ""
		}
	});

	App.Models.QuotaStatus = Model.extend({
		defaults: {
			"name": ""
		}
	});

	App.Models.QuotaStage = Model.extend({
		defaults: {
			"name": ""
		}
	});

	App.Models.QuotaRequest = Model.extend({
		defaults: {
			"name": ""
		}
	});

	App.Collections.QuotaTypes = Collection.extend({
		model: App.Models.QuotaType,

		url: function () {
			return DATA_PATH + "dir/quotaTypes";
		}
	});

	App.Models.Quota = Model.extend({
		idAttribute: "id",

		defaults: {
			"version": "",
			"appealNumber": "",
			"talonNumber": "",
			"stage": {},
			"request": {},
			"mkb": {},
			"quotaType": {},
			"department": {},
			"status": {}
		},

		relations: [
			{
				type: Backbone.HasOne,
				key: "mkb",
				relatedModel: App.Models.Mkb
			},
			{
				type: Backbone.HasOne,
				key: "quotaType",
				relatedModel: App.Models.QuotaType
			},
			{
				type: Backbone.HasOne,
				key: "department",
				relatedModel: App.Models.Department
			},
			{
				type: Backbone.HasOne,
				key: "status",
				relatedModel: App.Models.QuotaStatus
			},
			{
				type: Backbone.HasOne,
				key: "stage",
				relatedModel: App.Models.QuotaStage
			},
			{
				type: Backbone.HasOne,
				key: "request",
				relatedModel: App.Models.QuotaRequest
			}
		],

		urlRoot: function () {
			return DATA_PATH + "appeals/" + this.get("appealNumber") + "/quotes";
		}

		/*url: function () {
			return DATA_PATH + "appeals/" + this.get("appealNumber") + "/quotes";
		}*/
	});

	return App.Models.Quota;
});