/**
 * User: FKurilov
 * Date: 08.06.12
 */
define(function(require) {

	var template = require('text!templates/diagnostics/consultations/consultations-page.tmpl')

	var Consultations = require('collections/diagnostics/consultations/Consultations')

	var EditConsultationView = require('views/diagnostics/consultations/EditConsultationView');
	var Grid = require('views/grid')
	var NewConsultationView = require('views/diagnostics/consultations/NewConsultationView');

	ConsultationView = View.extend({
		className: "ContentHolder",
		template: template,

		events: {
			//"click .Actions.ToggleFilters": "toggleFilters",
			"click #assign-consult": "onNewConsultClick"
		},

		initialize: function() {

			this.canAddDirection = !this.options.appeal.get('closed');

			this.collection = new Consultations();
			this.collection.appealId = this.options.appealId;


			this.collection.setParams({
				sortingField: "plannedEndDate",
				sortingMethod: "desc"
			});

			this.collection.extra = {
				// doctorId: (this.options.appeal.get('execPerson')).id,
				userId: Core.Cookies.get("userId"),
				appealClosed: this.options.appeal.get('closed')
			};

			this.grid = new Grid({
				popUpMode: true,
				collection: this.collection,
				template: "diagnostics/consultations/consultations-grid",
				gridTemplateId: "#consult-diagnostic-grid",
				rowTemplateId: "#consult-diagnostic-grid-row",
				defaultTemplateId: "#consult-diagnostic-grid-default"
			});

			this.grid.on('grid:rowClick', this.onGridRowClick, this);
			this.depended(this.grid);


			this.paginator = new App.Views.Paginator({
				collection: this.collection
			});
			this.depended(this.paginator);

			this.collection.fetch({
				// url: "/js/app/views/consultations/consultations.json",
				// dataType: 'json'
			});

			this.collection.on('remove', function() {
				this.collection.fetch({});
			}, this);

			pubsub.on('consultation:added', function() {
				this.collection.fetch();
			}, this);
		},

		onGridRowClick: function(model, event) {
			event.preventDefault();

			if (_.indexOf(event.target.classList, 'cancel-direction') >= 0) {
				this.cancelDirection(model);
			} else if (_.indexOf(event.target.classList, 'edit-direction') >= 0) {
				this.editDirection(model);
			} else if (_.indexOf(event.target.classList, 'edit-consultation') >= 0) {
				this.editConsultation(model);
			} else {
				this.openResult(model);
			}

		},
		openResult: function(model) {
			console.log('openResult', model);
			var self = this;
			this.trigger("change:viewState", {
				type: "diagnostics-consultations",
				mode: "SUB_REVIEW",
				options: {
					modelId: model.get('id'),
					appealId: self.options.appealId
				}
			});
			App.Router.updateUrl("/appeals/" + this.options.appealId + "/diagnostics-consultations/" + model.get('id'));

		},

		editDirection: function(model) {
			//console.log('editDirection', model);
			this.editConsultationView = new EditConsultationView(_.extend(this.options, {
				title: 'Редактирование направления',
				id: model.get('id')
			}));
			//this.editConsultationView.render().open();
		},

		editConsultation: function(model) {
			var self = this;
			this.trigger("change:viewState", {
				type: "diagnostics-consultations",
				mode: "SUB_EDIT",
				options: {
					subId: model.get('id'),
					appealId: self.options.appealId
				}
			});
			App.Router.updateUrl("/appeals/" + this.options.appealId + "/diagnostics-consultations/" + model.get('id') + '/edit');
		},

		cancelDirection: function(model) {
			///console.log('cancelDirection ', model);
			model.destroy({
				wait: true,
				success: function(model, response) {
					pubsub.trigger('noty', {
						text: 'Направление удалено',
						type: 'alert'
					});
					// this.collection.fetch();
				},
				error: function(x, error) {
					//console.log(arguments);

					var response = $.parseJSON(error.responseText);
					pubsub.trigger('noty', {
						text: 'Ошибка: ' + response.errorMessage,
						type: 'error'
					});
				}
			});

		},

		onNewConsultClick: function() {
			this.newConsultationView = new NewConsultationView(this.options);
			this.newConsultationView.render().open();
		},

		render: function() {
			this.$el.html(_.template(this.template, {
				canAddDirection: this.canAddDirection
			}));

			this.$("#assign-consult").button({
				icons: {
					primary: "icon-plus icon-color-green"
				}
			});


			this.$("#grid").html(this.grid.el);
			this.$("#grid-pager").html(this.paginator.render().el);


			this.delegateEvents();
			this.grid.delegateEvents();
			this.paginator.delegateEvents();

			return this;
		}
	});

	return ConsultationView;
});
