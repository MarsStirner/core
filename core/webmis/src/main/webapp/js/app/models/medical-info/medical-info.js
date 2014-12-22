define(["collections/medical-info/allergies", "collections/medical-info/drug-intolerances"], function () {
	var Blood = Model.extend ({
		defaults: {
			group: "",
			checkingDate: ""
		},

		validate: function (attrs) {
			if (!attrs) {
				var errors = [];

				if (this.get("id") && this.get("id") !== -1 && !this.get("checkingDate")) {
					errors.push({property: "medicalInfo-blood-checkingDate", msg: "Дата установления группы крови"});
				}
				if (this.get("checkingDate") && !this.get("id")) {
					errors.push({property: "medicalInfo-blood-id", msg: "Группа крови"});
				}

				if (errors.length) return errors;
			}
		}
	});

	App.Models.MedicalInfo = Model.extend({
		defaults: {
			blood: {},
			allergies: [{}],
			drugIntolerances: [{}]
		},

		relations: [
			{
				type: Backbone.HasOne,
				key: "blood",
				relatedModel: Blood
			},
			{
				type: Backbone.HasMany,
				key: "allergies",
				relatedModel: "App.Models.Allergy",
				collectionType: "App.Collections.Allergies"
			},
			{
				type: Backbone.HasMany,
				key: "drugIntolerances",
				relatedModel: "App.Models.DrugIntolerance",
				collectionType: "App.Collections.DrugIntolerances"
			}
		],

		validate: function (attrs) {
			if (!attrs) {
				var errors = [];

				var bloodErrors = this.get("blood").validate() || [];

				var allergiesErrors = [];

				this.get("allergies").each(function (a) {
					allergiesErrors = _(allergiesErrors).union(a.validate() || []);
				});

				var drugIntolerancesErrors = [];

				this.get("drugIntolerances").each(function (d) {
					drugIntolerancesErrors = _(drugIntolerancesErrors).union(d.validate() || []);
				});

				errors = _(errors).union(bloodErrors, allergiesErrors, drugIntolerancesErrors);

				if (errors.length) return errors;
			}
		}
	});
});
