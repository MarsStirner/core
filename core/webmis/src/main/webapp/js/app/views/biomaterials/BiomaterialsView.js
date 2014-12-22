define(function(require) {

	var biomaterialsTemplate = require('text!templates/biomaterials/biomaterials-page.tmpl');

	require('collections/departments');
	require('collections/dictionary-values');
	var BarcodesCollection = require('collections/biomaterials/Barcodes');
	var BiomaterialsCollection = require('collections/biomaterials/Biomaterials');
	var JobsCollection = require('collections/biomaterials/JobTickets');

	require('views/print');
	var CountView = require('views/biomaterials/BiomaterialsCountsView');
	var DatetimePikerView = require('views/ui/RangeDatepickerView');
	var GridView = require('views/grid');
	var JobPopupView = require('views/biomaterials/BiomaterialPopupView');
	var SelectView = require('views/ui/SelectView');


	var BiomaterialsView = View.extend({
		///className: "ContentHolder",
		template: biomaterialsTemplate,
		events: {
			"click #select-all": "selectAll",

			"change .id": "onCheckboxChange"
		},

		initialize: function() {
			var view = this;

			view.collection = new BiomaterialsCollection();

			view.collection.setParams({
				sortingField: "date",
				sortingMethod: "asc",
				filter: {
					status: 0
				}
			});


			view.collection.on('reset', function() {
				view.updateButtons();
				view.resetSelectAllCheckbox();
				///console.log('reset', view.collection,view);

			});


			//	view.collection.on('reset', view.setDefaultDepartment, view);

			view.initGrid();

			view.initCounts();

			view.collection.fetch();

			view.initJobs();


		},

		initGrid: function() {
			var view = this;

			view.grid = new GridView({
				popUpMode: true,
				collection: view.collection,
				template: "biomaterials/biomaterials-grid",
				gridTemplateId: "#biomaterials-grid",
				rowTemplateId: "#biomaterials-grid-row",
				defaultTemplateId: "#biomaterials-grid-row-default",
				errorTemplateId: "#biomaterials-grid-row-error"
				//				,fetchTemplateId: "#biomaterials-grid-row-on-fetch"
			});

			view.depended(view.grid);

			view.grid.on('grid:rowClick', view.onGridRowClick, view);

		},

		onGridRowClick: function(model, event) {
			var view = this,
				$eventTarget = $(event.target);

			console.log($eventTarget)

			if (!$eventTarget.hasClass('id') && $eventTarget.hasClass('open-popup')) {
				view.openJobPopup(model);
			}

		},

		openJobPopup: function(model) {
			console.log('openJobPopup');
			var jobPopupView = new JobPopupView({
				biomaterial: model
			});

			jobPopupView.render().open();
		},

		onCheckboxChange: function(event) {
			var view = this;
			var selected = false;

			var $checkbox = $(event.target);

			if ($checkbox.prop('checked')) {
				selected = true;
			}

			var model = view.collection.get($checkbox.val());
			model.set({
				'selected': selected
			});

			view.updateButtons();

		},

		selectAll: function(event) {
			var view = this;
			var $select_all_button = $(event.target);

			view.$('.id').attr('checked', $select_all_button.prop("checked")).trigger('change');

		},

		resetSelectAllCheckbox: function() {
			var view = this;

			view.$('#select-all').prop('checked', false);
		},

		setStatus: function(modelsArray, status) {
			var view = this;

			view.jobs.reset();

			_.each(modelsArray, function(model) {
				//console.log('model',model);
				if (model.get('status') !== status) {
					view.jobs.add({
						'id': model.get('id'),
						'status': status
					});
				}
			});

			if (view.jobs.length) {
				view.jobs.updateAll();
			}

		},

		updateButtons: function() {
			var view = this;

			view.updateExecuteButton();
			view.updateSendToLabButton();
		},

		updateExecuteButton: function() {
			var view = this;
			var disabled = true;
			var jtc = view.collection.selected;

			if (jtc.status_0.length > 0 && jtc.status_1.length === 0 && jtc.status_2.length === 0) {
				disabled = false;
			}

			view.$('#execute').button({
				disabled: disabled
			});
		},

		updateSendToLabButton: function() {
			var view = this;
			var disabled = true;
			var jtc = view.collection.selected;

			if (jtc.status_0.length === 0 && jtc.status_1.length > 0 && jtc.status_2.length === 0) {
				disabled = false;
			}

			view.$('#send-to-lab').button({
				disabled: disabled
			});
		},


		selectedAndStatus10: function(model) {
			if (model.get('selected') && (model.get('status') < 2)) {
				return true;
			}
		},

		selectedAndStatus012: function(model) {
			if (model.get('selected') && (model.get('status') <= 2)) {
				return true;
			}
		},

		/***
		 * Запускает печать журнала выполнения работ
		 */
		printWorkList: function() {
			var view = this;
			var labTests = view.collection.getLabTests(view.selectedAndStatus012);
			var workList = view.collection.makeWorkList(labTests);

			if (workList.length) {

				new App.Views.Print({
					data: {
						'jobs': workList
					},
					template: "WorkList"
				});

			} else {
				pubsub.trigger('noty', {
					text: 'Выберите хотя бы один забор биоматериала!',
					type: 'alert'
				});
			}

		},

		printBarcodes: function() {
			var view = this;
			var labTests = view.collection.getLabTests(view.selectedAndStatus012);
			var barcodes = view.collection.makeBarcodes(labTests);

			if (barcodes.length) {
				//статус заборов биоматериала для которых начали печатать штрихкод меняется на "Выполняется"
				view.setStatus(view.collection.getSelectedModelsWithStatus0(), 1);

				new App.Views.Print({
					data: {
						'barcodes': barcodes
					},
					template: "WorkListBCode"
				});

			} else {
				pubsub.trigger('noty', {
					text: 'Выберите хотя бы один забор биоматериала!',
					type: 'alert'
				});
			}


		},

		initJobs: function() {
			var view = this;

			view.jobs = new JobsCollection();

			view.jobs.on('updateAll:success', function() {
				//pubsub.trigger('noty', {text: 'Статус обновлён', type: 'success'});
				view.collection.fetch();
			});

			view.jobs.on('updateAll:error', function() {
				pubsub.trigger('noty', {
					text: 'Не удалось обновить статус',
					type: 'error'
				});
			});

		},


		initCounts: function() {
			var view = this;

			view.counts = new CountView({
				collection: view.collection
			});

			view.depended(view.counts);
		},

		initTissues: function() {
			var view = this;

			//Получаем список типов биоматериалов
			view.tissues = new App.Collections.DictionaryValues("", {
				name: "tissueTypes"
			});

			view.tissues.setParams({
				sortingField: 'name',
				sortingMethod: 'asc'
			});

			view.tissueSelect = new SelectView({
				collection: view.tissues,
				el: view.$('#biomaterial')
			});

			view.depended(view.tissueSelect);

		},

		initDepartments: function() {
			var view = this;

			//список отделений
			view.departments = new App.Collections.Departments();

			view.departments.setParams({
				filter: {
					hasBeds: true
				},
				limit: 0,
				sortingField: 'name',
				sortingMethod: 'asc'
			});



			//строим селест после того как получили коллекцию биоматериалов, так как оттуда надо взять ид отделения

			function onetime() {
				view.departmentSelect = new SelectView({
					collection: view.departments,
					el: view.$('#departments'),
					selectText: 'name',
					initSelection: view.collection.requestData.filter.departmentId
				});

				view.depended(view.departmentSelect);
				view.collection.off('reset', onetime, view);
			}

			view.collection.on('reset', onetime, view);



		},

		setDefaultDepartment: function() {
			var view = this;

			var departmentId = view.collection.requestData.filter.departmentId;
			console.log('setDefaultDepartment', departmentId);

			//view.$(view.departmentSelect.$el).select2('val',departmentId);
			view.departmentSelect.val(departmentId);
			view.collection.off('reset', view.setDefaultDepartment);

		},


		initStatusFilterButtonset: function() {
			var view = this;

			view.$("#status_buttonset").buttonset();
			view.collection.on('reset', function() {
				var all = view.collection.count.all;
				var status_0 = view.collection.count.status_0;
				var status_1 = view.collection.count.status_1;
				var status_2 = view.collection.count.status_2;

				// view.$('#status-all-count').html(all ? '(' + all + ')' : '');
				view.$('#status-0-count').html(status_0 ? '(' + status_0 + ')' : '');
				view.$('#status-1-count').html(status_1 ? '(' + status_1 + ')' : '');
				view.$('#status-2-count').html(status_2 ? '(' + status_2 + ')' : '');
			});

			view.collection.on('fetch', function(e) {
				view.$('#status-all-count, #status-0-count,#status-1-count,#status-2-count').html('');
			});
		},
		initExecuteButton: function() {
			var view = this;

			view.$('#execute').button().on('click', function() {
				view.setStatus(view.collection.getSelectedModels(), 1);
			});

			//view.updateExecuteButton();
		},

		initResetAllButton: function() {
			var view = this;

			view.$('#reset-all').button().on('click', function() {
				view.setStatus(view.collection.models, 0);
			});

		},

		initSendToLabButton: function() {
			var view = this;

			view.$('#send-to-lab').button().on('click', function() {
				view.setStatus(view.collection.getSelectedModels(), 2);
			});

			//view.updateSendToLabButton();
		},

		initPrintButton: function() {
			var view = this;

			var options = {
				label: 'Печать',
				handler: view.printWorkList,
				scope: view,
				dropDownItems: [{
						label: 'Печать штрихкодов',
						handler: view.printBarcodes
					}, {
						label: 'Печать журнала выполнений работ',
						handler: view.printWorkList
					}
				]
			};

			var $list = view.$('.split-button-dropdown');

			_(options.dropDownItems).each(function(ddi) {
				$list.append($("<li><a href='#' class='SubPrint'>" + ddi.label + "</a></li>").click(function() {
					event.preventDefault();
					ddi.handler.apply(options.scope);
				}));
			});


			view.$('#print').button({
				label: options.label
			})
				.click(function() {
				options.handler.apply(options.scope);
			})
				.next().button({
				text: false,
				icons: {
					primary: "ui-icon-triangle-1-s"
				}
			})
				.click(function() {
				var menu = $(this)
					.parent().next('.split-button-dropdown')
					.show()
					.position({
					my: "right top",
					at: "right bottom",
					of: this,
					collision: "fit"
				});
				$(document).one("click", function() {
					menu.hide();
				});
				return false;
			})
				.parent()
				.buttonset()
				.next()
				.hide()
				.menu().css({
				'position': 'absolute'
			});

		},

		initFilters: function() {
			var view = this;

			view.$('#biomaterials-table-filter').on('change', function() {
				view.filterCollection();
			});

			view.$('#biomaterials-head-filter').on('change', function() {
				view.filterCollection();
			});
		},

		filterCollection: function() {
			var view = this;

			var filter1 = Core.Forms.serializeToObject($('#biomaterials-table-filter'));
			var filter2 = Core.Forms.serializeToObject($('#biomaterials-head-filter'));

			var filter = _.extend(filter1, filter2);
			//console.log(filter)

			view.collection.setParams({
				filter: filter
			});

			if (_.size(filter)) {
				view.collection.fetch();
			} else {
				view.collection.requestData = {};
				view.collection.reset();
			}

		},

		initDatepicker: function() {
			var view = this;
			var $filterDate = view.$("#begin-date");
			var $endDate = view.$("#end-date");

			UIInitialize(view.$el);

			var startOfday = moment(new Date()).startOf('day');

			var endOfDay = moment(new Date()).endOf("day");

			$filterDate.datepicker("setDate", startOfday.toDate());

			//			var e = (new Date()).getTime() + ((60 * 60 * 24) - 60) * 1000;
			//// это неправильно у енддате получается текуший час и минуты, надо 23:59
			$endDate.val(endOfDay.valueOf());

			$filterDate.on('change', function() {
				var start = $(this).datepicker("getDate").getTime();
				var end = start + ((60 * 60 * 24) - 60) * 1000;
				//console.log('$filterDate', start, end)
				$endDate.val(end);
			});

		},


		render: function() {
			var view = this;

			view.$el.html($.tmpl(view.template));


			view.initStatusFilterButtonset();
			view.initSendToLabButton();
			view.initExecuteButton();
			view.initResetAllButton();

			view.initPrintButton();
			view.initDatepicker();

			view.initTissues();
			view.initDepartments();

			view.initFilters();


			view.$("#biomaterial-grid").html(view.grid.el);
			view.$("#biomaterial-count").append(view.counts.el);


			//UIInitialize(view.el);
			//			this.delegateEvents();
			this.grid.delegateEvents();

			return view;
		}

	});

	return BiomaterialsView;

});
