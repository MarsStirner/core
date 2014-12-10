define(function(require){

	return Collection.extend({
		url: function () {
			return '/api/v1/dir/administration';
		},
	});

})
