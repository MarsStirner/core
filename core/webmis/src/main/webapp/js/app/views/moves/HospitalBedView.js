/**
 * User: VKondratev
 * Date: 29.11.12
 */

define(function(require) {
	var template = require('text!templates/moves/hospital-bed.tmpl');
	var Beds = require('collections/moves/Beds');
	var Moves = require('collections/moves/moves');
	var HospitalBed = require('models/moves/HospitalBed');
	//var Move = require('models/moves/move');
	var Departments = require('collections/departments');
    var BedProfilesDir = require('collections/moves/BedProfilesDir')
	var BedView = require('views/moves/BedView');


	return View.extend({
		className: "ContentHolder",
		template: template,

		events: {
			'change .Departments': 'onSelectDepartment',
			'change .bed-profiles': 'onSelectBedProfile',
			'change .movedFromUnitId': 'onChangeMovedFrom',
			'click .save': 'onSave',
			'click .actions.cancel': 'onCancel'
		},

		initialize: function() {
			var self = this;

			_.bindAll(this);

			this.model = new HospitalBed();
			this.model.appealId = this.options.appeal.get("id");
			this.model.set({
				"clientId": this.options.appeal.get("patient").get("id")
			});
			this.model.on("change", this.validate, this);
            this.model.on("change:bedId", function(){
                var bedId = this.model.get('bedId');
                var bed = this.bedsCollection.find(function(model){
                    return bedId == model.get('bedId');
                });
                if(bed){
                    var profileId = bed.get('profileId'); 
                    console.log('change:bedId', bed,profileId);
                    this.model.set('bedProfileId', profileId);
                }else{
                    this.model.set('bedProfileId',''); 
                }

            }, this);

            this.model.on("change:bedProfileId", function(model, bedProfileId){
                console.log('change:bedProfileId', model, bedProfileId)
                this.ui.$bedProfiles.select2('val',bedProfileId); 
            }, this);

			this.bedsCollection = new Beds();
			this.bedsCollection.on('reset', this.renderBeds, this);


			this.departments = new Departments();
			this.departments.setParams({
				filter: {
					hasBeds: true
				},
				limit: 0,
				sortingField: 'name',
				sortingMethod: 'asc'
			});

            this.bedProfiles = new BedProfilesDir();


			this.moves = new Moves();
			this.moves.appealId = this.options.appeal.get("id");


			$.when(this.departments.fetch(),this.bedProfiles.fetch(), this.moves.fetch()).done(function() {

				self.setMoveDateAndMovedDepartment();

				self.render();

				self.loadBeds(self.getLastMoveDepartment());

			});

		},

		getMoveNumber: function() {
			return this.moves.length;
		},

		getLastMoveDepartment: function(){
			return this.moves.last().get("unitId");
		},

		getMoveDatetime: function(){

		},

		setMoveDateAndMovedDepartment: function() {

			this.moves.off(null, null, this);

			this.previousMove = this.moves.at(this.moves.length - 2);
			this.lastMove = this.moves.last();

			//время предпоследнего движения, если нет в последнем то берём из предидущего движения
			var moveDatetime = this.lastMove.get('admission') || this.previousMove.get('leave') || this.previousMove.get('admission');
			console.log(this.getMoveNumber())
			if (this.getMoveNumber() === 2) {//Для первого движения по умолчанию не передаём отделение
				this.model.set({
					'movedFromUnitId': ''
				});
			} else {
				if (this.previousMove && this.previousMove.get("unitId")) {
					this.model.set({
						'movedFromUnitId': this.previousMove.get("unitId")
					});
				}
			}

			this.model.set({
				'moveDatetime': moveDatetime
			});

		},

		onChangeMovedFrom: function(event) {
			var $target = this.$(event.target);

			if ($target.prop('checked')) {
				this.model.set({
					'movedFromUnitId': 26//дневное отделение
				});
			} else {
				this.model.set({
					'movedFromUnitId': ''//this.previousMove.get("unitId")
				});
			}

		},

		onSelectDepartment: function(event) {
			var $target = this.$(event.target);
			var departmentId = $target.val();

			if (departmentId) {
				this.loadBeds(departmentId);
			}

			this.model.set('bedId', '');
            this.model.set('bedProfileId','');

		},

        onSelectBedProfile: function(){
			var $target = this.ui.$bedProfiles;//$(event.target);
            var bedProfileId = $target.val();
            //if(bedProfileId){
                this.model.set('bedProfileId',bedProfileId);
            //}
            console.log('onSelectBedProfile', $target, bedProfileId); 
        },

		loadBeds: function(departmentId){
			return this.bedsCollection.setDepartmentId(departmentId).fetch();
		},

		validate: function() {

			if (!this.model.get('bedId')||!this.model.get('bedProfileId')) {//!this.model.get('movedFromUnitId') ||
				this.disableSaveButton();
			} else {
				this.enableSaveButton();
			}

		},

		enableSaveButton: function() {
			this.ui.$saveButton.removeClass('Disabled').attr('disabled', false); //.on('click', this.onSave);
		},

		disableSaveButton: function() {
			this.ui.$saveButton.addClass('Disabled').attr('disabled', true); //.unbind('click', this.onSave);
		},

		renderBeds: function() {
			var view = this;

			view.ui.$beds.html('');

			view.bedsCollection.each(function(model) {
				var bedView = new BedView({
					model: model,
					hb: view.model
				});

				view.ui.$beds.append(bedView.render().el);

			});

			view.justifyBeds();

		},

		justifyBeds: function() {
			var maxWidth = Math.max.apply(Math, this.$('.bedBox').map(function() {
				return $(this).width();
			}).get());
			this.$('.bedBox').each(function() {
				$(this).width(maxWidth);
			})
		},

		redirectToMoves: function() {
			var view = this;
			view.trigger("change:viewState", {
				type: 'moves',
				options: {}
			});
			App.Router.updateUrl("appeals/" + view.model.appealId + "/moves/");
			this.close();

		},

		onSave: function() {
			var view = this;
			//console.log('onSave view.model', view.model);

			if (!view.model.get("moveDatetime")) {
				view.model.set({
					"moveDatetime": new Date().getTime()
				});
			}


			view.model.save({}, {
				success: function() {
					pubsub.trigger('noty', {
						text: 'Пациент зарегистрирован на койке'
					});

					view.redirectToMoves();
				}
			});


		},
		onCancel: function(e) {
			var view = this;
			e.preventDefault();
			view.redirectToMoves();
		},
		close: function() {
			this.off();
			this.model.off();
			this.moves.off();
			this.departments.off();
			this.bedsCollection.off();
		},
		data: function() {
			var data = {
				model: this.model.toJSON(),
				moveNumber: this.getMoveNumber(),
				departments: this.departments.toJSON(),
                bedProfiles: this.bedProfiles.toJSON(),
				movedToUnitId: this.moves.last() ? this.moves.last().get("unitId") : ''
			};

			return data;
		},

		render: function() {
			var view = this;


			view.$el.html(_.template(view.template, view.data()));

			view.ui = {};
			view.ui.$saveButton = view.$el.find('.save');
			view.ui.$department = view.$el.find('.Departments');
            view.ui.$bedProfiles = view.$el.find('.bed-profiles')
			view.ui.$beds = view.$el.find('.beds');


			UIInitialize(view.el);
			view.disableSaveButton();

			//view.model.connect("movedFromUnitId", "departments", view.$el);
			view.model.connect("patronage", "patronage", view.$el);
			view.model.connect("moveDatetime", view.$("#move-date"), view.$el);
			view.model.connect("moveDatetime", view.$("#move-time"), view.$el);


			view.$(".HourPicker").mask("99:99");
			view.$(".Departments").width("100%").select2();
            view.ui.$bedProfiles.select2();

			view.delegateEvents();

			return view;

		}
	});
});
