define(function(require) {
	var shared = require('views/appeal/edit/pages/monitoring/shared');

	var patientBsaRowTmpl = require('text!templates/appeal/edit/pages/monitoring/patient-bsa-row.tmpl');

	var BaseView = require('views/appeal/edit/pages/monitoring/views/BaseView');


	var PatientBsaRow = BaseView.extend({
		template: patientBsaRowTmpl,

		initialize: function() {
			BaseView.prototype.initialize.apply(this);

			this.collection.on("reset", this.render, this).fetch();
		},

		data: function() {
			var data = {
				weight: '',
				height: '',
				bsa: ''
			};
			if (this.collection.length) {
				//модели у которых есть рос и вес
				var wah = this.collection.filter(function(model) {
					return (model.get('weight') && model.get('growth'))
				});
				//последняя модель
				var model = _.first(wah);
				//console.log('model', model);

				if (!model) { //ели нет моделей с ростом и весом
					//модели у которых есть или рост или вес
					wah = this.collection.filter(function(model) {
						return (model.get('weight') || model.get('growth'))
					});
				}

				model = _.first(wah);

				if (model) {
					var weight = model.get('weight');

					if (weight && weight > 500) { //если вес больше 500, то наверно это граммы...
						weight = weight + ' г';
					} else if (weight && weight <= 500) {
						weight = weight + ' кг';
					}

					var height = model.get('growth') ? model.get('growth') + ' см' : '';
					var bsa = this.getBSA(model) ? this.getBSA(model) + ' кв.м' : '';

					data.weight = weight;
					data.height = height;
					data.bsa = bsa;
				}
			}


			return data;
		},

		getBSA: function(model) {
			var height = model.get('growth');
			var weight = model.get('weight');
			var bsa;

			if (weight > 500) { //если вес больше 500, то наверно это граммы...
				weight = weight / 1000;
			}

			if (height && weight) {
				bsa = Math.sqrt((height * weight) / 3600);

				bsa = bsa.toFixed(2);
			}

			return bsa;

		}

	});

	return PatientBsaRow;
});
