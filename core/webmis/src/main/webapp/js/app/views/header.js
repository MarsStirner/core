define(function () {
	var Header = View.extend({
		//tagName: "header",
		events: {
			"click .Actions.Logout": "logOut"
		},

		className: "Header Clearfix",

		logOut: function (event) {
			event.preventDefault();

			Core.Cookies.clear();
			App.Router.navigate("/auth/?logout=true", {trigger: true});
		},

		initialize: function () {
			this.on("template:loaded", this.ready, this);
			this.loadTemplate("header");
		},

		ready: function () {
			var view = this;

			view.$el.html($("#header").tmpl(view.options.structure));

			this.separateRoles(ROLES.NURSE_RECEPTIONIST, function () {
				var NavigationView = new Navigation(
					{
						structure: [
							{
								name: "patients",
								uri: "/patients/",
								title: "Пациенты"
							},
							{
								name: "appeals",
								uri: "/appeals/",
								title: "Госпитализации"
							},
							{
								title: "Амбулаторные талоны"
							}
						]
					}
				);

				NavigationView.render();
			});


			this.separateRoles(ROLES.DOCTOR_RECEPTIONIST, function () {
				var NavigationView = new Navigation(
					{
						structure: [
							{
								name: "appeals",
								uri: "/appeals/",
								title: "Госпитализации"
							},
							{
								name: "patients",
								uri: "/patients/",
								title: "Пациенты"
							}
						]
					}
				);
				NavigationView.render();
			});

			this.separateRoles(ROLES.DOCTOR_DEPARTMENT, function () {
				var NavigationView = new Navigation(
					{
						structure: [
							{
								name: "appeals",
								uri: "/appeals/",
								title: "Госпитализации"
							},
							{
								name: "patients",
								uri: "/patients/",
								title: "Пациенты"
							}
						]
					}
				);
				NavigationView.render();
			});

			this.separateRoles(ROLES.NURSE_DEPARTMENT, function () {
				var NavigationView = new Navigation(
					{
						structure: [
							{
								name: "patients",
								uri: "/patients/",
								title: "Пациенты"
							},
							{
								name: "appeals",
								uri: "/appeals/",
								title: "Госпитализации"
							},
							{
								name: "biomaterials",
								uri: "/biomaterials/",
								title: "Биоматериалы"
							},
							{
								name: "reports",
								uri: "/reports/",
								title: "Отчёты"
							},
							{
								title: "Амбулаторные талоны"
							}
						]
					}
				);
				NavigationView.render();
			});

			var RoleSelectorView = new RoleSelector({
				structure: this.options.structure
			});
			RoleSelectorView.render();
		}
	});

	var Navigation = View.extend({
		el: ".SectionNav",

		render: function () {
			var view = this;

			_.each(this.options.structure, function (element) {
				var NavigationItemView = new NavigationItem(element);
				NavigationItemView.on("navSelected", view.updateUrl, view);
				view.$el.append(NavigationItemView.render().el);
			});

			return this
		},

		updateUrl: function (event) {
			var $target = $(event.currentTarget),
				href = $target.find("a").attr("href");

			this.$("li").removeClass("Selected")

			$target.addClass("Selected");

			if (href) {
				App.Router.navigate(href, {trigger: true});
			}

			pubsub.trigger('noty_clear');
		}
	});

	var NavigationItem = View.extend({
		tagName: "li",
		events: {
			"click": "onClick"
		},

		onClick: function (event) {
			event.preventDefault();
			event.stopPropagation();
			this.trigger("navSelected", event);
		},

		render: function () {
			if (App.Router.currentPage == this.options.name) {
				this.$el.addClass("Selected");
			}

			this.$el.html($("#navigation-item").tmpl(this.options));

			return this
		}
	});


	var RoleSelector = View.extend({
		el: ".RoleContainer",
		events: {
			"click li": "changeRole"
		},
		initialize: function () {
			if (!Core.Data.currentRole()) {
				Core.Data.currentRole(this.options.structure.roles[0].role);
			}
			this._currentRole = Core.Data.currentRole();
		},
		changeRole: function (event) {
			Core.Data.currentRole($(event.currentTarget).data("role"));
			window.location.reload();
		},
		render: function () {
			var view = this;
			this.$el.html($("#role-selector").tmpl(this.options.structure));

			var availableRoles = JSON.parse(Core.Cookies.get("roles"));

			if (this.options.structure.roles) {
				_(this.options.structure.roles).each(function (element) {
					if (availableRoles.indexOf(element.id) !== -1) {
						if (view._currentRole == element.role) {
							view.$(".Title span").html(element.title);
						}

						var RoleSelectorItemView = new RoleSelectorItem(element);
						view.$("ul").append(RoleSelectorItemView.render().el);
					}
				});
			}
			UIInitialize(this.el);

			return this
		}
	});
	var RoleSelectorItem = View.extend({
		tagName: "li",
		initialize: function () {
			if (this.options.role) {
				this.$el.data("role", this.options.role)
			}
		},
		render: function () {
			this.$el.html(this.options.title);

			return this
		}
	});

	return Header;
});
