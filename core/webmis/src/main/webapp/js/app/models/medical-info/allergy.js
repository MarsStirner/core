App.Models.Allergy = Model.extend ({
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
					errors.push({property: "medicalInfo-allergies-"+self.cid+"-substance", msg: "Наименование вещества аллергии"});
				}
				if (!this.get("degree")) {
					errors.push({property: "medicalInfo-allergies-"+self.cid+"-degree", msg: "Степень аллергии"});
				}
				if (!this.get("checkingDate")) {
					errors.push({property: "medicalInfo-allergies-"+self.cid+"-checkingDate", msg: "Дата установления аллергии"});
				}
			}*/

			if ((this.get("degree") || this.get("checkingDate")) && !this.get("substance")) {
				errors.push({property: "medicalInfo-allergies-"+self.cid+"-substance", msg: "Наименование вещества аллергии"});
			}

			if (errors.length) return errors;
		}
	}
});