/**
 * User: FKurilov
 * Date: 26.07.12
 */
define(function () {
	/*App.Models.KLADREntry = Model.extend({
		defaults: {
			code: "",
			sock: "",
			value: "",
			index: ""
		},
		getTitle: function () {
			return this.get("sock") + " " + this.get("value");
		}
	});*/

	var cachedEntries = {};

	App.Collections.KLADREntries = Collection.extend({
		//model: App.Models.KLADREntry,

		initialize: function (models, options) {
			//cachedEntries = {};

			this.setLevel(options.level);
			this.setParentCode(options.parent);

			this.childName = options.childName;

			this.name = options.name;

			this.on("reset", this.cacheEntrySet, this);
		},

		setLevel: function (level) {
			this.level = level;
		},

		getLevel: function () {
			return this.level;
		},

		setParentCode: function (parent) {
			if (parent) this.parent = parent;
			return this;
		},

		getParentCode: function () {
			return this.parent || "";
		},

		url: function () {
			return DATA_PATH + "dir?dictName=KLADR&filter[level]=" + this.getLevel() + "&filter[parent]=" + this.getParentCode();
		},

		fetch: function () {
			this.trigger("fetch:start");

			//console.log(cachedEntries, this.getLevel() + "_" + this.getParentCode());

			if (cachedEntries[this.getLevel() + "_" + this.getParentCode()]) {
				//console.log("from Cache");
				//return
				this.reset(cachedEntries[this.getLevel() + "_" + this.getParentCode()]);
			} else if (this.getParentCode() || this.getLevel() === "republic") {
				//console.log("from Service");
				return Collection.prototype.fetch.call(this);
			} else {
				//console.log("from Empty");
				//return
				this.reset([]);
			}
		},

		cacheEntrySet: function () {
			//if (!cachedEntries[this.getLevel() + "_" + this.getParentCode()] && this.toJSON().length) {
			if (!cachedEntries[this.getLevel() + "_" + this.getParentCode()]) {
				cachedEntries[this.getLevel() + "_" + this.getParentCode()] = this.toJSON();
				/*console.log("cached entries", this.toJSON(), cachedEntries);*/
			}
		}
	});

	App.Models.KLADR = Model.extend({
		defaults: {
			republics: [],
			districts: [],
			cities: [],
			localities: [],
			streets: []
		},

		initialize: function () {
			this.set({
				republics:  new App.Collections.KLADREntries([], {level: "republic", name: "republics", childName: "districts"}),
				districts:  new App.Collections.KLADREntries([], {level: "district", name: "districts", childName: "localities"}),
				localities: new App.Collections.KLADREntries([], {level: "locality", name: "localities", childName: "cities"}),
				cities:     new App.Collections.KLADREntries([], {level: "city", name: "cities", childName: "streets"}),
				streets:    new App.Collections.KLADREntries([], {level: "street", name: "streets"})
			});

			this.get("republics").on("reset", this.bubbleReset, this);
			this.get("districts").on("reset", this.bubbleReset, this);
			this.get("cities").on("reset", this.bubbleReset, this);
			this.get("localities").on("reset", this.bubbleReset, this);
			this.get("streets").on("reset", this.bubbleReset, this);
		},

		bubbleReset: function (levelColl) {
			this.trigger("reset:" + levelColl.name, levelColl);
		}


		/*relations: [
			{
				type: Backbone.HasMany,
				key: "republics",
				collectionOptions: function () { return {level: "republic", childName: "districts"}},
				relatedModel: App.Models.KLADREntry,
				collectionType: App.Collections.KLADREntries
			},
			{
				type: Backbone.HasMany,
				key: "districts",
				collectionOptions: function () { return {level: "district", childName: "cities"}},
				relatedModel: App.Models.KLADREntry,
				collectionType: App.Collections.KLADREntries
			},
			{
				type: Backbone.HasMany,
				key: "cities",
				collectionOptions: function () { return {level: "city", childName: "localities"}},
				relatedModel: App.Models.KLADREntry,
				collectionType: App.Collections.KLADREntries
			},
			{
				type: Backbone.HasMany,
				key: "localities",
				collectionOptions: function () { return {level: "locality", childName: "streets"}},
				relatedModel: App.Models.KLADREntry,
				collectionType: App.Collections.KLADREntries
			},
			{
				type: Backbone.HasMany,
				key: "streets",
				collectionOptions: function () { return {level: "street"}},
				relatedModel: App.Models.KLADREntry,
				collectionType: App.Collections.KLADREntries
			}
		]*/
	});

	App.Models.KLADR.LEVELS_ORDER = [
		"republic",
		"district",
		"city",
		"locality",
		"street"
	];

	return App.Models.KLADR;
});