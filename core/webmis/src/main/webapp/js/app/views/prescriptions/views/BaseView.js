define(function(require) {

	var ViewBase = Backbone.View.extend({
		constructor: function() {
			this._subViews = {};
			this.render = _.wrap(this.render, function(render) {
				render.apply(this);
				this.afterRender.apply(this);
                return this;
			});

			Backbone.View.apply(this, arguments);
		},

		template: _.template(''),

		data: function() {
			return {};
		},

		addSubViews: function (obj) {
			this._subViews = _.extend(this._subViews, obj);
		},

		renderSubViews: function() {
			_.each(this._subViews, function(view, selector) {
				if(view){
                    view.setElement(this.$(selector)).render();
				}
			}, this);
		},

		render: function() {
			//console.log('render', this.cid)
			this.$el.html(_.template(this.template, this.data(),{variable: 'data'}));
			this.renderSubViews();

			return this;
		},

		afterRender: function() {

		},

		tearDown: function() {
            console.log('tearDown')
			this.tearDownSubviews();
			this.stopListening();
			this.undelegateEvents();
			this.remove();
		},

		tearDownSubviews: function() {
			_.invoke(this._subViews, 'tearDown');
		}

	});

	return ViewBase;

});
