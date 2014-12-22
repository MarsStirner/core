/**
 * User: FKurilov
 * Date: 30.07.12
 */
define([
	"models/kladr",
	"text!templates/kladr/kladr-new.tmpl"
], function (KLADRModel, tmpl) {
	/*var kladrBackboneless = _.extend({
		levels: {
			"republic": _.extend({
				fetch: function () {
					$.ajax({
						dataType: "jsonp",
						url: DATA_PATH + "dictionary?dictName=KLADR&filter[level]=" + this.getLevel() + "&filter[parent]=" + this.getParentCode(),
						success: function () {
							console.log("success!");
						}
					});
				}
			}, Backbone.Events),
			"district": {},
			"city": {},
			"locality": {},
			"street": {}
		}


	}, Backbone.Events);*/

	App.Views.KLADR = View.extend({
		template: tmpl,

		events: {
			"change select.kladrEntries": "onKLADREntryChange",
			"change .kladrToggle": "onKLADRToggleChange",
			"change .kladrPart:not(.kladrEntries)": "onKLADRPartChange"//,
			//"click #updateKladr": "onUpdateKLADRClick"
		},

		initialize: function (options) {
			this.model = new App.Models.KLADR();
			this.address = this.options.address;
			//console.log(this.options.address.toJSON());

			this.model.on("all", function (event, target) {
				//console.log(event, target);
				if (event.indexOf(":") != -1) {
					var eventType = event.split(":")[0];
					var eventTargetName = event.split(":")[1];

					if (eventType === "reset") {
						this.addEntries(eventTargetName, target);
					}
				}
			}, this);

			//this.loadKladr();
		},

		loadKladr: function () {
			this.model.get("districts").setParentCode(this.address.get("republic").get("code"));
			this.model.get("localities").setParentCode(this.address.get("district").get("code"));
			this.model.get("cities").setParentCode(this.address.get("locality").get("code"));
			this.model.get("streets").setParentCode(this.address.get("city").get("code"));

			/*if (!this.address.get("republic").get("code") && this.address.get("city").get("code")) {
				this.address.get("republic").set("code", this.address.get("city").get("code"), {silent: true});
			} else if (!this.address.get("locality").get("code")) {
				this.address.get("locality").set("code", this.address.get("city").get("code"), {silent: true});
			}*/

			this.model.get("republics").fetch();

			/*var levels = _([
				this.model.get("republics"),
				this.model.get("districts").setParentCode(this.address.get("republic").get("code")),
				this.model.get("cities").setParentCode(this.address.get("district").get("code")),
				this.model.get("localities").setParentCode(this.address.get("city").get("code")),
				this.model.get("streets").setParentCode(this.address.get("locality").get("code"))
			]);

			console.warn("loading initial kladr entries", levels);

			var promises = [];

			levels.each(function (level) {
				if (level.level === "republic" || level.getParentCode()) {
					promises.push(level.fetch());
				}
			});*/
		},

		onKLADREntryChange: function (event) {
			//console.warn("entry selected");

			var $sel = $(event.currentTarget);
			var $selectedOption = $sel.find("option:selected");
			var selectedCode = $selectedOption.val();
			var entrySocrAndName = $sel.find("option:selected").text().split(" ");

			this.$("#postIndex").text($selectedOption.data("index") || "-");

			var propertyChain = $sel.attr("name").split("-");

			var socr = "";
			var name = "";

			if (entrySocrAndName.length > 1) {
				socr = entrySocrAndName.shift();
			}
			name = entrySocrAndName.join(" ");

			this.address.get(propertyChain[propertyChain.length-2]).set({
				code: selectedCode,
				socr: socr || "",
				name: name || "",
				index: $selectedOption.data("index").toString()
			});



			if (selectedCode) {
				//console.log("selected entry has code", selectedCode);

				var changedLevelName = $sel.data("entries-name");
				var changedLevel = this.model.get(changedLevelName);

				var childLevelName = changedLevel.childName;
				var childLevel = this.model.get(childLevelName);

				if (childLevel) {
					var promise = childLevel.setParentCode(selectedCode).fetch();

					if (promise) {
						while (childLevelName) {
							//console.log("child found, setting parent code and fetching");

							this.address.get(childLevel.level).set({code: "", name: "", socr: ""});

							var $childSel = this.$("[data-entries-name=" + childLevelName + "]");

							$childSel.select2("val", "").select2("disable");


							childLevel.setParentCode(selectedCode);//.fetch();

							childLevelName = childLevel.childName;
							childLevel = this.model.get(childLevelName);
						}
					}

					/*if (childLevelName) {
					 console.log("child found, setting parent code and fetching");

					 var $childSel = this.$("[data-entries-name=" + childLevelName + "]");
					 $childSel.select2("val", "").select2("disable");

					 var childLevel = this.model.get(childLevelName);
					 childLevel.setParentCode(selectedCode).fetch();
					 } else {
					 console.log("no child (is street)");
					 }*/
				}
			} else {
				//console.log("empty value selected");
			}
		},

		onKLADRPartChange: function (event) {
			var $field = $(event.currentTarget);
			var propertyChain = $field.attr("name").split("-");
			this.address.set(propertyChain[propertyChain.length-1], $field.val());
		},

		onKLADRToggleChange: function () {
			var $tgl = this.$(".kladrToggle");
			//console.log("kladr toggle changed to", $tgl.is(":checked"));
			//this.options.address.set("kladr", $tgl.is(":checked"));
			this.address.set("kladr", $tgl.is(":checked"));
			this.toggleKladrMode($tgl.is(":checked"));
		},

		toggleKladrMode: function (enabled) {
			var $tgl = this.$(".kladrToggle");

			if ($tgl.is(":checked") != enabled) {
				$tgl.attr("checked", enabled)
			}

			if (enabled) {
				this.$(".kladrEntries");//.select2("val", "");
				this.$(".kladrEntries:has(option:not(:first))").select2("enable");
				this.$("input.kladrPart, #updateKladr")
					.removeClass("Disabled")
					.attr("disabled", false);
				this.$("[name$=fullAddress]")
					.addClass("Disabled")
					.attr("disabled", true);
				this.$(".kladrPreviewLine").css("color", "black");
					//.val("");
			} else {
				this.$(".kladrEntries")
					.select2("disable");
					//.select2("val", "");
				this.$("input.kladrPart, #updateKladr")
					//.val("")
					.addClass("Disabled")
					.attr("disabled", true);
				this.$("[name$=fullAddress]")
					.removeClass("Disabled")
					.attr("disabled", false);
				this.$(".kladrPreviewLine").css("color", "grey");
			}
		},

		//onUpdateKLADRClick: function (event) {
			/*var self = this;
			var address = self.address.toJSON();
			var kladrSelectedEntries = [
				address.republic,
				address.district,
				address.city,
				address.locality,
				address.street
			];
			var showKladrPreview = _(kladrSelectedEntries).any(function (p) { return p.name; });*/

			//this.$(".kladrPreviewLine").hide();
			//this.$(".kladrPartLine").show();
		//},

		addEntries: function (collectionName, collection) {

			//console.log("try to add entries", collectionName);

			var $sel = this.$("[data-entries-name=" + collectionName + "]");

			$sel.select2("val", "").find("option:not(:first)").remove();

			if (collection.length) {
				//console.log("entries found, adding", collection);

				var $tgl = this.$(".kladrToggle");
				if ($tgl.is(":checked")) {
					$sel.select2("enable");
				}

				//var entries = collection.toJSON();
				var options = "";

				//_(entries).each(function (entry) {
				collection.each(function (entry) {
					options += "<option value='" + entry.get("code") + "' data-index='" + entry.get("index") + "'>" + entry.get("sock") + " " + entry.get("value") + "</option>";
				});

				$sel.append(options);

				if (this.address.get(collection.level)) {
					//console.log("setting value", this.$("select[name$=" + collection.level + "-code]"), this.address.get(collection.level).get("code"));

					var code = this.address.get(collection.level).get("code");

					//if (collection.pluck("code").indexOf(code) != -1) {
						this.$("select[name$=" + collection.level + "-code]").select2('val', code);
					//} else {
						//var indexOfLevel = App.Models.KLADR.LEVELS_ORDER.indexOf(collection.level);
						//var indexOfHigherLevel = indexOfLevel - 1;

						//this.address.get(App.Models.KLADR.LEVELS_ORDER[indexOfHigherLevel]).set("code", code);

						//this.$("select[name$=" + App.Models.KLADR.LEVELS_ORDER[indexOfHigherLevel] + "-code]").select2('val', code);
						//this.address.get(collection.level).set("code")
					//}

				}
			} else {

			}

			if (collection.childName) {
				//console.log("no entries found, fetching child");

				//$sel.select2("disable");

				var childColl = this.model.get(collection.childName);

				if (!childColl.getParentCode() || childColl.getParentCode().substr(0, 2) != collection.getParentCode().substr(0, 2)) {
					childColl.setParentCode(collection.getParentCode());
				}

				childColl.fetch();

				//this.model.get(collection.childName).fetch();
			} else {
				//console.log("no entries found, child name undefined");
			}


			/*if (this.savedAddressJSON[collection.getLevel()]) {
				$sel.select2('val', this.savedAddressJSON[collection.getLevel()]);
			}*/

			//$sel.change();

			//console.log(this.$("select[name$=" + collection.getLevel() + "-code]"));
		},

		render: function () {
			var self = this;

			var address = self.address.toJSON();
			var kladrSelectedEntries = [
				address.republic,
				address.district,
				address.city,
				address.locality,
				address.street
			];

			if (address.kladr) {
				var deepestEntryWithIndex = _(_(kladrSelectedEntries).filter(function (kse) {
					return kse.index && kse.index.length > 0;
				})).last();

				address.index = deepestEntryWithIndex ? deepestEntryWithIndex.index : "";
			}

			//var showKladrPreview = false; //_(kladrSelectedEntries).any(function (p) { return p.name; });

			this.$el.html($.tmpl(this.template, {
				addressType: self.options.addressType,
				address: address,
				kladrSelectedEntries: kladrSelectedEntries//,
				//showKladrPreview: showKladrPreview
			}));

			this.$(".select2").width("100%").select2();

			this.loadKladr();
			//console.log("kladr", !!this.address.get("kladr"));
			this.toggleKladrMode(!!this.address.get("kladr"));

			//this.$(".Styled.Button").css("top", 0);

			//this.$(".kladrPreviewLine").toggle(showKladrPreview);
			//this.$(".kladrPartLine").toggle(!showKladrPreview);

			return this;
		}
	});

	return App.Views.KLADR;
});