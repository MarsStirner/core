define(function(require) {

	var shared = require('views/appeal/edit/pages/monitoring/shared');

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
		initialize: function() {
			this.combined = [];
			// console.log('init')
		},

		url: function() {
			return DATA_PATH + "appeals/" + shared.models.appeal.get("id") + "/monitoring";
			//return "/monitoring-info.json";
		},

		getParseMap: function(rawByDate) {
			console.log('rawByDate', rawByDate)
			return _.map(rawByDate, function(rawRow, date) {
				console.log('rawRow, date', rawRow, date)
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
		//преобразует коллекцию в список ключей с массивами значений

		byKeys: function() {
			var cj = (this.toJSON()).slice(0, 5);
			var result = {};

			if (cj.length > 0) {

				var keys = _.chain(cj).first().keys().value();

				_.each(keys, function(key) {
					result[key] = [];

					_.each(cj, function(item) {

						result[key].push(item[key]);

					});

				});

			}

			return result;
		},
		merge: function(items) {
			items = items.reverse();

			var obj = {};

			_.each(items, function(item) {
				_.each(_.keys(item), function(key) {
					if (!(item[key] === '' || item[key] === 'weight' || item[key] === 'growth' || typeof item[key] === 'undefined')) {
						obj[key] = item[key]
						//console.log('key', key, item[key],  typeof item[key]);
					}
				});
				// console.log('obj', obj)
			});
			// console.log('merge', items, obj);
			return obj;
		},


		combine: function(items) {

			if (_.isEmpty(items)) return [];
			items = items.slice();

			var top = _.first(items);
			var startTime = top.datetime;
			var endTime = startTime - (1 * 60 * 60 * 1000);
			var items4combine = [items.shift()];
			var nextItems = [];
			//var combined = [];


			_.each(items, function(item) {
				if (item.datetime >= endTime && !item.temperature) {
					items4combine.push(item);
				} else {
					nextItems.push(item);
				}
			});

			var merged = this.merge(items4combine);
			this.combined.push(merged);
			// console.log('combine',top, items4combine, merged);

			if (nextItems.length > 0) {
				this.combine(nextItems);
			}

			return this.combined;
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
			//console.log('raw', raw);
			//console.log('parsed', parsed);
			parsed = this.combine(parsed, []);

			return parsed;
		}
	});


	return MonitoringInfos;



});
