define( function ()
{
	// MKB diagnosis entry
	App.Models.Mkb = Model.extend({
		defaults: {
			code: "",
			diagnosis: ""
		},

		getTitle: function () {
			//return this.get("code") + " " + this.get("diagnosis");
			return this.get("diagnosis");
		},

		getBreadcrumbTitle: function () {
			return this.get("code");
		}
	});
});