define([], function() {

	var SelectView = View.extend({

		selectText: 'value',

		initialize: function() {
			var view = this;
			view.collection = view.options.collection;
			view.collection.on("reset", view.render, view);
			view.collection.fetch();
			if (this.options.selectText) {
				this.selectText = this.options.selectText;
			}
		},

		val: function(value) {
			var view = this;
			if (value) {
				view.$el.select2('val', value);
			} else {
				return view.$el.select2('val');
			}
		},
		onChange: function() {
			var view = this;
			//pubsub.trigger('select:change', view.select2.val())
		},
		render: function() {
			var view = this;
			var id = view.$el.prop('id');
			/**
			 * byString(someObj, 'part.name');
			 * возвращает свойство объекта по пути указонному в строке
			 *
			 * @param   {object}  o
			 * @param   {string}  s
			 *
			 * @return  {[type]}  [description]
			 */
			function byString(o, s) {
				s = s.replace(/\[(\w+)\]/g, '.$1'); // convert indexes to properties
				s = s.replace(/^\./, ''); // strip a leading dot
				var a = s.split('.');
				while (a.length) {
					var n = a.shift();
					if (n in o) {
						o = o[n];
					} else {
						return;
					}
				}
				return o;
			}

			_(view.collection.toJSON()).each(function(item) {

				view.$el.append($("<option/>", {
					"text": byString(item,view.selectText),
					"value": item.id
				}));
			}, view);

			view.select2 = view.$el.select2({
				allowClear: true
			});

			if (view.options.initSelection) {
				view.val(view.options.initSelection)
			}


			// view.select2.on('change', function () {
			// 	pubsub.trigger(id + ':change', view.select2.val());
			// });


			return view;
		},
		cleanUp: function() {
			this.collection.off(null, null, this);
		},
		close: function(){
			this.collection.off();

		}

	});

	return SelectView;

});
