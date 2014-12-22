define(["models/date"], function () {

	var IdCardType = Model.extend ({
		defaults: {
			name: ""
		}
	});

	App.Models.IdCard = Model.extend ({
		defaults: {
			series: "",
			number: null,
			issued: "",
			rangeDocumentDate: {},
			docType: {}
		},

		seriesRequired: true,

		validate: function (attrs) {
			if (!attrs) {

				var self = this;
				var errors = [];

				var conditionallyRequired = [
					self.get("docType").get("id"),
					self.get("series"),
					self.get("number"),
					self.get("rangeDocumentDate").get("start"),
					self.get("rangeDocumentDate").get("end")
				];

				if (_(conditionallyRequired).any()) {
					var startDate = this.get("rangeDocumentDate").get("start");
					var endDate = this.get("rangeDocumentDate").get("end");

					if (!this.get("docType").get("id")) {
						errors.push({property: "idCards-"+self.cid+"-docType-id", msg: "Тип удостоверения личности"});
					}
					if (!this.get("number")) {
						errors.push({property: "idCards-"+self.cid+"-number", msg: "Номер удостоверения личности"});
					}
					/*if (this.seriesRequired && !this.get("series")) {
						errors.push({property: "idCards-"+self.cid+"-series", msg: "Серия удостоверения личности"});
					}*/
					if (!startDate || isNaN(new Date(parseInt(startDate)).getTime()) || new Date(parseInt(startDate)) > new Date()) {
						errors.push({property: "idCards-"+self.cid+"-rangeDocumentDate-start", msg: "Дата выдачи удостоверения личности"});
					}
					if (endDate && (isNaN(new Date(parseInt(endDate)).getTime()) || new Date(parseInt(endDate)) < new Date())) {
						errors.push({property: "idCards-"+self.cid+"-rangeDocumentDate-end", msg: "Дата окончания удостоверения личности"});
					}
				}

				if (errors.length) return errors;
			}
		},

		relations: [
			{
				type: Backbone.HasOne,
				key: "docType",
				relatedModel: IdCardType
			},
			{
				type: Backbone.HasOne,
				key: "rangeDocumentDate",
				relatedModel: "App.Models.Date"
			}
		]
	});
});