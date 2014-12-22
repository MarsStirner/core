define(function(require) {
	var shared = require('views/appeal/edit/pages/monitoring/shared');
	var MonitoringInfos = require('views/appeal/edit/pages/monitoring/collections/MonitoringInfos2');
	var MonitoringInfo = require('views/appeal/edit/pages/monitoring/models/MonitoringInfo');


	/**
	 * Модель для таблицы "Экспресс-анализы"
	 * @type {*}
	 */
	var ExpressAnalysis = MonitoringInfo.extend({
		defaults: {
			"datetime": "",
			"k": "",
			"na": "",
			"ca": "",
			"glucose": "",
			"protein": "",
			"urea": "",
			"bilubrinOb": "",
			"bilubrinPr": "",
			"wbc": "",
			"gran": "",
			"neut": "",
			"hgb": "",
			"plt": ""
		}
	});


	/**
	 * Коллекция для таблицы "Экспресс-анализы"
	 * @type {*}
	 */
	var ExpressAnalyses = MonitoringInfos.extend({
		model: ExpressAnalysis,

		getParseMap: function(rawByDate) {
			return _.map(rawByDate, function(rawRow, date) {
				//console.log('rawRow, date', rawRow, date);
				return {
					"datetime": +date,
					"k": rawRow["K"],
					"na": rawRow["NA"],
					"ca": rawRow["CA"],
					"glucose": rawRow["GLUCOSE"],
					"protein": rawRow["TP"],
					"urea": rawRow["UREA"],
					"bilubrinOb": rawRow["TB"],
					"bilubrinPr": rawRow["CB"],
					"wbc": rawRow["WBC"],
					"gran": "",
					"neut": rawRow["NEUT"],
					"hgb": rawRow["HGB"],
					"plt": rawRow["PLT"]
				};
			});
		},

		url: function() {
			return DATA_PATH + "appeals/" + shared.models.appeal.get("id") + "/analyzes";
		}
	});

	return ExpressAnalyses;
});
