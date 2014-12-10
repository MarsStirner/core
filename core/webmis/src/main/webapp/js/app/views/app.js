/**
 * User: FKurilov
 * Date: 22.06.12
 */
define(["text!templates/app.tmpl", "views/header-new"], function(tmpl, Header) {
	App.Views.App = Backbone.View.extend({
		el: $("#wrapper"),
		events: {
			"keydown input:not(.NewLineAllowed), select, textarea:not(.NewLineAllowed)": "keyHandler"
		},

		initialize: function() {
			this.renderView = this.options.renderView;

			this.header = new Header({
				structure: {
					doctor: {
						name: {
							first: Core.Cookies.get("doctorFirstName"),
							last: Core.Cookies.get("doctorLastName")
						}
					},
					roles: [{
						id: 29,
						role: ROLES.NURSE_RECEPTIONIST,
						title: "Сестра приёмного отделения"
					}, {
						id: 30,
						role: ROLES.DOCTOR_RECEPTIONIST,
						title: "Врач приёмного отделения"
					}, {
						id: 24,
						role: ROLES.DOCTOR_DEPARTMENT,
						title: "Врач отделения"
					}, {
						id: 25,
						role: ROLES.NURSE_DEPARTMENT,
						title: "Сестра отделения"
					}
					, {
						id: 26,
						role: ROLES.CHIEF,
						title: "Главный врач"
					}
					]
				}
			});

			//this.logoutTimer();
		},

		logoutTimer: function() {
			var idleTimer = null;
			var intervalID = null;
			var idleWait = 900000;
			var timer;
			var logoutDialog = $('<div/>', {
			    html: "Срок ожидания для Вашего сеанса истечёт через <span id='timer'></span> секунд."
			}).dialog({
				modal: true,
				resizable: false,
				autoOpen: false,
				width: 500,
				title: "Сообщение системы",
				buttons: {
					"Ok": function() {
						$.ajax({
							url: "/data/build",
							success: function(data) {
						    	logoutDialog.dialog("close");
								isDialog = false;
								clearInterval(intervalID);	       
						  	},
						  	error: function() { 
							    timer = 150;
						  	}
						});
						return false;
					}
				}
			});
			var isDialog = false;
			$('*').bind('mousemove keydown scroll', function () {
				if(!isDialog) {
	            	clearTimeout(idleTimer);
		            idleTimer = setTimeout(function () {
		            	isDialog = true;
		                logoutDialog.dialog("open");
		                timer = 150;
						$('#timer').text(timer);
		                intervalID = setInterval(function(){
		                	$('#timer').text(--timer);
		                	if (timer < 1) {
		                		window.location.href = "/auth/";
		                	}
		                }, 1000);
		            }, idleWait);
		        }
        	});
        	$("body").trigger("mousemove");
		},

		keyHandler: function(event) {

			event.keyCode = event.keyCode || event.charCode;

			if (event.keyCode == 13) { // Enter
				event.stopPropagation();
				event.preventDefault();

				var $foundedArray = $();
				var $current = $(event.currentTarget);

				var form = event.currentTarget.form;

				if (form) {
					var currentTabIndex = parseFloat($current.attr("tabindex"));

					var $nextInput;
					var next = false;
					$.each(form, function() {
						var $input = $(this);

						if ($input.not(":disabled").length) {
							if (currentTabIndex && (parseFloat($input.attr("tabindex")) > currentTabIndex)) {
								$nextInput = $input;

								return false
							}

							if (next && !$nextInput) {
								$nextInput = $input;
							}
						}

						// Нам нужны только следующие за этим инпутом.
						if ($input.get(0) == $current.get(0)) {
							next = true;
						}

					});

					if ($nextInput) {
						$nextInput.focus();
					}
				}
			}

		},

		render: function() {
			this.$el.html($.tmpl(tmpl));
			this.$("header").html(this.header.render().el);
			this.renderView.render();
			this.$("#main").html(this.renderView.el);

			return this;
		},

		renderMainOnly: function() {
			this.$("#main").html(this.renderView.render().el);
		}
	});

	return App.Views.App;
});
