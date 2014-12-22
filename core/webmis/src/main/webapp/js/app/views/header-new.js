define(["text!templates/header-new.tmpl"], function(headerTmpl) {
	var Header = View.extend({
		className: "Header Clearfix",

		template: headerTmpl,

		events: {
			"click .SectionNav li": "onSectionNavClick",
			"click .LinkToProfile": "onLinkToProfileClick",
			"click .logout": "onLogoutClick",
			"click .user-role": "onUserRoleClick"
		},

		initialize: function() {
			//console.log('roles',this.options.structure.roles)
			if (!Core.Data.currentRole()) {
				Core.Data.currentRole(this.options.structure.roles[0].role);
			}
			this._currentRole = Core.Data.currentRole();
		},

		onLogoutClick: function(event) {
			event.preventDefault();

			this.logoutUser();
		},

		onSectionNavClick: function(event) {
			event.preventDefault();

			var $target = $(event.currentTarget);
			var href = $target.find("a").attr("href");

			this.$("li").removeClass("Selected");

			$target.addClass("Selected");

			pubsub.trigger('noty_clear');

			if (href) {
				if (href == "/statements/") {
					$("#main")
						.height("100%")
						.html("<iframe src='http://10.128.225.86:8888/reports/' style='height: 84%; width: 100%;margin-top: 1em;border-radius: 5px;'></iframe>");
					App.Router.navigate("reports/", {
						trigger: false
					});
				} else if (href == "/appointments/") {
					var patientId = null;
					if (Cache.Patient) {
						patientId = Cache.Patient.id
					} else {
						var currentPath = Backbone.history.fragment.split('/')
						if (currentPath.length > 2 && currentPath[0] == 'patients') {
							patientId = currentPath[1]
						}
					}
					App.Router.navigate(href + (patientId ? patientId : ''), {
						trigger: true
					});
				} else {
					App.Router.navigate(href, {
						trigger: true
					});
				}
			}
		},

		onLinkToProfileClick: function(event) {
			var menu = this.$(".profile-options");

			menu.css({
				"min-width": this.$(".LinkToProfile").width()
			}).show().position({
				my: "right top",
				at: "right bottom",
				of: this.$(".LinkToProfile")
			});

			$(document).one("click", function() {
				menu.hide();
			});

			return false;
		},

		onUserRoleClick: function(event) {
			this.changeRole($(event.currentTarget).data("role"), $(event.currentTarget).data("id"));
		},

		changeRole: function(roleCode, roleId) {
			$.ajax({
				url: '/data/changeRole/?roleId=' + roleId,
				type: 'post',
				dataType: 'jsonp',
				contentType: 'application/json'

			}).done(function(data) {
				if(data.authToken && data.authToken.id){
					Core.Cookies.set("authToken", data.authToken.id);
					Core.Data.currentRole(roleCode);
					window.location.reload();
				}else{

				}

			}).fail(function() {

			});

		},

		logoutUser: function() {
			Core.Cookies.clear();
			App.Router.navigate("/auth/?logout=true", {
				trigger: true
			});
		},

		render: function() {
			var sections = [];

			switch (Core.Data.currentRole()) {
				case ROLES.NURSE_RECEPTIONIST:
					sections.push({
						title: "Пациенты",
						name: "patients",
						uri: "/patients/"
					}, {
						title: "Госпитализации",
						name: "appeals",
						uri: "/appeals/"
					}, {
						title: "Амбулаторные талоны"
					});
					break;
				case ROLES.DOCTOR_RECEPTIONIST:
					sections.push({
						title: "Госпитализации",
						name: "appeals",
						uri: "/appeals/"
					}, {
						title: "Пациенты",
						name: "patients",
						uri: "/patients/"
					});
					break;
				case ROLES.DOCTOR_DEPARTMENT:
					sections.push({
						title: "Госпитализации",
						name: "appeals",
						uri: "/appeals/"
					}, {
						title: "Пациенты",
						name: "patients",
						uri: "/patients/"
					}, {
						title: "Запись на консультацию",
						name: "appointments",
						uri: "/appointments/"
					});
					break;
				case ROLES.NURSE_DEPARTMENT:
					sections.push({
							title: "Пациенты",
							name: "patients",
							uri: "/patients/"
						}, {
							title: "Госпитализации",
							name: "appeals",
							uri: "/appeals/"
						}, {
							title: "Биоматериалы",
							name: "biomaterials",
							uri: "/biomaterials/"
						}, {
                            title: "Лист назначений",
                            name: "prescriptions",
                            uri: "/prescriptions/"
                        }, {
							title: "Отчёты",
							name: "reports",
							uri: "/reports/"
						}
						//,{title: "Амбулаторные талоны"}
					);
					break;
				case ROLES.CHIEF:
					sections.push({
						title: "Госпитализации",
						name: "appeals",
						uri: "/appeals/"
					}, {
						title: "Отчёты",
						name: "statements",
						uri: "/statements/"
					});
					break;
			}

			//var currentRoleTitle = "";

			// Роли доступные пользователю
			var userRoles = JSON.parse(Core.Cookies.get("roles"));
			// Роли реализованные в приложении
			var availableRoles = this.options.structure.roles;
			// Пересечение доступных и реализованных ролей
			var userAvailableRoles = [];


			_(availableRoles).each(function(element) {
				if (userRoles.indexOf(element.id) !== -1) {
					/*if (this._currentRole == element.role) {
						currentRoleTitle = element.title;
					}*/
					userAvailableRoles.push(element);
				}
			});

			var data = {
				sections: sections,
				doctor: this.options.structure.doctor,
				userAvailableRoles: userAvailableRoles,
				currentPage: App.Router.currentPage,
				currentRole: this._currentRole
			};

			this.$el.html(_.template(this.template, data));

			this.$(".LinkToProfile").button({
				icons: {
					primary: "icon-user-md",
					secondary: "icon-caret-down"
				}
			});
			this.$(".profile-options").hide().menu();
			this.$(".logout").button({
				icons: {
					primary: "icon-off"
				}
			});

			return this;
		}
	});

	return Header;
});
