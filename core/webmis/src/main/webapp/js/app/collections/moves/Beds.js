/**
 * User: VKondratev
 * Date: 28.11.12
 */
define(function (require) {

	var Bed = require('models/moves/Bed');

	var Beds = Collection.extend({
		model: Bed,
		initialize: function (models, options) {
			//this.departmentId = options.departmentId;
		},
		setDepartmentId: function(departmentId){
			this.departmentId = departmentId;
			return this;
		},
		url: function () {
			if (this.departmentId) {
				return DATA_PATH + "hospitalbed/vacant?filter[departmentId]=" + this.departmentId;
			} else {
				throw new Error("No departmentId for bed collection");
			}
		}
	});

	return Beds;
});
