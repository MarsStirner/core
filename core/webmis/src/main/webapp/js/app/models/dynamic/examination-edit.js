define(function () {

	var BasicParamsModel = Model.extend({
		defaults: {
			name: "",
			value: ""
		}
	});

	var ParentID = Model.extend({
		defaults: {
			type: "",
			name: ""
		}
	});

	var AdParam = BasicParamsModel.extend();
	var AdParams = Collection.extend({
		model: AdParam
	});

	var LayoutProperty = Model.extend({
		defaults: {
			order: null,
			hAlign: "",
			vAlign: "",
			hSize: "",
			visible: "",
			adParams: [{}]
		},
		relations: [
			{
				type: Backbone.HasMany,
				key: "adParams",
				relatedModel: AdParam,
				collectionType: AdParams
			}
		]
	});


	var Child = Model.extend({
		defaults: {
			type: "",
			layoutProperties: [{}],
			childList: []
		},
		relations: [
			{
				type: Backbone.HasOne,
				key: "layoutProperties",
				relatedModel: LayoutProperty
			}
		]
	});

	var ChildList = Collection.extend({
		model: Child
	});

	var Group = Model.extend({
		defaults: {
			type: "",
			name: "",
			title: "",
			designIsVisible: "",
			parentID: {},
			childList: [{}]
		},
		relations: [
			{
				type: Backbone.HasOne,
				key: "parentID",
				relatedModel: ParentID
			},
			{
				type: Backbone.HasMany,
				key: "childList",
				relatedModel: Child,
				collectionType: ChildList
			}
		]
	});
	var Groups = Collection.extend({
		model: Group
	});
//////////////////////////////////

	var DatasourceParam = BasicParamsModel.extend();
	var DatasourceParams = Collection.extend({
		model: DatasourceParam
	});

	var WidgetParam = BasicParamsModel.extend();
	var WidgetParams = Collection.extend({
		model: WidgetParam
	});

	var GUIElement = Model.extend({
		defaults: {
			name: "",
			title: "",
			type: "",
			readOnly: "",
			mandatory: "",
			mask: "",
			datasourceType: "",
			datasourceParams: [{}],
			widgetName: "",
			widgetParams: [{}],
			adParams: [{}]
		},
		relations: [
			{
				type: Backbone.HasMany,
				key: "datasourceParams",
				relatedModel: DatasourceParam,
				collectionType: DatasourceParams
			},
			{
				type: Backbone.HasMany,
				key: "widgetParams",
				relatedModel: WidgetParam,
				collectionType: WidgetParams
			},
			{
				type: Backbone.HasMany,
				key: "adParams",
				relatedModel: AdParam,
				collectionType: AdParams
			}
		]
	});
	var GUIElements = Collection.extend({
		model: GUIElement
	});
//////////////////////////////////
	var SourceData = Model.extend({
		defaults: {
			title: ""
		}
	});
	var SourceDataList = Collection.extend({
		model: SourceData
	});

	var Value = Model.extend({
		defaults: {
			type: "",
			name: "",
			value: null
		}
	});
	var ValueList = Collection.extend({
		model: Value
	});

	var ElementData = Model.extend({
		defaults: {
			guiElementID: null,
			sourceDataList: [{}],
			valueList: [{}]
		},
		relations: [
			{
				type: Backbone.HasMany,
				key: "sourceDataList",
				relatedModel: SourceData,
				collectionType: SourceDataList
			},
			{
				type: Backbone.HasMany,
				key: "valueList",
				relatedModel: Value,
				collectionType: ValueList
			}
		]
	});
	var ElementsData = Collection.extend({
		model: ElementData
	});

	App.Dynamic.ExaminationEdit = Model.extend({
		urlRoot: function(){
			return "/js/dynamic/examination.php"
		},

		defaults: {
			groupList: [{}],
			guiElementList: [{}],
			guiElementDataList: [{}]
		},

		relations: [
			{
				type: Backbone.HasMany,
				key: "groupList",
				relatedModel: Group,
				collectionType: Groups
			},
			{
				type: Backbone.HasMany,
				key: "guiElementList",
				relatedModel: GUIElement,
				collectionType: GUIElements
			},
			{
				type: Backbone.HasMany,
				key: "guiElementDataList",
				relatedModel: ElementData,
				collectionType: ElementsData
			}
		]
	});
});


