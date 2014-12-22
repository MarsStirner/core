//окошко с выбранными лабороторными исследованиями
define(function(require) {

	var analyzesListTemplate = require('text!templates/diagnostics/laboratory/direction-analyzes-selected.html');

	var AnalysisView = require('views/diagnostics/laboratory/DirectionAnalysisView');
	var Analysis = require('models/diagnostics/laboratory/Analysis');

	var AnalyzesSelectedView = View.extend({
		el: 'ul',
		initialize: function() {
			var view = this;

			view.analyzes = view.collection;

			view.analyzes.on('reset add remove', function() {
				view.render();
			});

			pubsub.on('analysis:click', function(code) {
				console.log('analysis:click', code);
				//debugger;
				var analysis = new Analysis([], {
					code: code,
					patientId: view.options.patientId
				});

				analysis.fetch().done(function() {
					//текуший день, время округляем текущее до ближайшего часа, но если 23:30, то 23:59 надо ....
					// var now = moment(new Date());

					// var date = now.endOf("hour").seconds(0);//округляем текущее время до 59:00

					// if(date.hour() != 23){//если не 23 часа, то добавляем минуту
					// 	date.add('minutes', 1);
					// }

					// date = date.format('YYYY-MM-DD HH:mm:ss');
					// // console.log(date)

					//analysis.setProperty('plannedEndDate', 'value', '');

					analysis.checkTests();
					view.analyzes.add([analysis]);
					view.setExecutorFromAnalysis(analysis);

				});

			});


			this.childViews = [];

		},


		setExecutorFromAnalysis: function(test) {
			if (test.getProperty('doctorFirstName', 'value')) {
				var executor = {
					id: test.getProperty('executorId', 'value'),
					name: {
						first: test.getProperty('doctorFirstName', 'value'),
						middle: test.getProperty('doctorMiddleName', 'value'),
						last: test.getProperty('doctorLastName', 'value')
					}
				}

				pubsub.trigger('executor:changed', executor);

			}
		},



		analyzesData: function() {
			var data = this.analyzes.map(function(model) {
				return _.extend(model.toJSON(), {
					cid: model.cid
				});
			}, this);

			return data;
		},

		renderAll: function(testsData) {
			var view = this;
			//console.log('renderAll', testsData, view);

			view.$el.html(_.template(analyzesListTemplate, {}));
			view.$analyzesList = view.$el.find('tbody.item-container');

			this.closeChildViews();



			this.analyzes.each(function(model) {
				//console.log('analyzes item', model);
				var analysisView = new AnalysisView({
					model: model,
					collection: model.collection,
					patientId: view.options.patientId
				});

				view.childViews.push(analysisView);

				view.$analyzesList.append(analysisView.render().el)
			}, this);



		},

		renderNoData: function() {
			this.$el.html('<div class="msg">Нет результатов.</div>');
		},
		renderOnFetch: function() {
			this.$el.html('<div class="msg">Загрузка...</div>');
		},

		render: function() {
			console.log('render as')
			var view = this;
			var analysisData = view.analyzesData();

			if (analysisData.length > 0) {
				view.renderAll(analysisData);
			} else {
				view.renderNoData();
			}

			return view;
		},
		closeChildViews: function() {
			_.each(this.childViews, function(childView) {
				childView.close();
			}, this);

			this.childViews.length = 0;
		},
		close: function() {
			this.analyzes.off();
			pubsub.off('analysis:click');
			this.closeChildViews();
			this.$el.remove();
			this.remove();

		}

	});

	return AnalyzesSelectedView;

});
