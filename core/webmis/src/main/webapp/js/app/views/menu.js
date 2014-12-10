App.Views.Menu = View.extend({
	tagName: "nav",

	events: {
		"click a": "changePage"
	},

	currentPage: "",

	initialize: function() {
		checkForErrors (this.options.structure, "structure is mandatory");

		this.allowLinkExec = this.options.allowLinkExec;

		_.extend(this.options.structure, Backbone.Events);

		this.on ("template:loaded", this.ready, this);

		this.loadTemplate("menu");
	},
	items: [],

	changePage: function(event) {
		if (!this.allowLinkExec) {
			event.preventDefault();
			// Передадим вместе с событием, текущий name, чтобы другие вью, которые подписаны на событие, знали,
			// какой конкретно пункт меню был выбран
			var structure = $(event.currentTarget).data("structure");
			this.options.structure.trigger("change-page", structure);

			App.Router.updateUrl(structure.uri);
		}
	},

	setPage: function(pageName) {
		var view = this;
		view.currentPage = pageName;

		_.each(view.items, function(item){
			item.setUnactive();

			if ( item.options.structure.name == view.currentPage ) {
				item.setActive();
			}
		});
	},
	ready: function () {
		var view = this;

		var MenuList = new App.Views.MenuList({
			structure: this.options.structure,
			menuView: this
		});
		this.depended(MenuList);

		this.$el.html( MenuList.render().el );

		this.setPage(this.currentPage);
	}
});

App.Views.MenuList = View.extend({
	tagName: "ul",
	className: "AsideNav",

	render: function () {
		var view = this;

		_.each(this.options.structure, function(element) {
			var MenuItem = new App.Views.MenuItem ({
				structure: element,
				menuView: view.options.menuView
			});
			view.depended(MenuItem);

			view.$el.append(MenuItem.render().el);
		});

		return this
	}
});

App.Views.MenuItem = View.extend({
	tagName: "li",

	setActive: function () {
		this.$el.addClass("Active");
	},
	setUnactive: function () {
		this.$el.removeClass("Active");
	},

	render: function (){
		if ( this.options.structure.active ) {
			this.setActive();
		}

		this.$el.html( $("#menu-item").tmpl (this.options.structure) );
		// Запомним, к какому пункту меню относится этот элемент, чтобы при клике его опознать
		this.$el.find("a").data("structure", this.options.structure);

		// Добавим каждый пункт в общую вьюху, чтобы иметь возможность переключать их состояние
		this.options.menuView.items.push(this);

		if ( this.options.structure.structure )
		{
			var MenuList = new App.Views.MenuList({
				structure: this.options.structure.structure,
				menuView: this.options.menuView
			});

			this.depended (MenuList);
			this.$el.append(MenuList.render().el);
		}

		return this
	}
});
