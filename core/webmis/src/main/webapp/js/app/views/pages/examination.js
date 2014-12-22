define( function ()
{
	App.Views.Examination = View.extend({
		initialize: function () {
			this.clearAll();

			this.on("template:loaded", this.ready, this);
			this.loadTemplate("pages/examination");
		},

		ready: function () {
			this.$el.append( $("#examination-page").html() );

			var ExaminationFormView = new ExaminationForm({
				id: this.options.id
			});

			this.$(".ContentHolder").append(ExaminationFormView.render().el);
		}
	});


	var ExaminationForm = DynamicView.extend({
		url: function () {
			return DATA_PATH +"examinations/"+ this.options.id +"/"
		}
	});
	return App.Views.Examination
} );