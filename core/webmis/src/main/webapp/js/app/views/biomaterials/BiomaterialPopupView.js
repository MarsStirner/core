define(function(require) {

	var biomaterialPopupTmpl = require('text!templates/biomaterials/biomaterial-popup.tmpl');

	var BiomaterialPopup = View.extend({
		template: biomaterialPopupTmpl,
		className: "popup",

		events: {
			"click .ShowHidePopup": "close",
			'change .lab_test_id': 'recalculateAmount'
		},

		initialize: function() {
			var popup = this;
			popup.biomaterial = popup.options.biomaterial;
		},

		recalculateAmount: function() {
			var popup = this;
			console.log('recalculateAmount', popup.biomaterial);
		},

		render: function() {
			var popup = this;

			popup.$el.html($.tmpl(popup.template, popup.biomaterial.toJSON()));

			$("body").append(popup.$el);

			$(popup.$el).dialog({
				autoOpen: false,
				width: "116em",
				modal: true,
				dialogClass: "webmis",
				title: "Перечень лабораторных исследований"
			});

			return popup;
		},

		open: function() {
			//$(".ui-dialog-titlebar").hide();
			this.$el.dialog("open");
		},

		close: function() {
			//$(".ui-dialog-titlebar").show();
			this.$el.dialog("close");
		}
	});

	return BiomaterialPopup;
});
