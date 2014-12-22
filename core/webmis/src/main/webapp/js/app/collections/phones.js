define(["models/phone"], function () {

	App.Collections.Phones = Collection.extend ({
		model: App.Models.Phone,

		getMobile: function () {
			return this.filter(function(model){
				return ( model.get("typeId") == 3 )
			})
		},
		getWork: function () {
			return this.filter(function(model){
				return ( model.get("typeId") == 2 )
			})
		},
		getHome: function () {
			return this.filter(function(model){
				return ( model.get("typeId") == 1 )
			})
		}
	});

});
