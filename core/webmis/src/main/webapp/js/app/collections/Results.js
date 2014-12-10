define(function () {

	return Collection.extend({
		url:function(){
			return '/api/v1/dir/result';
		},
		parse: function(raw){
			return raw.data;
		}
	});
});
