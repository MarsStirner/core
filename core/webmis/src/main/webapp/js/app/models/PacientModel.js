define(function () {

	return Collection.extend({
		url:function(){
			return '/api/v1/dir/pacient_model';
		},
		parse: function(raw){
			return raw.data;
		}
	});
});
