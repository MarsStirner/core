App.Models.DrugIntolerance = Model.extend ({
	defaults: {
		substance: "",
		degree: "",
		checkingDate: "",
		comment: ""
	},

	validate: function (attrs) {
		if (!attrs) {
			var self = this;
			var errors = [];

			/*var conditionallyRequired = [
				self.get("substance"),
				self.get("degree"),
				self.get("checkingDate")
			];

			if (_(conditionallyRequired).any()) {
				if (!this.get("substance")) {
					errors.push({property: "medicalInfo-drugIntolerances-"+self.cid+"-substance", msg: "Наименование непереносимого вещества"});
				}
				if (!this.get("degree")) {
					errors.push({property: "medicalInfo-drugIntolerances-"+self.cid+"-degree", msg: "Степень непереносимости"});
				}
				if (!this.get("checkingDate")) {
					errors.push({property: "medicalInfo-drugIntolerances-"+self.cid+"-checkingDate", msg: "Дата установления непереносимого вещества"});
				}
			}*/

			if ((this.get("degree") || this.get("checkingDate")) && !this.get("substance")) {
				errors.push({property: "medicalInfo-drugIntolerances-"+self.cid+"-substance", msg: "Наименование непереносимого вещества"});
			}

			if (errors.length) return errors;
		}
	}
});
