define( function () {
	// Базовый View для списков групп полей
	App.Views.AbstractLine = View.extend( {
		className: "DoubleLineBlock",

		events: {
			"click .Actions.Clone": "clone",
			"click .Actions.Remove": "remove"
		},
		clone: function ()
		{

		},
		doClone: function (model, view, options) {
			// Отличим переданный экземпляр от ссылки на класс
			model = _.isFunction( model ) ? new model : model;
			this.collection.add( model );

			var params = {
				model: model,
				collection: this.collection
			};

			if (options) {
				_.each(options, function (value, key) {
					params[key] = _.clone(value);
				});
			}

			var clonedView = new view (params);
			this.depended(clonedView);

			clonedView.render().$el.insertAfter( this.el );

			return {
				model: model,
				view: clonedView
			}
		},
		remove: function ()
		{
			this.collection.remove( this.model );
			this.$el.remove();
		},
		initialize: function ()
		{
			this.collection.on( "add", this.addedEvent, this );
			this.collection.on( "remove", this.removedEvent, this );
		},
		addedEvent: function ( model, collection ) {
			var view = this;

			if ( collection.length > 1 )
			{
				this.removingButtonShow();
			}
		},
		removedEvent: function ( model, collection ) {
			var view = this;

			if ( collection.length <= 1 )
			{
				this.removingButtonHide();
			}
		},
		renderTemplate: function ( templateId, collection, options )
		{
			collection = collection || this.collection;
			this.$el.html( $( templateId ).tmpl(options || {}) );
			UIInitialize( this.el );

			// Скроем кнопку удаления
			if ( collection.length <= 1 )
			{
				this.removingButtonHide();
			}
		},

		removingButtonShow: function (){
			this.$el.find( ".Actions.Remove" ).show();
		},
		removingButtonHide: function ()
		{
			this.$el.find( ".Actions.Remove" ).hide();
		},

		/**
		 * @param targets array of objects,
		 * example:
		 * [{
		 * 	selector: "#targetid",
		 * 	triggers: [{selector: "#otherid", events: "change"}],
		 * 	toolTip: {selector:"#tooltipid", position:"right[left,top,bottom]"},
		 * 	validator: function () { return true; } // custom validator, returns boolean, if undefined - models "validate" method using
		 * }
		 * [, ...]]
		 */
		setValidationTargets: function (targets) {
			var self = this;

			self.$(".ToolTip").hide();

			_.each(targets, function (target) {
				var $target = self.$(target.selector);
				//console.log($target);

				_.each(target.triggers, function (trigger) {
					var $trigger = self.$(trigger.selector);
					//console.log($trigger);

					$trigger.on(trigger.events, function () {
						var valid;
						if (target.validator) {
							valid = target.validator();
						} else {
							valid = self.model.isValid();
							console.log(valid);
						}

						if (!valid) {
							self._setToolTipPosition(target.toolTip.selector, target.toolTip.position, $target).show();
							if ($target.is("select"))
								$target.next().addClass("WrongField Mandatory invalid");
							else
								$target.addClass("WrongField Mandatory invalid");
						} else {
							self.$(target.toolTip.selector).hide();
							if ($target.is("select"))
								$target.next().removeClass("WrongField Mandatory invalid");
							else
								$target.removeClass("WrongField Mandatory invalid");
						}
					});
				});
			});
		},

		_setToolTipPosition: function (sel, pos, $target) {
			if ($target.is("select"))
				$target = $target.next();

			var $tip = this.$(sel);
			var p = $target.position();
			var x, y;

			console.log($target.offset(), $target.width(), $target.height());

			if (pos === "right") {
				$tip.addClass("LeftTail");
				x = p.left + $target.outerWidth(true) + 10;
				y = p.top - $target.height()/2;
			} else if (pos === "top") {
				x = p.left - ($target.outerWidth(true) + ($target.outerWidth(true) - $tip.outerWidth(true)))/2;
				y = p.top - $target.outerHeight(true) - $tip.outerHeight(true);
				$tip.addClass("BottomTail");
			} else if (pos === "left") {
				$tip.addClass("RightTail");
				x = p.left - $target.outerWidth(true) - ($tip.outerWidth(true) - $target.outerWidth(true)) -10;
				y = p.top - $target.height()/2;
			} else if (pos === "bottom") {
				$tip.addClass("TopTail");
				x = p.left - ($target.outerWidth(true) + ($target.outerWidth(true) - $tip.outerWidth(true)))/2;
				y = p.top + $target.outerHeight(true) + 10;
			}

			$tip.css('position', 'absolute').css('z-index', '999').css('left', x + 'px').css('top', y + 'px');

			return $tip;
		}
	} );
});