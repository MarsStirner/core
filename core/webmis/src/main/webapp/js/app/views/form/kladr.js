/**
 * User: FKurilov
 * Date: 30.07.12
 */
define([
	"models/kladr",
	"text!templates/kladr/kladr-main.tmpl",
	"text!templates/kladr/kladr-field.tmpl",
	"text!templates/kladr/kladr-entries.tmpl"
], function (KLADRModel, mainTemplate, fieldTemplate, entriesTemplate) {
	var KLADRField = View.extend({
		tagName: "li",

		template: fieldTemplate,
		kladrEntriesTemplate: entriesTemplate,

		events: {
			"change select": "onEntrySelected"
		},

		initialize: function () {
			this.collection.on("reset", this.addAll, this);
			this.collection.on("fetch:start", this.showLoader, this);
		},

		render: function () {
			this.$el.html($.tmpl(this.template, {levelTitle: this.options.levelTitle, fieldName: this.options.fieldName}))
				.find("select")
				.width("100%")
				//.attr("disabled", true)
				//.chosen({contextView: this.el, no_results_text: "Нет результатов для"});
				.select2();

			//this.$(".loader").hide();

			this.delegateEvents();

			return this;
		},

		addAll: function () {
			//this.$("select[name='" + this.options.fieldName + "']")
			this.$("select")
				.html($.tmpl(this.kladrEntriesTemplate, {
					entries: this.collection.toJSON()
					//,selectedCode: this.options.selectedCode
				}))
				.select2("val", this.options.selectedCode)
				.select2(this.collection.length > 0 ? "enable" : "disable");

				//.attr("disabled", this.collection.length == 0)
				//.trigger("liszt:updated");
			//this.$(".loader").hide();

			if (this.collection.length == 0)
				this.trigger("entrySelected", {code: this.collection.getParentCode()});
		},

		clean: function () {
			this.$("select option :not(:first)").remove();
			this.$("select").select2("val", "").select2("disable");
			//.trigger("change");
			//this.$("select").trigger("change");
				//.val("")
				//.attr("disabled", true)
				//.trigger("change");
		},

		onEntrySelected: function () {
			this.trigger("entrySelected", {code: this.$("select").val()});
		},

		showLoader: function () {
			this.$("select").select2("disable");
			//this.$(".loader").show();
		}
	});

	App.Views.KLADR = View.extend({
		className: "LineCol",

		template: mainTemplate,

		events: {
			"change .kladr-toggle": "onToggle"
			/*,"keyup input[name='address[house]']": "onOtherInputChange",
			"keyup input[name='address[building]']": "onOtherInputChange",
			"keyup input[name='address[flat]']": "onOtherInputChange",
			"keyup input[name='address[fullAddress]']": "onOtherInputChange",
			"change select[name='address[localityType]']": "onOtherInputChange"*/
		},

		initialize: function () {
			this.model = new App.Models.KLADR();

			this.republicsView  = new KLADRField({
				collection: this.model.get("republics"),
				levelTitle: "Республика, край, область",
				fieldName: "address-"+this.options.addressType +"-republic-code",
				selectedCode: this.options.address.get("republic").get("code")
			});
			this.districtsView  = new KLADRField({
				collection: this.model.get("districts").setParentCode(this.options.address.get("republic").get("code")),
				levelTitle: "Район",
				fieldName: "address-"+this.options.addressType +"-district-code",
				selectedCode: this.options.address.get("district").get("code")
			});
			this.citiesView     = new KLADRField({
				collection: this.model.get("cities").setParentCode(this.options.address.get("district").get("code")),
				levelTitle: "Города, сельсоветы",
				fieldName: "address-"+this.options.addressType +"-city-code",
				selectedCode: this.options.address.get("city").get("code")
			});
			this.localitiesView = new KLADRField({
				collection: this.model.get("localities").setParentCode(this.options.address.get("city").get("code")),
				levelTitle: "Населённый пункт",
				fieldName: "address-"+this.options.addressType +"-locality-code",
				selectedCode: this.options.address.get("locality").get("code")
			});
			this.streetsView    = new KLADRField({
				collection: this.model.get("streets").setParentCode(this.options.address.get("locality").get("code")),
				levelTitle: "Улица, площадь, проспект и т.д.",
				fieldName: "address-"+this.options.addressType +"-street-code",
				selectedCode: this.options.address.get("street").get("code")
			});

			this.attachHandlers();
		},

		attachHandlers: function () {
			this.republicsView.on("entrySelected", function (e) {
				if (e.code)
					this.districtsView.collection.setParentCode(e.code).fetch();
				else
					this.districtsView.clean();
				this.citiesView.clean();
				this.localitiesView.clean();
				this.streetsView.clean();

				//this.trigger("addressChange", {key: "republic", value: e.code});
			}, this);

			this.districtsView.on("entrySelected", function (e) {
				if (e.code)
					this.citiesView.collection.setParentCode(e.code).fetch();
				else
					this.citiesView.clean();
				this.localitiesView.clean();
				this.streetsView.clean();

				//this.trigger("addressChange", {key: "district", value: e.code});
			}, this);

			this.citiesView.on("entrySelected", function (e) {
				if (e.code)
					this.localitiesView.collection.setParentCode(e.code).fetch();
				else
					this.localitiesView.clean();
				this.streetsView.clean();

				//this.trigger("addressChange", {key: "city", value: e.code});
			}, this);

			this.localitiesView.on("entrySelected", function (e) {
				if (e.code)
					this.streetsView.collection.setParentCode(e.code).fetch();
				else
					this.streetsView.clean();

				//this.trigger("addressChange", {key: "locality", value: e.code});
			}, this);

			this.streetsView.on("entrySelected", function (e) {
				//this.trigger("addressChange", {key: "street", value: e.code});
			}, this);
		},

		render: function () {
			this.$el.html($.tmpl(this.template, {
				addressType: this.options.addressType,
				address: this.options.address.toJSON()
			}));

			//UIInitialize(this.el);

			this.$(".kladr-toggle").parent().after(
				this.republicsView.render().el,
				this.districtsView.render().el,
				this.citiesView.render().el,
				this.localitiesView.render().el,
				this.streetsView.render().el
			);

			if (this.options.address && this.options.address.get("kladr")) {
				this.republicsView.collection.fetch();
				if (this.districtsView.collection.getParentCode()) this.districtsView.collection.fetch();
				if (this.citiesView.collection.getParentCode()) this.citiesView.collection.fetch();
				if (this.localitiesView.collection.getParentCode()) this.localitiesView.collection.fetch();
				if (this.streetsView.collection.getParentCode()) this.streetsView.collection.fetch();
			}

			//var chk = this.$(".kladr-toggle").is(':checked');
			//this.$("input[type=text][name!='address-"+this.options.addressType+"-fullAddress']").attr('disabled', !chk).toggleClass("Disabled", !chk);
			//this.$("input[type=text][name='address-"+this.options.addressType+"-fullAddress']").attr('disabled', chk).toggleClass("Disabled", chk);

			return this;
		},

		onToggle: function () {
			var chk = this.$(".kladr-toggle").is(':checked');

			if (!chk) {
				//this.$("select").attr('disabled', true).trigger("liszt:updated");
			} else {
				if (this.model.get("republics").isEmpty()) this.model.get("republics").fetch();
			//	this.$("select").attr('disabled', function () { return $("option", this).size() <= 1; }).trigger("liszt:updated");
			}
			//this.$("input[type=text][name!='address[fullAddress]']").attr('disabled', !chk).toggleClass("Disabled", !chk);
			//this.$("input[type=text][name='address[fullAddress]']").attr('disabled', chk).toggleClass("Disabled", chk);

			//this.trigger("addressChange", {key: "kladr", value: this.$(".kladr-toggle").is(':checked')});

			return this;
		},

		onOtherInputChange: function (e) {
			//this.trigger("addressChange", {key: $(e.currentTarget).data("key"), value: $(e.currentTarget).val()});
		}
	});
	
	return App.Views.KLADR;
});