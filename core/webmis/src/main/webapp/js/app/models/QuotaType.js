define(function () {

	return Collection.extend({
		url:function(){
			return '/api/v1/dir/quotaType';
		},
		parse: function(raw){
			return raw.data;//{items: raw.data}
		}
	});
});
