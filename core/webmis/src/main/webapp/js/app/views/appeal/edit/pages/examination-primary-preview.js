define([
	"text!templates/appeal/edit/pages/examination-primary-preview.tmpl",
	"views/appeal/edit/pages/examination-primary"
], function (tmpl) {

	App.Views.ExaminationPrimaryPreview = View.extend({
		className: "ContentHolder",

		events: {
			"click .AllPage": "onAllPageClick",
			"click .EditExam": "onEditExamClick"
		},

		canPrint: true,

		attributesOrder: {
			139: [70,82,84,51,57,77,58,74,75,97,78,91,85,86,87,88,89,90,59,60,61,73,62,52,66,71,65,79,63,67,72,53,54,80,81,68,69,56,76,103,55,64,93,94,95,96],
			2456: [128,129,130,131,132,133,134,135,136,137,138,139,140,141,142,143,144,145,146,147,148,149,150,151,152,153,154,155,156]
		},

		printOptions: function () {
			var self = this;

			return {
				label: "Печать",
				scope: self,
				handler: self.printExam
			};
		},
		
		initialize: function (options) {
			var self = this;

			/*this.hospitalizationPointTypes = new App.Models.FlatDirectory().set("id", 19);
			this.hospitalizationPointTypes.on("change", function () {
				var attrs = self.model.getFlatAttrs();
				var directoryValue = _(this.hospitalizationPointTypes.toBeautyJSON()).find(function (type) {
					return type.id == attrs['96'];
				});
				if (directoryValue) {
					this.$("#pointType").text(directoryValue['49']);
				}
			}, this);
			this.hospitalizationPointTypes.fetch();*/
		},

		onEditExamClick: function (event) {
			this.trigger("change:viewState", {type: "examination-primary", options: {model: this.model}});
		},

		onAllPageClick: function (event) {
			event.preventDefault();
			this.trigger("change:viewState", {type: "examinations", options: {}});
		},

		printExam: function () {
			var self = this;
			var exam = this.model;
			var examJSON = exam.toJSON();
			var summaryAttrs   = examJSON[0]["group"][0]["attribute"];
			var examName       = summaryAttrs[1]["properties"][0]["value"];
			var examEndDate    = summaryAttrs[3]["properties"][0]["value"];
			var examDoctorName = [
				summaryAttrs[4]["properties"][0]["value"],
				summaryAttrs[5]["properties"][0]["value"],
				summaryAttrs[6]["properties"][0]["value"]
			].join(" ");
			var examDoctorSpecs = summaryAttrs[7]["properties"][0]["value"];
			var typeId = examJSON[0].typeId;

			var data = {
				patientId: this.options.appeal.get("patient").get("id"),
				patientName: this.options.appeal.get("patient").get("name").toJSON(),
				appealId: this.options.appeal.get("id"),
				appealNumber: this.options.appeal.get("number"),
				examId: exam.get("id"),
				examName: examName,
				examEndDate: examEndDate,
				examDoctorName: examDoctorName,
				examDoctorSpecs: examDoctorSpecs,
				attributes: exam.getFilledAttrs().sort(function (a, b) {
					var ai = self.attributesOrder[typeId].indexOf(a.id);
					var bi = self.attributesOrder[typeId].indexOf(b.id);

					return ai > bi ? 1 : (ai < bi ? -1 : 0);
				})
			};

			var pointType = _(data.attributes).where({id: 96});

			if (pointType.length) {
				var pointTypeId = pointType[0].value;
				var directoryValue = _(this.hospitalizationPointTypes.toBeautyJSON()).find(function (type) {
					return type.id == pointTypeId;
				});
				if (directoryValue) {
					_(data.attributes).where({id: 96})[0].value = directoryValue['49'];
					//pointType.value = directoryValue['49'];
				}
			}

			console.log(data);

			new App.Views.Print({
				data: data,
				template: "examination"
			});
		},
		
		render: function () {
			var self = this;

			this.hospitalizationPointTypes = new App.Models.FlatDirectory().set("id", 19);
			this.hospitalizationPointTypes.on("change", function () {
				var examJSON = self.model.toJSON();
				var summaryAttrs = examJSON[0]["group"][0]["attribute"];

				var examName       = summaryAttrs[1]["properties"][0]["value"];
				var examEndDate    = summaryAttrs[3]["properties"][0]["value"];
				var examDoctorName = [
					summaryAttrs[4]["properties"][0]["value"],
					summaryAttrs[5]["properties"][0]["value"],
					summaryAttrs[6]["properties"][0]["value"]
				].join(" ");
				var examDoctorSpecs = summaryAttrs[7]["properties"][0]["value"];

				var typeId = examJSON[0].typeId;

				var tmplData = {
					examAttrs:       self.model.getFilledAttrs().sort(function (a, b) {
						var ai = self.attributesOrder[typeId].indexOf(a.id);
						var bi = self.attributesOrder[typeId].indexOf(b.id);

						return ai > bi ? 1 : (ai < bi ? -1 : 0);
					}),
					examName:        examName,
					examEndDate:     examEndDate,
					examDoctorName:  examDoctorName,
					examDoctorSpecs: examDoctorSpecs,
					appealId:        self.model.appealId,
					isClosed:        self.options.appeal.isClosed()
				};

				var pointType = _(tmplData.examAttrs).where({id: 96});

				if (pointType.length) {
					var pointTypeId = pointType[0].value;
					var directoryValue = _(this.hospitalizationPointTypes.toBeautyJSON()).find(function (type) {
						return type.id == pointTypeId;
					});
					if (directoryValue) {
						_(tmplData.examAttrs).where({id: 96})[0].value = directoryValue['49'];
					}
				}

				this.$el.html(_.template(tmpl, tmplData));

				this.$(".EditExam").button({icons: {primary: "icon-edit"}});

				self.trigger("change:printState");

			}, this);

			this.hospitalizationPointTypes.fetch();

			return this;
		}
	});

	return App.Views.ExaminationPrimaryPreview;
});
