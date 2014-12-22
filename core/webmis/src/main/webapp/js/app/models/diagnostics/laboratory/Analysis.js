define(function(require) {
	var commonData = require('mixins/commonData');

	var Analysis = Model.extend({
		initialize: function(attr, options) {
			this.code = options.code;
			this.patientId = options.patientId;
			//this.deferred = this.fetch();

		},
		idAttribute: 'blablabla',
		checkTests: function() {
			// var self = this;
			var tests = this.get('group')[1].attribute;

			_.each(tests, function(test) {

				if (test.type == 'String') {
						this.setProperty(test.name, 'isAssigned', 'true');
				}

			}, this);
		},

		getTests: function() {
			var self = this;

			var tests = this.get('group')[1].attribute;

			var testsList = [];

			_.each(tests, function(test) {

				if (test.type == 'String') {
					var select = false;
					var unselectable = false;

					if (self.getProperty(test.name, 'isAssignable') == 'false') {
						unselectable = true;
					}

					if (self.getProperty(test.name, 'isAssigned') == 'true') {
						select = true;
					}

					testsList.push({
						title: test.name,
						unselectable: unselectable,
						select: select
					});

				}

			});


			return testsList;
		},

		url: function() {
			return DATA_PATH + 'dir/actionTypes?filter[mnem]=LAB&filter[mnem]=BAK_LAB&filter[code]=' + this.code + '&patientId=' + this.patientId;
		},

		parse: function(raw) {
			return raw.data[0];
		}

	}).mixin([commonData]);

	return Analysis;

});
