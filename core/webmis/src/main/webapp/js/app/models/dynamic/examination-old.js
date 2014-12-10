define(function () {
	var Property = Model.extend({
		defaults: {
			name: "",
			value: ""
		},

		validate: function (attrs) {
			switch (this.collection.attrType) {
				case "Double":
					if (attrs && attrs.value && !parseFloat(attrs.value)) {
						return "Введены недопустимые символы";
					}
					break;
			}
		}
	});

	var Properties = Collection.extend({
		model: Property,

		initialize: function (models, options) {
			this.attrType = options.type;
		}
	});

	var Attribute = Model.extend({
		defaults: {
			version: 0,
			name: "",
			scope: "",
			typeId: "",
			type: "",
			properties: []
		},

		relations: [
			{
				type: Backbone.HasMany,
				key: "properties",
				relatedModel: Property,
				collectionType: Properties,
				collectionOptions: function (model) {
					return {type: model.get("type")};
				}
			}
		]
	});

	var Attributes = Collection.extend({
		model: Attribute
	});

	var Group = Model.extend({
		defaults: {
			version: "",
			name: "",
			attribute: []
		},

		relations: [
			{
				type: Backbone.HasMany,
				key: "attribute",
				relatedModel: Attribute,
				collectionType: Attributes
			}
		]
	});

	var Groups = Collection.extend({
		model: Group
	});

	App.Dynamic.ExaminationOld = Model.extend({
		defaults: {
			version: 0,
			name: "",
			status: "",
			code: "",
			type: "",
			typeId: 0,
			group: []
		},

		//idAttribute: "id",

		relations: [
			{
				type: Backbone.HasMany,
				key: "group",
				relatedModel: Group,
				collectionType: Groups
			}
		],

		typesMap: {
			primaryStruct: "/examinations/primary",
			primarySave: "/examinations/primary"
		},

		initialize: function (options) {
			//this.typePath = this.typesMap[options.type] || this.typesMap.primaryStruct;
			this.structId = options.structId;
			this.appealId = options.appealId || 0;
			this.copy = options.copy;
			/*if (options.editId) {
				this.editId = options.editId;
			}*/

			Model.prototype.initialize.apply(this);
		},

		url: function () {
			var path = DATA_PATH + "appeals/" + this.appealId + "/examinations/";

			var structId = this.structId || this.get("typeId");

			if (this.copy) {
				path += "structWithCopy/" + structId|| this.get("typeId");
			} else if (this.get("id") && this.get("id") !== structId) {
				path += this.get("id");
			} else if (!this.get("id")) {
				path += "struct/" + structId;
			}

			return path;

			/*if (this.isSaving) {
				return DATA_PATH + "appeals/" + this.appealId + this.typePath;// + (this.get("id") ? this.get("id") : "");
			} else {
				return DATA_PATH + "appeals/" + this.appealId + this.typePath + (this.get("id") ? this.get("id") : "struct");
			}*/
		},
		
		toJSON: function () {
			var json = [Model.prototype.toJSON.apply(this)];
			delete json[0]["appealId"];
			return json;
		},

		getFlatAttrs: function () {
			if (!this.get("group").isEmpty()) {
				var examAttributes = this.get("group").at(1).get("attribute");
				var examFlatJSON = {};
				if (examAttributes) {
					examAttributes.each(function (a) {
						examFlatJSON[a.get("typeId")] = a.get("properties").first().get("value");
					});
				}
				return examFlatJSON;
			} else {
				return {};
			}
		},

		getFilledAttrs: function () {
			if (!this.get("group").isEmpty()) {
				var examAttributes = this.get("group").at(1).get("attribute");
				var examFlatJSON = [];
				if (examAttributes) {
					examAttributes.each(function (a) {
						var valueProp = a.get("properties").find(function (p) {
							return p.get("name") === "value";
						});

						if (valueProp && valueProp.get("value") && valueProp.get("value") !== "0.0") {
							examFlatJSON.push({
								id: a.get("typeId"),
								name: a.get("name"),
								value: valueProp.get("value")
							});
						}
					});
				}
				return examFlatJSON;
			} else {
				return [];
			}
		},

		parse: function (raw) {
			var data = _(raw.data).isArray() ? _(raw.data).first() : raw.data;

			var structId = this.structId || this.get("typeId");

			if (data.id !== structId) this.id = data.id;
			console.log("exam id", this.id);

			var generalAttrs = data.group[0]["attribute"];

			var examBeginDate = _(generalAttrs).find(function (attr) {
				return attr.name === "assessmentBeginDate";
			}).properties[0];

			if (examBeginDate.value) examBeginDate.value = new Date(examBeginDate.value).getTime();

			var examEndDate = _(generalAttrs).find(function (attr) {
				return attr.name === "assessmentEndDate";
			}).properties[0];

			if (examEndDate.value) examEndDate.value = new Date(examEndDate.value).getTime();

			return data;
		},

		save: function () {
			var generalAttrs = this.get("group").at(0).get("attribute");

			var examBeginDate = generalAttrs.find(function (attr) {
				return attr.get("name") === "assessmentBeginDate";
			}).get("properties").first();

			var begin = new Date(examBeginDate.get("value"));

			examBeginDate.set("value", $.datepicker.formatDate("yy-mm-dd", begin) + " " + begin.toTimeString().match( /^([0-9]{2}:[0-9]{2}:[0-9]{2})/ )[0] );//Core.Date.formatDateTime(examBeginDate.get("value")));

			var examEndDate = generalAttrs.find(function (attr) {
				return attr.get("name") === "assessmentEndDate";
			}).get("properties").first();

			if (examEndDate.get("value")) {
				var end = new Date(examEndDate.get("value"));

				examEndDate.set("value", $.datepicker.formatDate("yy-mm-dd", end) + " " + end.toTimeString().match( /^([0-9]{2}:[0-9]{2}:[0-9]{2})/ )[0] );//Core.Date.formatDateTime(examEndDate.get("value")));
			}

			console.log(examBeginDate, examEndDate);

			Model.prototype.save.apply(this);
		},

		validate: function () {

		}
	});

	return {
		Property: Property,
		Properties: Properties,
		Attribute: Attribute,
		Attributes: Attributes,
		Group: Group,
		Groups: Groups,
		ExaminationOld: App.Dynamic.ExaminationOld
	};
});