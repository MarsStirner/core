App.Models.Phone = Model.extend ({
	defaults: {
		number: "",
		typeId: "",
		typeName: "",
		comment: ""
	},
	validate: function (attrs) {
		if (!attrs) {
			var self = this;
			var errors = [];

			if (this.get("number") && !this.get("typeId")) {
				errors.push({property: "phones-"+self.cid+"-typeId", msg: "Тип контактных данных"});
			}

			// Validate email
			if (this.get("typeId") === "9" && !/^([a-zA-Z0-9_\.\-\+])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/.test(this.get("number"))) {
				errors.push({property: "phones-"+self.cid+"-number", msg: "Адрес электронной почты"});
			}

			// Validate phone
			if (this.get("typeId") && this.get("typeId") !== "9" && !/^[0-9\-\+\(\)\s]+$/.test(this.get("number"))) {
				errors.push({property: "phones-"+self.cid+"-number", msg: "Номер телефона"});
			}

			if (errors.length) {
				return errors;
			}
		}
	}
});
