define(["models/Name", "models/department"], function (Name){
	var Specs = Model.extend({
		defaults: {
			name: {}
		}
	});

	App.Models.Doctor = Model.extend({
		defaults: {
			//id: -1,
			name: {},
			specs: {},
			department: {}
		},

		relations: [
			{
				type: Backbone.HasOne,
				key: "name",
				relatedModel: Name
			},
			{
				type: Backbone.HasOne,
				key: "specs",
				relatedModel: Specs
			},
			{
				type: Backbone.HasOne,
				key: "department",
				relatedModel: "App.Models.Department"
			}
		],

		parse: function ( data ) {
			var doc = new App.Models.Doctor();
			data = data.data ? data.data : data;

			return Core.Objects.mergeAll( doc.toJSON(), data )
		}
	});

	return App.Models.Doctor;
});
