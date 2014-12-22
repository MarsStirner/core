define(["models/medical-info/drug-intolerance"], function () {

	App.Collections.DrugIntolerances = Collection.extend ({
		model: App.Models.DrugIntolerance
	});

});

