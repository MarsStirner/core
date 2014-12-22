define(["text!templates/ui/mkbInput.tmpl"], function(tmpl) {

	var MkbInput = View.extend({
		events: {
			"click .MKBLauncher": "toggleMKB",
			"keyup [name='diagnosis[mkb][code]']": "onMKBCodeKeyUp"
		},

		initialize: function() {
			var view = this;


		},

		toggleMKB: function(event) {
			event.preventDefault();

			//this.mkbAttrId = $(event.currentTarget).data("mkb-examattr-id");
			if (!this.mkbDirectory) {
				this.mkbDirectory = new App.Views.MkbDirectory();
				this.mkbDirectory.on("selectionConfirmed", this.onMKBConfirmed, this);
				this.mkbDirectory.render();

				this.depended(this.mkbDirectory);
			}

			this.mkbDirectory.open();
		},

		onMKBConfirmed: function(event) {
			var sd = event.selectedDiagnosis;
			//console.log('sd', sd.get("id"));

			//this.mkbAttrId = sd.get("id");

			this.$("input[name='diagnosis[mkb][code]']").val(sd.get("code"));
			this.$("input[name='diagnosis[mkb][diagnosis]']").val(sd.get("diagnosis"));
			this.$("input[name='diagnosis[mkb][code]']").data('mkb-id', sd.get("id")).trigger('change');
		},

		onMKBCodeKeyUp: function(event) {
			$(event.currentTarget).val(Core.Strings.toLatin($(event.currentTarget).val()));
		},

		render: function() {
			var view = this;

			view.$el.html($.tmpl(tmpl));



			var patientSex = Cache.Patient.get("sex").length ? (Cache.Patient.get("sex") == "male" ? 1 : 2) : 0;

			this.$("input[name='diagnosis[mkb][code]']").autocomplete({
				source: function(request, response) {
					$.ajax({
						url: "/data/dir/mkbs/",
						dataType: "jsonp",
						data: {
							filter: {
								view: "mkb",
								code: request.term,
								sex: patientSex
							}
						},
						success: function(raw) {
							response($.map(raw.data, function(item) {
								return {
									label: item.code + " " + item.diagnosis,
									value: item.code,
									id: item.id,
									diagnosis: item.diagnosis
								};
							}));
						}
					});
				},
				minLength: 2,
				select: function(event, ui) {
					//view.mkbAttrId = $(this).data("mkb-examattr-id");
					console.log('ui', ui)

					view.$("input[name='diagnosis[mkb][diagnosis]']").val(ui.item.diagnosis);
					view.$("input[name='diagnosis[mkb][code]']").val(ui.item.value);
					view.$("input[name='diagnosis[mkb][code]']").data('mkb-id', ui.item.id).trigger('change');
				}
			}).on("keyup", function() {
				if (!$(this).val().length) {
					view.$("input[name='diagnosis[mkb][diagnosis]']").val("");
					view.$("input[name='diagnosis[mkb][code]']").data('mkb-id', "");
				}
			});

			view.$(".MKBLauncher").button({
				icons: {
					primary: "icon-book"
				}
			});


			return view;

		},
		disable: function() {
			//console.log('disable')
			this.$(".MKBLauncher").button('disable');
			this.$("input[name='diagnosis[mkb][code]']").prop('disabled', true).addClass('Disabled NotEditable');
		},
		close: function() {
			if (this.mkbDirectory) {
				this.mkbDirectory.remove();
			}
		}

	});

	return MkbInput;
});
