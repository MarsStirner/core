define(function () {
	App.Models.PrintForm007 = Model.extend({
		urlRoot: function () {
			var url = DATA_PATH + 'reports/f007';
			var params = [];
			
			if (this.get('departmentId')) {
				params.push('filter[departmentId]=' + this.get('departmentId'));
			}
			if (this.get('endDate')) {
				params.push('filter[endDate]=' + this.get('endDate'));
			}
			if (this.get('bedProfiles') && this.get('bedProfiles').length) {
				var bpFilter = 'filter[profileBed]=';
				params.push(bpFilter + this.get('bedProfiles').join("&" + bpFilter));
			}

			if (params.length) url += "?" + params.join("&");

			return url;
		}
	});

	return App.Models.PrintForm007;
});
