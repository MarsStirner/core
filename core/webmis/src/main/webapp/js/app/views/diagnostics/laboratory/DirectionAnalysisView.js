define(function(require) {
	var analysisTemplate = require('text!templates/diagnostics/laboratory/direction-analysis.html');
	var Analysis = require('models/diagnostics/laboratory/Analysis');

	var AnalysisView = Backbone.View.extend({
		tagName: 'tr',
		events: {
			'change .select_date': 'onChangePlannedEndDate',
			'blur .select_date': 'onBlurPlannedEndDate',
			'change .select_time': 'onChangePlannedEndDate',
			'change .cito': 'onChangeCito',
			'change .picked': 'onChangePicked',
			'change .test-select': 'onSelectTest',
			'click .icon': 'onClickArrow',
			'click .title2': 'onClickTitle'
		},
		initialize: function(options) {
			//console.log('options', options);
			this.$el.attr('data-code', this.model.get('code'));
			this.$el.attr('data-cid', this.model.cid);
			this.$el.addClass('context-menu-' + this.cid);

			this.model.on('change:plannedEndDate', function(model, value) {
				this.render();
			}, this);
			this.model.on('change', function(model, value) {
				console.log('coll', this.model.collection.toJSON())
				console.log('change', model, value);
			}, this);

		},
		onBlurPlannedEndDate: function(){
			//console.log('onBlurPlannedEndDate')
			this.ui.$plannedDatepicker.trigger('change');

		},

		analysisData: function() {
			// this.model.set('picked', true)
			var urgent = (this.model.getProperty('urgent', 'value') == 'true') ? true : false;

			var date, time;

			if (this.model.getProperty('plannedEndDate', 'value')) {
				var plannedEndDate = moment(this.model.getProperty('plannedEndDate', 'value'), 'YYYY-MM-DD HH:mm:ss');
				date = plannedEndDate.format('DD.MM.YYYY');
				time = plannedEndDate.format('HH:mm');
			} else {
				//var now = new Date()
				date = ''; //moment(now).add('days', 1).format('DD.MM.YYYY');
				time = ''; //'07:00';
				//this.model.setProperty('plannedEndDate', 'value', moment(now).format('YYYY-MM-DD HH:mm:ss'))
			}


			var data = _.extend(this.model.toJSON(), {

				cid: this.model.cid,
				date: date,
				time: time,
				urgent: urgent,
				model: this.model,
				tests: this.model.getTests()
			});

			return data;
		},

		onSelectTest: function(event) {
			var $target = $(event.target);
			var name = $target.val();
			var value = $target.prop('checked');

			this.model.setProperty(name, 'isAssigned', "" + value);
		},

		onChangeCito: function() {
			var value = this.ui.$cito.prop('checked');

			this.model.setProperty('urgent', 'value', "" + value);

		},

		onChangePicked: function() {
			var value = this.ui.$picked.prop('checked');

			this.model.set('picked', value);
		},

		onChangePlannedEndDate: function() {
			var view = this;
			var rawDate = this.ui.$plannedDatepicker.val();
			if(!rawDate) return;

			var rawTime = this.ui.$plannedTimepicker.val();
			//console.log(rawDate, rawTime)


			var date = moment(rawDate, 'DD.MM.YYYY').format('YYYY-MM-DD');
			// if(!rawTime){
			// 	console.log(date.)

			// }
			rawTime = rawTime ? rawTime : '00:00';


			var time = rawTime + ':00';
			var datetime = date + ' ' + time;

			view.model.setProperty('plannedEndDate', 'value', datetime);

			if (view.model.get('picked')) {
				//если анализ "выбран", то его дату ставим всем выбранным анализам
				var picked = view.model.collection.getPicked()
				_.each(picked, function(pickedModel) {
					///console.log('picked', pickedModel.get('name'));
					pickedModel.setProperty('plannedEndDate', 'value', datetime);
				});
			}

		},

		onClickTitle: function() {
			var closed = this.$el.find('.icons').hasClass('closed');
			var open = this.$el.find('.icons').hasClass('open');

			if (closed || open) {
				if (closed) {
					this.expand();
				} else {
					this.collapse();
				}
			}

		},

		onClickArrow: function(e) {
			var $target = $(e.target);

			if ($target.hasClass('icon-open')) {
				this.collapse();
			} else {
				this.expand();
			}

		},

		triggerIcons: function(select) {
			if (select) {
				this.ui.$icons.removeClass('closed').addClass('open');
			} else {
				this.ui.$icons.removeClass('open').addClass('closed');
			}
		},

		triggerTestsList: function(select) {
			if (select) {
				this.ui.$tests.show();
			} else {
				this.ui.$tests.hide();
			}
		},

		expand: function() {
			this.triggerTestsList(true);
			this.triggerIcons(true);
		},

		collapse: function() {
			this.triggerTestsList(false);
			this.triggerIcons(false);
		},

		render: function() {
			var view = this;

			console.log(' this.analysisData()', this.analysisData());

			this.$el.html(_.template(analysisTemplate, this.analysisData(), {
				variable: 'data'
			}));


			view.ui = {};

			view.ui.$plannedDatepicker = view.$el.find(".select_date");
			view.ui.$plannedTimepicker = view.$el.find(".select_time");

			view.ui.$cito = view.$el.find(".cito");
			this.ui.$picked = view.$el.find(".picked");
			// view.ui.$select = view.$el.find(".select");
			view.ui.$tests = view.$el.find(".tests");
			view.ui.$icons = view.$el.find(".icons");


			view.ui.$plannedDatepicker.mask("99.99.9999");
			view.ui.$plannedTimepicker.mask("99:99");

			view.ui.$plannedDatepicker.datepicker({
				minDate: new Date(),
				onSelect: function(dateText, inst) {
					var date = view.$(this).datepicker("getDate");
					date = date ? date : new Date;
					var currentDay = moment().startOf('day');
					var currentHour = moment().hour();

					var day = moment(date).startOf('day');
					var hour = view.ui.$plannedTimepicker.timepicker('getHour');
					//если выбрана текущая дата и время в таймпикере меньше текущего, то сбрасываем таймпикер
					if (day.diff(currentDay, 'days') === 0) {
						if (hour <= currentHour) {
							view.ui.$plannedTimepicker.val('').trigger('change');
						}
					}

					$(this).change();
				}
			});

			view.ui.$plannedDatepicker.next('.icon-calendar').on('click', function(){
				view.ui.$plannedDatepicker.datepicker('show');
			})

			view.ui.$plannedTimepicker.timepicker({
				defaultTime: 'now',
				onHourShow: function(hour) {
					var date = view.ui.$plannedDatepicker.datepicker("getDate");
					date = date ? date : new Date;
					var currentDay = moment().startOf('day');
					var currentHour = moment().hour();
					var day = moment(date).startOf('day');
					//если выбран текущий день, то часы меньше текущего нельзя выбрать
					if (day.diff(currentDay, 'days') === 0) {
						if (hour < currentHour) {
							return false;
						}
					}

					return true;
				},
				onMinuteShow: function(hour, minute) {
					var date = view.ui.$plannedDatepicker.datepicker("getDate");
					date = date ? date : new Date;
					var currentDay = moment().startOf('day');
					var currentHour = moment().hour();
					var day = moment(date).startOf('day');
					var currentMinute = moment().minute();
					//если выбран текущий день и час, то минуты меньше текущего времени нельзя выбрать
					if (day.diff(currentDay, 'days') === 0) {
						if (hour === currentHour && minute <= currentMinute) {
							return false;
						}
					}
					return true;
				},
				showPeriodLabels: false,
				showOn: 'both'
			});

			$.contextMenu({
				autoHide: true,
				selector: '.context-menu-' + view.cid,
				callback: function(key, options) {
					//var m = "clicked: " + key + " " + options.$trigger.data("cid");

					//console.log(arguments, options.$trigger.data("cid"));
				},
				items: {
					"select": {
						name: "Выбрать все тесты",
						callback: function() {
							$('.context-menu-' + view.cid + ' .tests ')
								.find('input:checkbox').prop('checked', true)
								.trigger('change');
						}
					},
					"deselect": {
						name: "Снять выделение со всех тестов",
						callback: function() {
							$('.context-menu-' + view.cid + ' .tests ')
								.find('input:checkbox').prop('checked', false)
								.trigger('change');
						}
					},
					"delete": {
						name: "Удалить исследование из списка",
						callback: function() {
							view.model.collection.remove(view.model);
						}
					},
					"delete all": {
						name: "Удалить все исследования",
						callback: function() {
							view.model.collection.reset();
						}
					}
				}
			});


			return this;
		},

		close: function() {
			this.$el.remove();
			this.remove();
		}
	});

	return AnalysisView;

});
