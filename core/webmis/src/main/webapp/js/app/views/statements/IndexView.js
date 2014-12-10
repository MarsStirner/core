define(function(require) {
	var template = require('text!templates/statements/index.html');

	return View.extend({
		className: 'ContentHolder',
		initialize: function() {
			this.period = 'd';
			this.statement = 2;

		},
		template: template,
		events: {
			'click [data-period]': 'onClickFilter',
			'click .st': 'onClickStatment'
		},
		onClickFilter: function(e) {
			e.preventDefault();
			var $target = this.$(e.target);

			this.$('li').removeClass('Selected');
			$target.parents('li').addClass('Selected');
			this.period = $target.data('period');
			if (this.period) {
				this.loadHtml();
			}

		},
		onClickStatment: function(e) {
			e.preventDefault();
			var $target = this.$(e.target);

			this.$('li').removeClass('Active');
			$target.parents('li').addClass('Active');
			console.log($target)
			this.statement = $target.parents('li').data('statement');
			if (this.statement) {
				this.loadHtml();
			}
		},

		loadHtml: function() {
			console.log(this.period, this.statement);

			$.get('/js/app/fixtures/table' + this.statement + this.period + '.html', function(data) {
				$(".ContentSide").html(data);

			});
		},
		render: function() {
			this.$el.html(_.template(this.template));
			this.loadHtml();
			return this;
		}
	});

});
