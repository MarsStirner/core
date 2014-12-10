/**
 * User: FKurilov
 * Date: 18.06.12
 */
define(["models/flat-directory"], function (FlatDirectory) {

	App.Collections.FlatDirectories = Collection.extend({
		model: FlatDirectory,

		url: function () {
			return DATA_PATH + "flatDirectory" + this.getRequestIdsParams() + this.getAdditionalParams();
		},

		requestIds: [],

		requestAll: false,

		additionalParams: {
			includeMeta: "no",
			includeRecordList: "yes",
			includeFDRecord: "yes"
		},

		sortingField: "",

		getRequestIdsParams: function () {
			return this.requestAll ? "flatDirectoryId=*" : _.map(this.requestIds, function (id, i) {
				return ((i > 0) ? "&flatDirectoryId=" : "?flatDirectoryId=") + id;
			}, this).join("");
		},

		getAdditionalParams: function () {
			return _.map(this.additionalParams, function (val, name) {
				return val ? "&" + name + "=" + val : "";
			}, this).join("");
		},

		setAdditionalParam: function (name, value) {
			this.additionalParams[name] = value;
		},

		addRequestId: function (id) {
			if (_.isNumber(id) && this.requestIds.indexOf(id) === -1) {
				this.requestIds.push(id);
				return true;
			} else {
				if (window.console) console.log("RequestId is invalid or already in the list.");
				return false;
			}
		},

		addRequestIds: function (ids) {
			if (_.isArray(ids)) {
				_.each(ids, function (id) {
					this.addRequestId(id);
				}, this);
				return true;
			} else {
				if (window.console) console.log("Expected an Array");
				return false;
			}
		},

		removeRequestId: function (id) {
			var ridIndex = this.requestIds.indexOf(id);

			if (ridIndex !== -1) {
				this.requestIds.splice(ridIndex, 1);
				return true;
			} else {
				if (window.console) console.log("Can't find RequestId in the list.");
				return false;
			}
		},

		clearRequestIds: function () {
			this.requestIds = [];
		},

		toBeautyJSON: function () {
			return this.map(function (fd) {
				return {
					id: fd.get("id"),
					records: fd.toBeautyJSON()
				};
			});
		}
	});

	/*var flatDictionaries = new FlatDictionaries();
	flatDictionaries.setParams({
		flatDirectoryId: [],
		sortingFieldId: 5,
		includeMeta: "yes"
	});*/

	return App.Collections.FlatDirectories;
});