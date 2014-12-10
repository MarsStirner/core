/***
 * Нотификация
 * http://needim.github.com/noty/
 * pubsub.trigger('noty', {text:'текст сообщения',type:'alert'});
 * pubsub.trigger('noty', {text:'текст сообщения',type:'error'});
 *
 * pubsub.trigger('noty_save', {text:'текст сообщения',type:'error'}); - сохранить сообщение в куки и показать после перезагрузки страницы
 *
 * pubsub.trigger('noty_clear'); - очистить все сообщения
 *
 */
define([], function () {

	var FlashMessage = Backbone.View.extend({
		initialize: function() {
			pubsub.bind('noty', this.showMessage);
			pubsub.bind('noty_save', this.saveMessage);
			pubsub.bind('noty_clear', this.clear);
			this.render();

			var previousMessage = this.readMessage();
			if(previousMessage){
				this.showMessage(previousMessage);
				Core.Cookies.set('noty',null, new Date(0));
			}

		},

		render: function(){
			var noty = $('<div id="noty" >').css({
				'position':'fixed',
				'width':300,
				'z-index':10500,
				'right': 0,
				'top': "3.8em"
			});

			$('body').append(noty);

		},
		/***
		 *
		 * @param {object} options
		 * @param {string} options.text - текст
		 * @param {string} options.type - alert,success,error,warning,information,confirmation
		 */
		showMessage: function(options){

		 var default_options = {
				text: '',
				type: 'success',
				dismissQueue: true,
				force: true,
				theme: 'defaultTheme',
				modal: false,
				callback: {
					afterShow: function() {
						var noty = this;
						setTimeout(function() {
							noty.close();
						}, 15000);
					}
				},
				closeWith: ['click'] // ['click', 'button', 'hover']
			};

			var noty_optyons = _.extend(default_options, options );

			$('#noty').noty(noty_optyons);

		},
		cookie_name: 'noty',
		saveMessage: function(options){
			var message = JSON.stringify(options)
			Core.Cookies.set('noty',message);

		},
		readMessage: function(){

			var cookie = Core.Cookies.get('noty');
			if(cookie){
				return JSON.parse(cookie);
			}else{
				return false;
			}
		},
		clear: function(){
			$('#noty').html('');
		}

	});


	return FlashMessage;

});
