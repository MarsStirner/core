define(function(require) {

	var bedTemplate = require('text!templates/moves/bed.tmpl');
	Bed = View.extend({
		className: "item",
		template: _.template(bedTemplate),

		events: {
			"click .bedBox": "onBedClick"
		},

		initialize: function() {
			this.on('bedChecked', function(bedId) {
				this.options.hb.set({
					'bedId': bedId
				});
			}, this);
		},


		onBedClick: function(event) {

			var beds = $('.bedBox');
			var bed = this.$(event.currentTarget);

			if (bed.hasClass('disabledBed')) {
				return false;
			}

			var checkbox = bed.find(".bedCheckbox");

			if (!checkbox.prop('checked')) {
				bed.removeClass('checkedBed');
				checkbox.removeClass("DoneIcon").removeAttr("checked");
				this.trigger('bedChecked', '');
			} else {
				beds.removeClass('checkedBed');
				beds.find(".bedCheckbox").removeClass("DoneIcon").removeAttr("checked");
				bed.addClass('checkedBed');
				checkbox.addClass("DoneIcon").attr("checked", "checked");
				this.trigger('bedChecked', checkbox.val());
			}

		},

		render: function() {
			this.$el.html(this.template(this.model.toJSON()));

			return this;
		}
	});

	return Bed;
});
