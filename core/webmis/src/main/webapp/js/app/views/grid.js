define(["views/grid-row"], function () {
	App.Views.Grid = View.extend({

		events: {
			"click .Actions.SortCol": "sort",
			"change .Filter select, .Filter input": "filter",
			"keyup .Filter input": "filter"
		},

		initialize: function () {
			checkForErrors(this.options.gridTemplateId, "gridTemplateId is mandatory");
			checkForErrors(this.options.rowTemplateId, "rowTemplateId is mandatory");

			if (this.options.template) {
				this.queue([
					{event: "template:loaded", context: this},
					{event: "reset", context: this.collection}
				], this.ready, this);

				this.loadTemplate(this.options.template);
				this.collection.on("reset", this.refresh, this);
				this.collection.on("reset", this.showDefaultSorting, this);

				this.collection.on("fetch", this.onFetch, this);
			}

		},

		sort: function (event) {
			var collection = this.collection;

			var $target = $(event.currentTarget),
				$th = $target.closest("th");

			if ($th.hasClass("Active")) {
				$th.toggleClass("Desc");
			}
			else {
				$th.siblings().removeClass("Active Desc");
				$th.addClass("Active");
			}

			this.collection.setParams({
				sortingField: $target.data("field"),
				sortingMethod: $th.hasClass("Desc") ? "desc" : "asc"
			});

			this.collection.fetch();
		},

		//шаблон показываемый во время загрузки данных
		onFetch: function () {
			var view = this;

			if (this.options.fetchTemplateId) {
				var $tbody = view.$el.find("tbody").empty();

				var GridRow = new App.Views.GridRow({
					collection: view.collection,
					rowTemplateId: view.options.fetchTemplateId
				});

				view.depended(GridRow);
				$tbody.append(GridRow.render().el);
			}

		},

		//показ сортировки по умолчанию
		showDefaultSorting: function () {
			var view = this;

			if (view.collection.requestData && view.collection.requestData.sortingField && view.collection.requestData.sortingMethod) {

				var sortingMethod = view.collection.requestData.sortingMethod;

				var $sortingField = view.$el.find("[data-field='" + view.collection.requestData.sortingField + "']");

				if ($sortingField) {
					var $th = $sortingField.parent('th');

					$th.addClass('Active');

					if (sortingMethod == 'desc') {
						$th.addClass('Desc');
					}
				}
			}

		},

		filter: function (event) {
			var view = this;
			var $input = $(event.currentTarget);


			if (this._timeout) {
				clearTimeout(this._timeout);
			}

			var timeout,
				DELAY = 700;

			if ($input.data("old-value") && $input.data("old-value") == $input.val()) {
				clearTimeout(this._timeout);
				return;
			}
			$input.data("old-value", $input.val());

			this._timeout = setTimeout(function () {

				var filter = Core.Forms.serializeToObject(view.$(".Filter input, .Filter select"));
				view.collection.setParams({
					filter: filter
				});

				if (_.size(filter)) {
					view.collection.fetch();
				} else {
					view.collection.reset();
				}
			}, DELAY);
		},

		templateLoaded: function () {
			if (this.options.defaultTemplateId) {
				this.$el.html($.tmpl($(this.options.defaultTemplateId)));
			}
		},

		refresh: function () {
			var view = this,
				$el = this.$el;


			var $tbody = $el.find("tbody").empty();

			var total = view.collection.length;
			//console.log(total);

			if (total) {

				view.collection.forEach(function (model, i) {
					var _index = i;
					var rowTemplateId = view.options.rowTemplateId;

					//console.log(i);
					if (view.options.lastRowTemplateId && (i == total - 1) && (i > 0)) {
						rowTemplateId = view.options.lastRowTemplateId;
					}

					if (view.collection.requestData && view.collection.requestData.page > 1) {
						_index += (view.collection.requestData.page - 1) * view.collection.requestData.limit;
					}

					var GridRow = new App.Views.GridRow({
						model: model,
						collection: view.collection,
						rowTemplateId: rowTemplateId, // Передаём строке таблицы ID шаблона в файле шаблонов
						triggerOnly: view.options.popUpMode,
						_index: _index
					});
					view.depended(GridRow);

					GridRow.on("row:click", function (model, event) {
						view.trigger("grid:rowClick", model, event);
					}, this);
					GridRow.on("row:dbclick", function (model, event) {
						view.trigger("grid:rowDbClick", model, event);
					}, this);

					$tbody.append(GridRow.render().el);
				});
			}
			else if (view.options.errorTemplateId) {
				var GridRow = new App.Views.GridRow({
					collection: view.collection,
					rowTemplateId: view.options.errorTemplateId
				});
				view.depended(GridRow);
				$tbody.append(GridRow.render().el);
			}

			UIInitialize(this.el);
		},
		ready: function () {

			var view = this,
				$el = this.$el;

			$el.html($(view.options.gridTemplateId).tmpl(view.collection.requestData));
		},


		render: function () {
			this.$el.empty();

			return this;
		}
	});

	return App.Views.Grid;
});
