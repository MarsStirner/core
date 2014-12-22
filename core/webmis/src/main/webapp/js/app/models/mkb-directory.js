define(['models/mkb'], function () {

	App.Collections.Mkbs = Collection.extend({
		model: App.Models.Mkb,

		initialize: function (models, options) {
			this._params = {};
			this.subGroupId = options.subGroupId;
			this.diagnosis = options.diagnosis;
			this.requestAll = options.requestAll;
			this.sex = 0;

			if (Cache.Patient) {
				if (Cache.Patient.get("sex") === "male")
					this.sex = 1;
				else if (Cache.Patient.get("sex") === "female")
					this.sex = 2;
			}
		},

		url: function () {
			if (this.requestAll) {
				return DATA_PATH + "dir/mkbs/?filter[view]=mkb&filter[sex]=" + this.sex;
			} else if ( this.diagnosis ) {
				return DATA_PATH + "dir/mkbs/?filter[view]=mkb&filter[diagnosis]=" + this.diagnosis + "&filter[sex]=" + this.sex;
			} else {
				return DATA_PATH + "dir/mkbs/?filter[view]=mkb&filter[code]=" + this.subGroupId + "&filter[sex]=" + this.sex;
			}

		}
	});

	// A group of MKB diagnosis entries
	App.Models.MkbSubGroup = Model.extend({
		defaults: {
			code: "",
			mkbs: []
		},

		hasChildren: true,

		getTitle: function () {
			//return this.get("id") + " " + this.get("code");
			return this.get("code");
		},

		getBreadcrumbTitle: function () {
			return this.get("id");
		},

		/*parse: function (raw) {
			return {
				id: raw.subGroup.id,
				code: raw.subGroup.code,
				mkbs: raw.subGroup.mkbs
			};
		},*/

		relations: [
			{
				type: Backbone.HasMany,
				key: "mkbs",
				isChild: true,
				relatedModel: "App.Models.Mkb",
				collectionType: "App.Collections.Mkbs",
				collectionOptions: function (model) {
					return {
						subGroupId: model.get("id")
					};
				},
				reverseRelation: {
					key: "parent",
					includeInJSON: false
				}
			}
		]
	});

	App.Collections.MkbSubGroups = Collection.extend({
		model: App.Models.MkbSubGroup,

		initialize: function (models, options) {
			this._params = {};
			this.groupId = options.groupId;
			this.sex = Cache.Patient.get("sex" ).length ? (Cache.Patient.get("sex")=="male" ? 1 : 2) : 0;
		},
		url: function () {
			return DATA_PATH + "dir/mkbs/?filter[view]=subgroup&filter[groupId]=" + this.groupId+"&filter[sex]="+this.sex;
		}
	});

	// A group of subgroups
	App.Models.MkbGroup = Model.extend({
		defaults: {
			code: "",
			subGroups: []
		},

		hasChildren: true,

		getTitle: function () {
			//return this.get("id").replace(/[\(\)]/g, "") + " " + this.get("code");
			return this.get("code");
		},

		getBreadcrumbTitle: function () {
			return this.get("id").replace(/[\(\)]/g, "");
		},

		/*parse: function (raw) {
			return raw.group ? {
				id: raw.group.id,
				code: raw.group.code,
				subGroups: raw.subGroups || []
			} : {};
		},*/

		relations: [
			{
				type: Backbone.HasMany,
				key: "subGroups",
				isChild: true,
				createModels: true,
				relatedModel: "App.Models.MkbSubGroup",
				collectionType: "App.Collections.MkbSubGroups",
				collectionOptions: function (model) {
					return {
						groupId: model.get("id")
					}
				},
				reverseRelation: {
					key: "parent",
					includeInJSON: false
				}
			}
		]

	});

	App.Collections.MkbGroups = Collection.extend({
		model: App.Models.MkbGroup,

		initialize: function (models, options) {
			this._params = {};
			this.classMkbId = options.classMkbId;
			this.sex = Cache.Patient.get("sex" ).length ? (Cache.Patient.get("sex")=="male" ? 1 : 2) : 0;
		},

		url: function () {
			return DATA_PATH + "dir/mkbs/?filter[view]=group&filter[classId]=" + this.classMkbId +"&filter[sex]="+this.sex;
		}
	});

	// MKB classes
	App.Models.MkbClass = Model.extend({
		defaults: {
			code: "",
			diagIdMin: "",
			diagIdMax: "",
			groups: []
		},

		hasChildren: true,

		getTitle: function () {
			//return this.get("id") + " [" + this.get("diagIdMin") + "-" + this.get("diagIdMax") + "] " + this.get("code");
			return "[" + this.get("diagIdMin") + "-" + this.get("diagIdMax") + "] " + this.get("code");
		},

		getBreadcrumbTitle: function () {
			return this.get("id");
		},

		/*parse: function (raw) {
			//parsing hell here...
			return raw.classMkb ? {
				id: raw.classMkb.id,
				code: raw.classMkb.code,
				groups: _.map(raw.groups, function (rawGroup) {
					return {
						id: rawGroup.group.id,
						code: rawGroup.group.code,
						subGroups: _.map(rawGroup.subGroups, function (rawSubgroup) {
							return {
								id: rawSubgroup.subGroup.id,
								code: rawSubgroup.subGroup.code,
								mkbs: rawSubgroup.mkbs || []
							};
						}) || []
					}
				}) || []
			} : {};
		},*/

		relations: [
			{
				type: Backbone.HasMany,
				createModels: true,
				key: "groups",
				isChild: true,
				relatedModel: App.Models.MkbGroup,
				collectionType: App.Collections.MkbGroups,
				collectionOptions: function (model) {
					return {
						classMkbId: model.get("id")
					};
				},
				reverseRelation: {
					key: "parent",
					includeInJSON: false
				}
			}
		]
	});

	App.Collections.MkbClasses = Collection.extend({
		model: App.Models.MkbClass,

		initialCode: "",

		initialize: function (models, options) {
			this._params = {};
			this.sex = Cache.Patient.get("sex" ).length ? (Cache.Patient.get("sex")=="male" ? 1 : 2) : 0;
		},

		setInitialCode: function (code) {
			this.initialCode = code;
		},

		getInitialCode: function () {
			return this.initialCode;
		},

		url: function () {
			return this.getInitialCode() ?
				DATA_PATH + "dir/mkbs/?filter[code]=" + this.getInitialCode() +"&filter[sex]="+this.sex :
				DATA_PATH + "dir/mkbs/?filter[view]=class"+"&filter[sex]="+this.sex;
		}
	});

	//And finally the mkb directory model! %)
	App.Models.MkbDirectory = Model.extend({
		defaults: {
			mkbClasses: []
		},

		isRoot: true,

		getBreadcrumbTitle: function () {
			return "МКБ";
		},


		relations: [
			{
				type: Backbone.HasMany,
				key: "mkbClasses",
				isChild: true,
				createModels: true,
				relatedModel: "App.Models.MkbClass",
				collectionType: "App.Collections.MkbClasses",
				reverseRelation: {
					key: "parent",
					includeInJSON: false
				}
			}
		]
	});
});