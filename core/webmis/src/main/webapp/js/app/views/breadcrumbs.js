define([], function ()
{
	App.Views.Breadcrumbs = View.extend({
		tagName: "ul",
		className: "BreadCrumb",
		initialize: function () {
			this.on ("template:loaded", this.ready, this);
			this.loadTemplate("breadcrumbs");
		},
		setStructure: function ( structure ) {
			this.options.structure = structure;
			this.render();
			this.ready();
		},
		ready: function () {
			this.templateLoadComplete = true;

			this.clear();
			this.$el.empty();

			if ( !this.options.structure ) {
				return false
			}

			var view = this;

			_.each(this.options.structure, function(element, index){
				var Item = new BreadcrumbsItem ({
					structure: element,
					active: view.options.structure.length -1 == index
				});
				view.depended(Item);

				view.$el.append(Item.render().el);
			})
		},
		render: function () {

			return this
		}
	});

	var BreadcrumbsItem = View.extend({
		tagName: "li",

		render: function () {
			if ( this.options.active ) {
				this.$el.html($("#breadcrumbs-item-active" ).tmpl(this.options.structure));
			}else {
				this.$el.html($("#breadcrumbs-item" ).tmpl(this.options.structure));
			}

			return this
		}
	});

	return App.Views.Breadcrumbs;
} );