define(["models/date"], function () {

	var DisabilityType = Model.extend ({
		defaults: {
			name: ""
		}
	});

	var Document = Model.extend ({
		defaults: {
			series: "",
			number: "",
			date: "",
			comment: ""
		}
	});

	var BenefitsCategory = Model.extend ({
		defaults: {
			category: ""
		}
	});


	App.Models.Disability = Model.extend ({
		defaults: {
			disabilityType: {},
			document: {},
			benefitsCategory: {},
			rangeDisabilityDate: {}
		},

		relations: [
			{
				type: Backbone.HasOne,
				key: "disabilityType",
				relatedModel: DisabilityType
			},
			{
				type: Backbone.HasOne,
				key: "document",
				relatedModel: Document
			},
			{
				type: Backbone.HasOne,
				key: "rangeDisabilityDate",
				relatedModel: "App.Models.Date"
			},
			{
				type: Backbone.HasOne,
				key: "benefitsCategory",
				relatedModel: BenefitsCategory
			}
		],

		validate: function (attrs) {
			if (!attrs) {
				var self = this;
				var errors = [];

				var conditionallyRequired = [
					self.get("disabilityType").get("id"),
					self.get("document").get("id"),
					self.get("rangeDisabilityDate").get("start"),
					self.get("rangeDisabilityDate").get("end"),
					self.get("document").get("series"),
					self.get("document").get("number"),
					self.get("document").get("date")
				];

				if (_(conditionallyRequired).any()) {
					var startDate = self.get("rangeDisabilityDate").get("start");
					var endDate = self.get("rangeDisabilityDate").get("end");

					var docDate = self.get("document").get("date");

					if (!this.get("disabilityType").get("id")) {
						errors.push({property: "disabilities-"+self.cid+"-disabilityType-id", msg: "Тип инвалидности"});
					}
					if ((self.get("document").get("series") || self.get("document").get("number") || self.get("document").get("date")) && !this.get("document").get("id")) {
						errors.push({property: "disabilities-"+self.cid+"-document-id", msg: "Документ подтверждающий инвалидность"});
					}
					if (!startDate || isNaN(new Date(parseInt(startDate)).getTime()) || new Date(parseInt(startDate)) > new Date()) {
						errors.push({property: "disabilities-"+self.cid+"-rangeDisabilityDate-start", msg: "Дата начала инвалидности"});
					}
					if (!endDate || isNaN(new Date(parseInt(endDate)).getTime()) || new Date(parseInt(endDate)) < new Date()) {
						errors.push({property: "disabilities-"+self.cid+"-rangeDisabilityDate-end", msg: "Дата окончания инвалидности"});
					}
					if (!docDate || isNaN(new Date(parseInt(docDate)).getTime()) || new Date(parseInt(docDate)) > new Date()) {
						errors.push({property: "disabilities-"+self.cid+"-document-date", msg: "Дата выдачи документа подтверждающего инвалидность"});
					}
				}

				if (errors.length) return errors;
			}
		}
	});
});