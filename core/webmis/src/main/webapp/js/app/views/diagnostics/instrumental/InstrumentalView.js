/**
 * User: FKurilov
 * Date: 08.06.12
 */
define(function(require) {
	var template = require('text!templates/diagnostics/instrumental/instrumental-page.tmpl');

	var InstrumentalResearch = require('models/diagnostics/instrumental/InstrumentalResearch');
	var InstrumentalResearchs = require('collections/diagnostics/instrumental/InstrumentalResearchs');

	var GridView = require('views/grid');
	var InstrumentalEditPopupView = require('views/diagnostics/instrumental/InstrumentalEditPopupView');
	var InstrumentalPopupView = require('views/diagnostics/instrumental/InstrumentalPopupView');
	var PaginatorView = require('views/paginator');



	var InstrumentalView = View.extend({
		className: "ContentHolder",
		template: template,

		events: {
			"click #assign-inst-diag": "onNewDiagnosticClick"
		},

		initialize: function(options) {
			console.log('view', options);
			this.collection = new InstrumentalResearchs([], {
				appealId: options.appealId
			});

			this.collection.setParams({
				sortingField: "plannedEndDate",
				sortingMethod: "desc"
			});

			this.collection.extra = {
				appealClosed: this.options.appeal.get('closed')
			};

			this.grid = new GridView({
				popUpMode: true,
				collection: this.collection,
				template: "diagnostics/instrumental/instrumental-grid",
				gridTemplateId: "#inst-diagnostic-grid",
				rowTemplateId: "#inst-diagnostic-grid-row",
				defaultTemplateId: "#inst-diagnostic-grid-default"
			});

			this.grid.on('grid:rowClick', this.onGridRowClick, this);
			this.depended(this.grid);


			this.paginator = new PaginatorView({
				collection: this.collection
			});
			this.depended(this.paginator);


			//this.collection.on("reset", function(collection) {
			//	console.log('reset collection', collection);
			//}, this);


			this.collection.fetch({});

			pubsub.on('instrumental-diagnostic:added', function() {
				this.collection.fetch();
			}, this);



		},

		onNewDiagnosticClick: function() {
			this.newAssignPopup = new InstrumentalPopupView({
				appeal: this.options.appeal
			});
			this.newAssignPopup.render().open();
		},

		onGridRowClick: function(model, event) {
			event.preventDefault();

			if (_.indexOf(event.target.classList, 'cancel-direction') >= 0) {
				this.cancelDirection(model);
			} else if (_.indexOf(event.target.classList, 'edit-direction') >= 0) {
				this.editDirection(model);
			} else {
				this.openResult(model);
			}

		},
		openResult: function(model) {
			console.log('openResult', model);
			var self = this;
			this.trigger("change:viewState", {
				type: "diagnostics-instrumental",
				mode: "SUB_REVIEW",
				options: {
					modelId: model.get('id'),
					appealId: self.options.appealId
				}
			});
			App.Router.updateUrl("/appeals/" + this.options.appealId + "/diagnostics-instrumental/" + model.get('id'));

		},

		cancelDirection: function(model) {
			var self = this;
			model.destroy({
				success: function(model, response) {
					if (response === true) {
						self.collection.trigger('reset');
						pubsub.trigger('noty', {
							text: 'Направление удалено'
						});
					} else {
						console.log('cancelDirection error', arguments);
					}
				},
				error: function() {
					console.log('cancelDirection error', arguments);
				}
			});


		},

		editDirection: function(model) {
			var self = this;

			var testId = model.get('id');
			var test = new InstrumentalResearch({
				"id": testId
			}, {
				appealId: this.options.appealId
			});

			test.fetch({
				success: function() {
					console.log('editDirection success', arguments);
					this.newEditPopup = new InstrumentalEditPopupView({
						appeal: self.options.appeal,
						model: test
					});
					this.newEditPopup.render().open();
				},
				error: function() {
					console.log('editDirection error', arguments);
				}
			});

		},

		render: function() {
			this.$el.html(_.template(this.template,{closed: this.options.appeal.get('closed')}));
			this.$("#grid").html(this.grid.el);
			this.$("#grid-pager").html(this.paginator.render().el);

			this.$("#assign-inst-diag").button({
				icons: {
					primary: "icon-plus icon-color-green"
				}
			});

			this.delegateEvents();
			this.grid.delegateEvents();
			this.paginator.delegateEvents();

			return this;
		},

		cleanUp: function() {
			this.collection.off(null, null, this);

		}
	});

	return InstrumentalView;
});
