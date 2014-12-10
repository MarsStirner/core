define(function(require){

	return Collection.extend({
		url: function () {
			return DATA_PATH + "dir?dictName=operationTypes";
		},
	});

})
