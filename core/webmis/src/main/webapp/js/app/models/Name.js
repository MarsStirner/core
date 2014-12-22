define(function () {

	var Name = Model.extend({
		defaults: {
			first: "",
			last: "",
			middle: "",
			raw: "",
			fio: "",
			full: ""
		},
		get: function (attr) {
			if (typeof this[attr] == 'function') {
				return this[attr]();
			}
			return Backbone.Model.prototype.get.call(this, attr);
		},
		toJSON: function() {
			var attr = Backbone.Model.prototype.toJSON.call(this);
			attr.fio = this.fio();
			attr.full = this.full();
			return attr;
		},
		fio: function () {
			var fio = '';
			if(this.get("last") && this.get("first") && this.get("middle")){
				fio = this.get("last") + ' ' + this.get("first")[0] + '. ' + this.get("middle")[0] + '.';
			}
			return fio;
		},
		full: function () {
			return this.get("last") + ' ' + this.get("first") + ' ' + this.get("middle");
		}

	});

	return Name;

});
