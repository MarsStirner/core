define(function(require){
    var SendToDepartment = require("views/moves/send-to-department");
	require("collections/appeals");
	require("collections/doctors");
	require("collections/departments");
	require("views/grid");
	require("views/filter");
	require("views/filter-dictionaries");
	require("views/paginator");
	require("collections/department-patients");
	require("models/print/form007");
	require("views/print");

   		App.Views.AppealsList = View.extend({
		id: "main",

		initialize: function() {
			this.clearAll();

			this.on("template:loaded", this.ready, this);
			this.loadTemplate("pages/appeals-list");

			_.bindAll(this, 'printForm007');

		},

		events: {
			"click .shift-back": "decreaseDate",
			"click .shift-forward": "increaseDate",
			"click .toggle-filters": "toggleFilters"
		},

		increaseDate: function(event) {
			event.preventDefault();

			this.setDate(1);
		},

		decreaseDate: function(event) {
			event.preventDefault();

			this.setDate(-1);
		},

		setDate: function(increment) {
			increment = increment || 0;

			var $startDateTime = this.$(".date-range-start");
			var $endDateTime = this.$(".date-range-end");

			var startDate = $startDateTime.datepicker("getDate");
			var endDate = $endDateTime.datepicker("getDate");

			if (_.isDate(startDate)) startDate.setDate(startDate.getDate() + increment);
			if (_.isDate(endDate)) endDate.setDate(endDate.getDate() + increment);

			$startDateTime.datepicker("setDate", startDate);
			$endDateTime.datepicker("setDate", endDate);

			this.collection.setParams({
				page: 1
			});

			//this.$("#appeal-start-date").change();
			this.$("#appeal-end-date").change();
		},

		toggleFilters: function(event) {
			//$(event.currentTarget).toggleClass("Pushed");
			this.$(".Grid thead tr").toggleClass("EditTh");
			this.$(".Grid .Filter").toggle();
		},

		//Новое мероприятие/направление или перевод в отделение
		newSendToDepartment: function(appeal) {
			var previousDepartmentName = false;
			var previousDepartmentDate = false;

			var sendPopUp = new SendToDepartment({
				previousDepartmentName: previousDepartmentName,
				previousDepartmentDate: previousDepartmentDate,
				showDatepicker: false,
				appealId: appeal.get("id"),
				clientId: appeal.get("patient").get("id"),
				moveDatetime: appeal.get("createDatetime"),
				popupTitle: "Направление в отделение"
			}).render().open();

			sendPopUp.on("closed", function() {
				this.collection.fetch();
			}, this);
		},

		newHospitalBed: function(appealId) {
			this.trigger("change:viewState", {
				type: 'hospitalbed',
				options: {}
			});
			App.Router.updateUrl("appeals/" + appealId + "/hospitalbed/");
		},

		printForm007: function() {
			//console.log('printForm007', this)
			var endDate = $("#appeal-start-date").datepicker("getDate").getTime() + (7 * 60 + 59) * 60 * 1000;
			var beginDate = endDate - (24 * 60 - 1) * 60 * 1000;

			var form007 = new App.Models.PrintForm007({
				//departmentId: 18,
				beginDate: beginDate,
				endDate: endDate
			});

			new App.Views.Print({
				model: form007,
				template: "f007"
			});

			form007.fetch();
		},

		ready: function() {
			var view = this;

			this.$el.html($("#appeals-list-page").tmpl());

			var Collection;
			this.collection = Collection;

			var Filter;
			var AppealsGrid;

			this.separateRoles(ROLES.DOCTOR_RECEPTIONIST, function() {
				Collection = new App.Collections.Appeals();

				Filter = new App.Views.Filter({
					collection: Collection,
					templateId: "#appeals-list-filters-reception",
					path: this.options.path
				});

				AppealsGrid = new App.Views.Grid({
					collection: Collection,
					popUpMode: true,
					template: "grids/appeals",
					gridTemplateId: "#appeals-grid-doctor",
					rowTemplateId: "#appeals-grid-doctor-row",
					defaultTemplateId: "#appeals-grid-row-default"
				});

				AppealsGrid.on('grid:rowClick', function(model, event) {
					if (event.target.localName != 'a') {
						var url = '/appeals/' + model.get('id') + '/';
						window.open(url);
					}
				});

			}, this);

			this.separateRoles(ROLES.NURSE_RECEPTIONIST, function() {
				Collection = new App.Collections.Appeals();

				var DepCollection = new App.Collections.Departments();


				Filter = new App.Views.Filter({
					collection: Collection,
					templateId: "#appeals-list-filters-reception",
					path: this.options.path
				});

				AppealsGrid = new App.Views.Grid({
					collection: Collection,
					popUpMode: true,
					template: "grids/appeals",
					gridTemplateId: "#appeals-grid-nurse",
					rowTemplateId: "#appeals-grid-nurse-row",
					defaultTemplateId: "#appeals-grid-row-default"
				});

				AppealsGrid.on('grid:rowClick', function(model, event) {
					if (event.target.localName != 'a') {
						var url = '/appeals/' + model.get('id') + '/';
						window.open(url);
					} else {
						view.newSendToDepartment(model);
					}
				});

			}, this);

			this.separateRoles(ROLES.DOCTOR_DEPARTMENT, function() {

				Collection = new App.Collections.DepartmentPatients({
					role: "doctor"
				});


				var doctors = new App.Collections.Doctors();
				doctors.setParams({
					limit: 9999,
					sortingField: 'lastname'
				});


				var departments = new App.Collections.Departments();
				departments.setParams({
					filter: {
						hasBeds: true
					},
					limit: 0,
					sortingField: 'name',
					sortingMethod: 'asc'
				});

				doctors.on('reset', function() {
					setTimeout(function() {
						view.$("#appeal-start-date").change();
					}, 500);
				}); //код распечатать и сжечь

				Filter = new App.Views.FilterDictionaries({
					collection: Collection,
					templateId: "#appeals-list-filters-doctor-department",
					path: this.options.path,
					dictionaries: {
						departments: {
							collection: departments,
							elementId: "deps-dictionary",
							getText: function(model) {
								return model.get("name");
							},
							getValue: function(model) {
								return model.get("id");
							},
							preselectedValue: Core.Cookies.get("userDepartmentId")
						},
						doctors: {
							collection: doctors,
							elementId: "docs-dictionary",
							getText: function(model) {
								return model.get("name").raw;
							},
							getValue: function(model) {
								return model.get("id");
							},
							matcher: function(term, text, opt) {
								return text.split(' ')[0].toUpperCase().indexOf(term.toUpperCase()) >= 0
							},
							preselectedValue: Core.Cookies.get("userId")
						}

					}
				});

				AppealsGrid = new App.Views.Grid({
					popUpMode: true,
					collection: Collection,
					template: "grids/appeals",
					gridTemplateId: "#appeals-grid-doctor-department",
					rowTemplateId: "#appeals-grid-doctor-department-row",
					defaultTemplateId: "#appeals-grid-row-default"
				});

				AppealsGrid.on('grid:rowClick', function(model, event) {
					if (event.target.localName != 'a') {
						var url = '/appeals/' + model.get('id') + '/';
						window.open(url);
					}
				});

				Collection.on("reset", function() {
					if (Collection.length && Collection.requestData) {
						if (!this.$(".recordCounters").length) {
							this.$(".Container").append(
								'<div style="margin-top: 2em;" class="recordCounters">' +
								//'<label style="margin-right: 2em;">Записей на странице: <b class="recordsCountPage"></b></label>' +
								'<label>Всего пациентов: <b class="recordsCountTotal"></b></label>' +
								'</div>'
							);
						}
						//this.$(".recordsCountPage").text(Collection.length);
						this.$(".recordsCountTotal").text(Collection.requestData.recordsCount);
					} else {
						this.$(".recordCounters").remove();
					}
				}, this);



			}, this);

			this.separateRoles(ROLES.NURSE_DEPARTMENT, function() {
				Collection = new App.Collections.DepartmentPatients();
				/*Collection.setParams({'filter[date]':1334300400000})*/

				Collection.setParams({
					sortingField: 'bed',
					sortingMethod: 'asc'
				});
				Collection.reset();

				this.printButton = $('<button style="float: right;">Печать</button>').button().click(this.printForm007);

				Filter = new App.Views.Filter({
					collection: Collection,
					templateId: "#appeals-list-filters-nurse-department",
					path: this.options.path
				});

				AppealsGrid = new App.Views.Grid({
					collection: Collection,
					popUpMode: true,
					template: "grids/appeals",
					gridTemplateId: "#appeals-grid-nurse-department",
					rowTemplateId: "#appeals-grid-nurse-department-row",
					defaultTemplateId: "#appeals-grid-row-default"
				});

				AppealsGrid.on('grid:rowClick', function(model, event) {
					var target = $(event.target);

					if (target.hasClass('bed-registration')) {
						//						console.log(model);
						//						console.log('bed-registration', model.get('id'));
						view.newHospitalBed(model.get('id'));
					} else {
						App.Router.navigate('/appeals/' + model.get('id') + '/monitoring', {
							trigger: true
						});
					}

				});


				setTimeout(function() {
					view.$("#appeal-start-date").change();
				}, 0);


			}, this);


			//главный врач
			this.separateRoles(ROLES.CHIEF, function() {

				Collection = new App.Collections.DepartmentPatients({
					role: "doctor"
				});
				//Collection.reset();

				// Collection.setParams({
				// 	filter: {
				// 		roleId: 25
				// 	}
				// });

				var doctors = new App.Collections.Doctors();
				doctors.setParams({
					limit: 9999,
					sortingField: 'lastname'
				});


				var departments = new App.Collections.Departments();
				departments.setParams({
					filter: {
						hasBeds: true
					},
					limit: 0,
					sortingField: 'name',
					sortingMethod: 'asc'
				});

				doctors.on('reset', function() {
					setTimeout(function() {
						view.$("#appeal-start-date").change();
					}, 500);
				}); //код распечатать и сжечь

				Filter = new App.Views.FilterDictionaries({
					collection: Collection,
					templateId: "#appeals-list-filters-doctor-department",
					path: this.options.path,
					dictionaries: {
						departments: {
							collection: departments,
							elementId: "deps-dictionary",
							getText: function(model) {
								return model.get("name");
							},
							getValue: function(model) {
								return model.get("id");
							},
							preselectedValue: Core.Cookies.get("userDepartmentId")
						},
						doctors: {
							collection: doctors,
							elementId: "docs-dictionary",
							getText: function(model) {
								return model.get("name").raw;
							},
							getValue: function(model) {
								return model.get("id");
							},
							matcher: function(term, text, opt) {
								return text.split(' ')[0].toUpperCase().indexOf(term.toUpperCase()) >= 0
							},
							preselectedValue: Core.Cookies.get("userId")
						}

					}
				});

				AppealsGrid = new App.Views.Grid({
					collection: Collection,
					template: "grids/appeals",
					gridTemplateId: "#appeals-grid-doctor-department",
					rowTemplateId: "#appeals-grid-doctor-department-row",
					defaultTemplateId: "#appeals-grid-row-default"
				});

				Collection.on("reset", function() {
					if (Collection.length && Collection.requestData) {
						if (!this.$(".recordCounters").length) {
							this.$(".Container").append(
								'<div style="margin-top: 2em;" class="recordCounters">' +
								//'<label style="margin-right: 2em;">Записей на странице: <b class="recordsCountPage"></b></label>' +
								'<label>Всего пациентов: <b class="recordsCountTotal"></b></label>' +
								'</div>'
							);
						}
						//this.$(".recordsCountPage").text(Collection.length);
						this.$(".recordsCountTotal").text(Collection.requestData.recordsCount);
					} else {
						this.$(".recordCounters").remove();
					}
				}, this);
			}, this);


   
			this.collection = Collection;


			this.collection.on('reset', function() {
				console.log('collection reset', arguments);
			});

			this.depended(Filter);

			this.$el.prepend(Filter.render().el);

			this.depended(AppealsGrid);

			this.$el.find(".Container").html(AppealsGrid.render().el);

			if (this.printButton) {
				this.$el.find(".EditForm").append(this.printButton);
			}


			// Пэйджинатор
			var Paginator = new App.Views.Paginator({
				collection: Collection
			});
			this.depended(Paginator);

			this.$(".Container").append(Paginator.render().el);

			//UIInitialize(this.el);

			this.$(".date-input").datepicker().mask("99.99.9999");

			this.$("#appeal-start-time").timepicker({
				showPeriodLabels: false,
				defaultTime: "08:00"
			}).mask("99:99");
			this.$("#appeal-end-time").timepicker({
				showPeriodLabels: false,
				defaultTime: "07:59"
			}).mask("99:99");

			this.$(".print-btn").button({
				icons: {
					primary: "icon-print"
				}
			});
			this.$(".shift-back").button({
				icons: {
					primary: "icon-angle-left"
				},
				text: false
			});
			this.$(".shift-forward").button({
				icons: {
					primary: "icon-angle-right"
				},
				text: false
			});
			this.$(".toggle-filters").button({
				icons: {
					primary: "icon-filter"
				}
			});

			var now = new Date();
			var startDate = new Date();
			var endDate = new Date();

			if (now.getHours() >= 0 && now.getHours() < 8) {
				startDate.setDate(startDate.getDate() - 1);
			}

			//для медсестры отделения ставим текущее время в таймпикере
			this.separateRoles(ROLES.NURSE_DEPARTMENT, function() {
				var time = moment().format('HH:mm');
				this.$("#appeal-start-time").val(time);
			}, this);

			endDate.setDate(startDate.getDate() + 1);

			this.$(".date-range-start, #appeal-start-date").datepicker("setDate", startDate);
			this.$(".date-range-end").datepicker("setDate", endDate);

			this.setDate();
		}
	});
});
