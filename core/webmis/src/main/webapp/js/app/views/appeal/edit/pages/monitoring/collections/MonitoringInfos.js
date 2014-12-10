define(function(require) {

	// var shared = require('views/appeal/edit/pages/monitoring/shared');

	/**
	 * Модель для таблицы "Мониторинг"
	 * @type {*}
	 */
	var MonitoringInfo = require('views/appeal/edit/pages/monitoring/models/MonitoringInfo');


	/**
	 * Коллекция для таблицы "Мониторинг"
	 * @type {*}
	 */
	var MonitoringInfos = Collection.extend({
		model: MonitoringInfo,
		initialize: function(models, options) {
			this.appealId = options.appealId;
		},

		url: function() {
			return DATA_PATH + "appeals/" + this.appealId + "/monitoring";
			//return "/monitoring-info.json";
		},

		getParseMap: function(rawByDate) {
			//console.log('rawByDate', rawByDate)
			return _.map(rawByDate, function(rawRow, date) {
				//console.log('rawRow, date', rawRow, date)
				return {
					datetime: +date,
					temperature: rawRow["TEMPERATURE"],
					bpras: rawRow["BPRAS"],
					bprad: rawRow["BPRAD"],
					heartRate: rawRow["PULS"],
					spo2: rawRow["SPO2"],
					breathRate: rawRow["RR"],
					state: rawRow["STATE"],
					health: rawRow["WB"],
					weight: rawRow["WEIGHT"],
					growth: rawRow["GROWTH"]

				};
			});
		},

		getKeys: function() {
			var keys = _.keys(this.model.defaults)
			return keys;
		},

		//преобразует коллекцию в список ключей с массивами значений

		byKeys: function() {
			this.getKeys();

			var cj = (this.toJSON()).slice(0, 5);

			var result = {};

			if (cj.length > 0) {

				// var keys = _.chain(cj).first().keys().value();
				var keys = this.getKeys();

				_.each(keys, function(key) {
					result[key] = [];

					_.each(cj, function(item) {

						result[key].push(item[key]);

					});

				});

			}

			return result;
		},

		parse: function(raw) {
			var rawByDate = {};

			_.each(raw.data, function(param) {
				_.each(param.values, function(paramValue) {
					if (!rawByDate.hasOwnProperty(paramValue.date)) {
						rawByDate[paramValue.date] = {};
					}
					rawByDate[paramValue.date][param.code] = (paramValue.value !== "0.0" ? paramValue.value : "");
				});
			});

			var parsed = this.getParseMap(rawByDate);

			parsed = parsed
				.sort(function(a, b) {

					var adt = a.datetime;
					var bdt = b.datetime;

					if (adt < bdt) return 1;
					else if (adt > bdt) return -1;
					else return 0;
				})
				.filter(function(row) {
					return _.some(row, function(field, fieldName) {
						return fieldName !== "datetime" && field && field.toString().length;
					});
				})
			///.slice(0, 5);

			// console.log(rawByDate);
			// console.log('raw', raw);
			// console.log('parsed', parsed);

			return parsed;
		}
	});


	return MonitoringInfos;



});
