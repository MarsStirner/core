define([], function () {
	var Biomaterial = Model.extend({
		default: {
			id: '',
			fullName: '',
			sex: 'male',
			birthDate: '',
			actionType: '',
			testTube: '',
			status: '',
			selected: false
		},
		idAttribute: 'id'

	});

	return Biomaterial;
});
