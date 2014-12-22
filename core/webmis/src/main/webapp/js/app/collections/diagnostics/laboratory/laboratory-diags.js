/**
 * User: FKurilov
 * Date: 08.06.12
 */
define(["models/diagnostics/laboratory/laboratory-diag"], function (LabDiagnostic) {
	App.Collections.LaboratoryDiags = Collection.extend({
		model: App.Models.LaboratoryDiag,

		url: function () {
			return DATA_PATH + "appeals/" + this.appealId + "/diagnostics/laboratory/";
		}

	});
});
