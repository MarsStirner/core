/**
 * User: FKurilov
 * Date: 18.06.12
 */
define(function () {
	var FieldDescription = Model.extend({
		defaults: {
			name: "",
			description: "",
			order: 0
		}
	});

	var FieldDescriptions = Collection.extend({
		model: FieldDescription
	});

	var Value = Model.extend({
		defaults: {
			value: ""
		}
	});

	var FieldValue = Model.extend({
		defaults: {
			fieldDescription: {},
			fieldValue: {}
		},

		relations: [
			{
				type: Backbone.HasOne,
				key: "fieldDescription",
				relatedModel: FieldDescription
			},
			{
				type: Backbone.HasOne,
				key: "fieldValue",
				relatedModel: Value
			}
		]
	});

	var FieldValues = Collection.extend({
		model: FieldValue
	});

	var Record = Model.extend({
		defaults: {
			order: "",
			fieldValueList: []
		},

		relations: [
			{
				type: Backbone.HasMany,
				key: "fieldValueList",
				relatedModel: FieldValue,
				collectionType: FieldValues
			}
		]
	});

	var Records = Collection.extend({
		model: Record
	});

	App.Models.FlatDirectory = Model.extend({
		defaults: {
			name: "",
			description: "",
			fieldDescriptionList: [],
			recordList: []
		},

		relations: [
			{
				type: Backbone.HasMany,
				key: "fieldDescriptionList",
				relatedModel: FieldDescription,
				collectionType: FieldDescriptions
			},
			{
				type: Backbone.HasMany,
				key: "recordList",
				relatedModel: Record,
				collectionType: Records
			}
		],

		additionalParams: {
			includeMeta: "no",
			includeRecordList: "yes",
			includeFDRecord: "yes"
		},

		getAdditionalParams: function () {
			return _.map(this.additionalParams, function (val, name) {
				return val ? "&" + name + "=" + val : "";
			}, this).join("");
		},

		url: function () {
			return DATA_PATH + "dir/flatDirectory/?limit=999&flatDirectoryId=" + this.get("id") + this.getAdditionalParams();
		},

		toBeautyJSON: function () {
			var recordList = this.get("recordList");
			var beautyRecordList = [];
			recordList.each(function (record) {
				var recordId = record.get("id");
				var beautyRecord = {id: recordId, order: record.get("order")};

				record
					.get("fieldValueList")
					.each(function(value) {
						beautyRecord[value.toJSON().fieldDescription.id] = value.toJSON().fieldValue.value;
						beautyRecord[value.toJSON().fieldDescription.name] = value.toJSON().fieldValue.value;
					});
				beautyRecordList.push(beautyRecord);
			});

			return beautyRecordList.sort(function (a, b) {
				return a.order - b.order;
			});
		},

		parse: function ( raw ) {
			return raw.data ? _.first(raw.data) : raw;
		}
	});

	return App.Models.FlatDirectory;
});