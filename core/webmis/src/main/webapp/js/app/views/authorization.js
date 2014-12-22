define(["collections/authorization/roles", "models/authorization/authorization"], function() {
	App.Views.Authorization = View.extend({
		id: "main",

		events: {
			"click .Submit": "getRoles"
		},

		getRoles: function(event) {
			event.preventDefault();

			var login = this.$("[name='login']").val(),
				password = this.$("[name='password']").val().trim().length ? MD5(this.$("[name='password']").val()) : "";

			this.collection.login = login;
			this.collection.password = password;
			this.collection.fetch();

		},

		showAvailableRoles: function() {
			var view = this;

			if (this.collection.length > 1) {
				view.$(".LoginForm").html($("#authorization-page-role-form").tmpl(this.collection.doctor.toJSON()));

				this.collection.each(function(model) {
					var Role = new RoleView({
						model: model
					});
					view.$("#roles-list").append(Role.render().el);
				});
			} else {
				//this.redirect(this.collection.first());

				new RoleView({
					model: view.collection.first()
				}).chooseRole();
			}
		},

		showErrorToolTip: function(c, error) {
			if (error && error.responseText) {
				var errorMessage = "Ошибка авторизации, попробуйте снова.";
				try {
					var errorJSON = JSON.parse(error.responseText);
					var rawErrorMessage = errorJSON.errorMessage.replace(/\s/g, "").toUpperCase();

					if (/СОТРУДНИК.*НЕНАЙДЕН/gi.test(rawErrorMessage) || /ПОЛЬЗОВАТЕЛЬ.*НЕНАЙДЕН/gi.test(rawErrorMessage))
						errorMessage = "Неверный логин или пароль";
					else
						errorMessage = errorJSON.errorMessage;
				} catch (e) {

				}

				this.$("#auth-error .Error").text(errorMessage).parent().fadeIn("fast");
			}
		},

		initialize: function() {
			this.on("template:loaded", this.ready, this);
			this.loadTemplate("authorization");

			this.collection = new App.Collections.Roles();
			this.collection.on("reset", this.showAvailableRoles, this);
			this.collection.on("error", this.showErrorToolTip, this);
		},
		ready: function() {
			this.$el.append($("#authorization-page").tmpl());
			this.$(".LoginForm").html($("#authorization-page-login-form").tmpl({
				useTextForPassword: (navigator.appVersion.indexOf("Mac") != -1) && $.browser.webkit
			}));
			this.$("#auth-error").css({
				"width": "100%",
				"margin-left": "-1.2em",
				"margin-bottom": "1em"
			}).hide();
			this.$(".Submit").button();
		},
		render: function() {
			$("#wrapper").html(this.el);

			return this;
		}
	});

	var RoleView = View.extend({
		events: {
			"click .role": "chooseRole"
		},

		tagName: "li",

		chooseRole: function(event) {
			var Authorization = new App.Models.Authorization();

			Authorization.login = this.model.collection.login;
			Authorization.password = this.model.collection.password;
			Authorization.roleId = event ? $(event.currentTarget).data("role-id") : this.model.get("id");


			Authorization.on("change", this.redirect, this);
			Authorization.fetch();
		},

		redirect: function(model) {
			var currentRole = ROLES.DEFAULT;

			var Doctor = new App.Models.Doctor(model.get("doctor"));

			checkForErrors(model.get("authToken"), "authToken doesn't found in response");
			checkForErrors(model.get("authToken").id, "authToken doesn't contains id");

			checkForErrors(Doctor.get("name").get("first"), "Doctor's first name is required");
			checkForErrors(Doctor.get("name").get("last"), "Doctor's last name is required");

			var roleUnavailable = false;

			// Соответствие загружаемых ролей к предопределённым ролям
			switch (parseInt(model.roleId)) {
				case 24:
					{
						Core.Cookies.set("currentRole", ROLES.DOCTOR_DEPARTMENT);
						break;
					}
				case 25:
					{
						Core.Cookies.set("currentRole", ROLES.NURSE_DEPARTMENT);
						break;
					}
				case 26:
					{
						// Глав врач
						Core.Cookies.set("currentRole", ROLES.CHIEF);
						break;
					}
				case 27:
					{
						// Зав отделением
						break;
					}
				case 28:
					{
						// Дежурный врач отделения
						break;
					}
				case 29:
					{
						Core.Cookies.set("currentRole", ROLES.NURSE_RECEPTIONIST);
						break;
					}
				case 30:
					{
						Core.Cookies.set("currentRole", ROLES.DOCTOR_RECEPTIONIST);
						break;
					}
				default:
					{
						roleUnavailable = true;

						$("<div><p>Выбранная роль недоступна.</p></div>").dialog({
							modal: true,
							resizable: false,
							buttons: {
								"Принять": function() {
									//window.location.href = "/auth/";
									$(this).dialog("close");
									return false;
								}
							}
						}).dialog("open");
						//wrongRolePopup.dialog("open");
						//window.location.href = "/auth/";
						//return false;
					}
			}

			if (!roleUnavailable) {
				Core.Cookies.set("authToken", model.get("authToken").id);
				Core.Cookies.set("userId", model.get("userId"));
				Core.Cookies.set("doctorFirstName", Doctor.get("name").get("first"));
				Core.Cookies.set("doctorLastName", Doctor.get("name").get("last"));
				Core.Cookies.set("doctorMiddleName", Doctor.get("name").get("middle"));
				if (Doctor.get("department")) {
					Core.Cookies.set("userDepartmentId", Doctor.get("department").get("id"));
				}


				Core.Cookies.set("roles", JSON.stringify(this.model.collection.pluck("id")));

				$("#wrapper").empty();

				var ref = Core.Url.getReferrer();

				if (!ref || ref === "/auth/" || Core.Url.extractUrlParameters() && Core.Url.extractUrlParameters().logout) ref = "/";

				// Возвращаем пользователя на предыдущую страницу
				window.location.href = ref;
			}
		},

		render: function() {
			this.$el.html($("#authorization-page-role").tmpl(this.model.toJSON()));

			this.$("button").button();

			return this
		}
	});

	return {};
});
