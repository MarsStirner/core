define(["models/payment"], function () {
	var Payments = App.Collections.Payments = Collection.extend({
		model: App.Models.Payment,

		dictionaries: {
			insuranceCompanies: [],
			tfomses: [],
			policyTypes: []
		},

		selectedTfoms: undefined,

		getDms: function () {
			return this.filter(function (model) {
				return (parseInt(model.get("policyType").get("id")) == 3);
			});
		},

		getOms: function () {
			return this.filter(function (model) {
				return (parseInt(model.get("policyType").get("id")) != 3);
			});
		},

		setDictionaries: function (dicts) {
			this.dictionaries = dicts;

			this.trigger("dictionaries-change", {dictionaries: this.getDictionaries()});

			return this;
		},

		getDictionaries: function () {
			return {
				tfomses: this.dictionaries.tfomses,
				policyTypes: this.dictionaries.policyTypes,
				insuranceCompanies: this.dictionaries.insuranceCompanies.filter(function (ic) {
					return parseInt(ic.headId) === this.getSelectedTfoms();
				}, this)
			};
		},

		setSelectedTfoms: function (selectedTfoms) {
			this.selectedTfoms = selectedTfoms.tfomsId;

			this.trigger("selected-tfoms-change", {selectedTfoms: this.selectedTfoms, unsetSmo: selectedTfoms.unsetSmo});

			return this;
		},

		getSelectedTfoms: function () {
			return parseInt(this.selectedTfoms);
		}
	});

	return Payments;
});