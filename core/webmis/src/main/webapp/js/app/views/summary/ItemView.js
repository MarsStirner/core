define(function(require) {
	var template = require('text!templates/summary/summary-main.html');
	require("models/appeal");

	var Documents = require("views/documents/documents");

    var userPermissions = require('permissions');
    var CardNav = require('views/CardNav');
	require("models/appeal");
    require("views/print");

	return View.extend({
		initialize: function(){
			var self = this;
            var canSee = (userPermissions.indexOf('see_patient_summary') != -1);
            if(!canSee){
			    App.Router.navigate("/",{trigger: true});
            }

    		this.appeal = new App.Models.Appeal({
				id: this.options.appealId
			});


			this.patient = new App.Models.Patient();
			this.patient.set('id',this.options.patientId);

            $.when(this.patient.fetch(),this.appeal.fetch()).then(function(){
				self.renderPage();
            });

			
		},
		getPatientAppeals: function(patientId){
			this.appealsList.setPatientId(patientId);
			return this.appealsList.fetch();
		},
		getAppealById: function(id){
			this.appeal.set('id',id);
			return this.appeal.fetch();
		},
		render: function() {
			return this;
		},
		renderPage: function(appeal, events, selectedEventId){
            var patientId = this.options.patientId;

			var html = _.template(template, {
				patientId: this.options.patientId,
				patientName: this.patient.get('name').get('raw')
			});

            this.cardNav = new CardNav({
                permissions: userPermissions,
                patient: this.patient,
                structure: [{
                    link: '/patients/'+patientId+'/',
                    name: 'Карточка пациента',
                    permissions: ['see_patient_card']  
                },{
                    link: '/patients/'+patientId+'/appeals/',
                    name: 'Госпитализации',
                    permissions: ['see_patient_appeals']
                },{
                    link: '/patients/'+patientId+'/summary',
                    name: 'Сводная информация',
                    permissions: ['see_patient_summary'],
                    active: true
                }]
            });
	
            appeal = this.appeal;
			this.itemView = new Documents.Summary.Review.Layout({
				patientId: this.options.patientId,
				subId: this.options.docIds.split(','),
                appealId: this.appeal.get('id'),
                appeal: this.appeal
			});

			this.itemView.render();
			this.$el.append(html);

			this.$el.find('#summary-list').append(this.itemView.el);
			this.$el.find('.CardNav').append(this.cardNav.render().el);



		}
	});

});
