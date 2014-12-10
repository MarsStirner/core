define(["models/patient"], function () {

	App.Collections.Patients = Collection.extend ({
		url: DATA_PATH + "patients/?filter[withRelations]=true",
		model: App.Models.Patient
	});

});
