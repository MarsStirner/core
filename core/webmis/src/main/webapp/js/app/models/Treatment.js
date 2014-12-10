define(function () {

	return Collection.extend({
		url:function(){
			return '/api/v1/dir/treatment';
		},
		parse: function(raw){
			return raw.data;
		}
	});
});
