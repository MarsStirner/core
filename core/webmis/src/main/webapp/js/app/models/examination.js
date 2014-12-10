/**
 * Author: FKurilov
 * Date: 23.05.12
 */
define([
	"models/date",
	"models/doctor",
	"collections/relations",
	"collections/diagnoses"
	], function () {

	var ExaminationName = Model.extend({
		defaults: {
			name: ""
		}
	});

	App.Models.Examination = Model.extend({
		defaults: {
			//examinationDate: 0,
			//examinationName: {},
			assessmentDate: 0,
			assessmentName: {},
			doctor: {}
		},

		relations: [
			{
				type: Backbone.HasOne,
				key: "examinationName",
				relatedModel: ExaminationName
			},
			{
				type: Backbone.HasOne,
				key: "doctor",
				relatedModel: "App.Models.Doctor"
			}
		],

		parse: function ( data ) {
			var examination = new App.Models.Examination;
			data = data.data ? data.data : data;

			return Core.Objects.mergeAll( examination.toJSON(), data )
		}
	});

	//return App.Models.Examination;
});