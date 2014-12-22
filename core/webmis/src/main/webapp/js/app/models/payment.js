define(["models/smo", "models/date"], function () {
	var PolicyType = Model.extend ({
		defaults: {
			id: null
		}
	});
	
	App.Models.Payment = Model.extend ({
		defaults: {
			policyType: {},
			series: "",
			number: null,
			comment: "",
			issued: "",
			smo: {},
			rangePolicyDate: {}
		},

		relations: [
			{
				type: Backbone.HasOne,
				key: "smo",
				relatedModel: "App.Models.Smo"
			},
			{
				type: Backbone.HasOne,
				key: "rangePolicyDate",
				relatedModel: "App.Models.Date"
			},
			{
				type: Backbone.HasOne,
				key: "policyType",
				relatedModel: PolicyType
			}
		],

		validate: function (attrs) {
			if (!attrs) {
				var self = this;
				var errors = [];

				var conditionallyRequired = [
					self.get("series"),
					self.get("number"),
					self.get("smo").get("id"),
					self.get("rangePolicyDate").get("start"),
					self.get("rangePolicyDate").get("end")
				];

				if (_(conditionallyRequired).any()) {
					var policyTypeId = self.get("policyType").get("id");
					var policyTypeName = ((policyTypeId != 3) ? "ОМС" : "ДМС");
					var startDate = self.get("rangePolicyDate").get("start");
					var endDate = self.get("rangePolicyDate").get("end");



					if (policyTypeId != 3 && !policyTypeId) {
						errors.push({property: "payments-"+self.cid+"-policyType-id", msg: "Тип полиса " + policyTypeName});
					}
					/*if (!self.get("series")) {
						errors.push({property: "payments-"+self.cid+"-series", msg: "Серия полиса " + policyTypeName});
					}*/
					if (!self.get("number")) {
						errors.push({property: "payments-"+self.cid+"-number", msg: "Номер полиса " + policyTypeName});
					}
					if (!self.get("smo").get("id")) {
						errors.push({property: "payments-"+self.cid+"-smo-id", msg: "Страховая компания " + policyTypeName});
					}
					if (!startDate || isNaN(new Date(parseInt(startDate)).getTime()) || new Date(parseInt(startDate)) > new Date()) {
						errors.push({property: "payments-"+self.cid+"-rangePolicyDate-start", msg: "Дата выдачи полиса " + policyTypeName});
					}
					if (endDate && (isNaN(new Date(parseInt(endDate)).getTime()) || new Date(parseInt(endDate)) < new Date())) {
						errors.push({property: "payments-"+self.cid+"-rangePolicyDate-end", msg: "Дата окончания полиса " + policyTypeName});
					}
				}

				if (errors.length) {
					return errors;
				}
			}
		}
	});
});