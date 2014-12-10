//окошко с деревом групп лабораторных исследований
define(function(require) {
	var template = require('text!templates/diagnostics/laboratory/direction-analyzes-tree.html');

	var AnalyzesTreeView = View.extend({
		initialize: function() {
			var view = this;

			pubsub.on('lab:click', function(labCode) {
				view.$el.html('');
				view.collection.fetch({
					data: {
						'filter[code]': 2,
						sortingField: "name",
						sortingMethod: "asc"
					}
				});
			});

			view.collection.on('reset', function() {
                // console.log('reset tree collection', view.collection);
				view.render();
                // $('#tree-search').prop('disabled', false).removeClass('Disabled');
			});

			view.collection.on('search', function() {
				view.$el.html('<div class="msg">Поиск...</div>');
				//console.log('fetch')
			});

			view.collection.on('fetch', function() {
				view.$el.html('<div class="msg">Загрузка...</div>');
				//console.log('fetch')
			});

		},
		renderAll: function(treeData) {
			var view = this;

			view.$el.html('<div class="tree"></div>');
			view.$analyzesList = view.$('.tree');

			view.$analyzesList.append(_.template(template, {
				items: treeData,
				template: template
			})).unbind().on('click', 'li', function(event) {
				event.stopPropagation();
				var $this = $(this);
				var code = $this.data('code');

				$this.siblings().removeClass('open').addClass('closed');
				$this.toggleClass('open').toggleClass('closed').addClass('clicked');

				view.$analyzesList.find('.clicked').removeClass('clicked');
				$this.addClass('clicked');

				if (!$this.hasClass('parent')) {
					pubsub.trigger('analysis:click', code);
				}

				var code = $(this).data('code');
				//console.log('code', code, treeData);
			});
		},
		renderNoResult: function() {
			var view = this;
			view.$el.html('<div class="msg">Нет результатов</div>');
		},

		render: function() {
			var view = this;
			var treeData = view.collection.toJSON();
			//console.log('tree', treeData);
			if (treeData.length > 0) {
				view.renderAll(treeData);
			} else {
				view.renderNoResult();
			}



			return this;
		},
		close: function() {
            console.log('close tree view')
			pubsub.off('lab:click');
			this.collection.off();
			this.$el.remove();
			this.remove();
		}

	});


	return AnalyzesTreeView;

});
