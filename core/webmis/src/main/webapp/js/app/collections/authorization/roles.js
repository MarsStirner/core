define(["models/doctor"], function ()
{
	var Role = Model.extend({
		initialize: function (){

		}
	});


	App.Collections.Roles = Collection.extend({
		model: Role,

		url: function () {
			return DATA_PATH + "roles/"
		},

		parse: function(data){
			this.doctor = new App.Models.Doctor(data.doctor);

			return data.roles
		},

		fetch: function ( options )
		{
			options = options || {};

			var data = JSON.stringify($.extend ({
				login: this.login,
				password: this.password,
				roleId: 0
			}, options.data ));

			options.type = "post";
			options.dataType = "jsonp";
			options.contentType = 'application/json';


			/*var errorHandler = $.extend ( function (model, xhr)
			{
				// TODO Отрабатывать ошибки
				if ( xhr.responseText.length ) {
					showError(xhr.responseText);
				}
			}, options.error );*/


			return Backbone.Collection.prototype.fetch.call ( this, $.extend ( options, {
				data: data//,
				//error: errorHandler
			}));
		}
	});
	return {};
} );