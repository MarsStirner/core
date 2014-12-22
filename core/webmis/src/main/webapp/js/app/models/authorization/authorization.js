define( function ()
{
	App.Models.Authorization = Model.extend({
		urlRoot: function(){
			return DATA_PATH + "auth/"
		},
		parse: function(data){
			return data
		},
		sync: function(method, model, options) {
			options.type = "post";
			options.dataType = "jsonp";
			options.contentType = 'application/json';

			options.data = JSON.stringify($.extend ({
				login: this.login,
				password: this.password,
				roleId: this.roleId
			}, options.data ));


			return Backbone.sync(method, model, options);
		}
	});

	return {};
} );