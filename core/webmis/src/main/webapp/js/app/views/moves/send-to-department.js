/**
 * User: FKurilov
 * Date: 17.10.12
 */
define(function(require) {

	var tmpl = require('text!templates/moves/send-to-department.tmpl');
	var Departments = require('collections/departments');
	var Move = require('models/moves/move');

	return Form.extend({
		className: "popup",

		popUpOnly: true,

		template: tmpl,

		events: {
			/*"click .Cancel": "onCancelClick",
			"click .Save": "onSaveClick"*/
		},

		initialize: function(options) {
			_.bindAll(this);

			this.model = new Move();

			this.previousDepartmentName = this.options.previousDepartmentName;
			this.previousDepartmentDate = this.options.previousDepartmentDate;
			this.showDatepicker = this.options.showDatepicker ? this.options.showDatepicker : true;
			this.model.appealId = this.options.appealId;
			this.model.set("clientId", this.options.clientId);
			this.model.set("moveDatetime", this.options.moveDatetime);

			//console.log('move ',this.model );
			this.model.on("sync", function() {
				pubsub.trigger('noty', {
					text: 'Направление в отделение создано'
				});
				this.close();
			}, this);

			this.model.on("error", function() {
				pubsub.trigger('noty', {
					text: 'Ошибка при создании нового движения',
					type: 'error'
				});
			}, this);

			this.departments = new Departments();
			this.departments.setParams({
				filter: {
					hasBeds: true
				},
				limit: 0,
				sortingField: 'name',
				sortingMethod: 'asc'
			});
			this.departments.on("reset", this.onDepartmentsReset, this);
			this.departments.fetch();
		},

		onDepartmentsReset: function(collection) {
			this.departments.each(function(m) {
				this.$("#department").append($("<option/>", {
					text: m.get("name"),
					value: m.get("id")
				}));
			}, this);
		},

		onCancelClick: function(event) {
			this.close();
		},

		onSaveClick: function(event) {
			var readyToSave = this.save();
			if (readyToSave) {
				this.$(".Save").addClass("Disable").attr("disabled", true);
			}
		},

		open: function(opts) {
			this.$el.dialog("open");
			return this;
		},

		close: function() {

			this.$el.dialog("close");
			this.model.unbind(null, null, this);
			this.trigger("closed").unbind(null, null, this).remove();
			return this;
		},

		render: function() {
			if (!this.$el.hasClass("webmis")) {

				this.$el.html($.tmpl(this.template, {
					previousDepartmentName: this.previousDepartmentName,
					previousDepartmentDate: this.previousDepartmentDate,
					showDatepicker: this.showDatepicker
				}));

				$(this.el).dialog({
					autoOpen: false,
					width: "70em",
					modal: true,
					dialogClass: "webmis",
					resizable: false,
					title: this.options.popupTitle,
					buttons: [{
						text: "Сохранить",
						"class": "button-color-green",
						click: this.onSaveClick
					}, {
						text: "Отмена",
						click: this.onCancelClick
					}]
				});

				this.$("a").click(function(event) {
					event.preventDefault();
				});

				UIInitialize(this.el);


				this.$(".select2").width("100%").select2();

				this.delegateEvents();
			}

			this.model.connect("unitId", "department", this.$el);
			this.model.connect("moveDatetime", "movingDate", this.$el);
			this.model.connect("moveDatetime", "movingTime", this.$el);

			return this;
		}
	});

});
