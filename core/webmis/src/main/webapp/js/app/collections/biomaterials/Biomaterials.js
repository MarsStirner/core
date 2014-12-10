define(function(require) {

	var Biomaterial = require('models/biomaterials/Biomaterial');

	var Biomaterials = Collection.extend({
		model: Biomaterial,
		url: function() {
			return DATA_PATH + "biomaterial/info?"; //filter[beginDate]=1354305600000&filter[endDate]=1354478400000"
		},
		initialize: function() {
			var collection = this;

			collection.countSelected();
			collection.countStatuses();
			collection.on("change:selected reset", collection.countSelected, collection);
			collection.on("reset", collection.countStatuses, collection);

		},

		/***
		 * подсчёт статусов
		 */
		countStatuses: function() {
			var collection = this;

			function countByStatus(status) {
				return collection.reduce(function(memo, model) {
					var i = 0;

					if (model.get('status') == status) {
						i = 1;
					}
					return memo + i;
				}, 0);

			}

			collection.count = {
				status_0: countByStatus(0),
				status_1: countByStatus(1),
				status_2: countByStatus(2),
				all: collection.length
			};

		},

		/***
		 * Считает выбранные джоб тикеты с разными статусами
		 */
		countSelected: function() {
			var collection = this;

			function filterSelectedByStatus(status) {
				return collection.filter(function(model) {
					return ((model.get("selected") == true) && (model.get('status') == status));
				});
			}

			collection.selected = {
				status_0: filterSelectedByStatus(0),
				status_1: filterSelectedByStatus(1),
				status_2: filterSelectedByStatus(2)
			};


		},

		getSelectedModels: function() {
			var collection = this;

			return collection.filter(function(model) {
				return model.get('selected');
			});
		},
		getSelectedModelsWithStatus0: function() {
			var collection = this;

			return collection.filter(function(model) {
				return (model.get('selected') && (model.get('status')===0));
			});
		},

		/***
		 * Возвращает отфильтрованный список лаб.исследований
		 *
		 * @param {Function} filterFunction - получает jobTicket, если возвращает true,
		 * то лаб.исследования из этого тикета попадают в возвращаемый массив
		 * @returns {Array} - массив лаб.исследований
		 */

		getLabTests: function(filterFunction) {
			var collection = this;
			var labTests = [];

			if (!_.isFunction(filterFunction)) {
				throw new Error("No 'filterFunction' for 'Biomaterials.getLabTests'");
			}

			collection.each(function(model) {

				if (filterFunction(model)) {
					_.each(model.get('actions'), function(labTest) {
						labTests.push(labTest);
					});
				}

			});

			return labTests;
		},

		/***
		 * Готовит данные для печати журнала выполнения работ
		 * @returns {Array}
		 */
		makeWorkList: function(labTests) {
			var collection = this;
			var workList = [];

			_.each(labTests, function(labTest, index) {

				var orgStructure = '';
				if (labTest.department && labTest.department.name) {
					orgStructure = labTest.department.name;
				}
				if (labTest.bed && labTest.bed.code) {
					orgStructure = orgStructure + '/' + labTest.bed.code;
				}

				var tissueTypeName = '';
				if (labTest.biomaterial && labTest.biomaterial.tissueType && labTest.biomaterial.tissueType.name) {
					tissueTypeName = labTest.biomaterial.tissueType.name;
				}

				var tubeTypeName = '';
				if (labTest.tubeType && labTest.tubeType.name) {
					tubeTypeName = labTest.tubeType.name;
				}


				workList.push({
					'index': index + 1,
					'jobTicketDate': labTest.jobTicket.date,
					'orgStructure': orgStructure,
					'patientName': labTest.patient.name,
					'patientSex': labTest.patient.sexShortName,
					'patientBirthDate': labTest.patient.birthDate,
					'labTestName': labTest.actionType.name,
					'tissueTypeName': tissueTypeName,
					'tubeTypeName': tubeTypeName,
					'jobTicketLabel': labTest.jobTicket.label,
					'jobTicketNote': labTest.jobTicket.note,
					'takenTissueJournalId': labTest.takenTissueJournal
				});
			});

			return workList;
		},

		/***
		 * Готовит данные для печати штрихкодов
		 * @param labTests - массив с лабораторными исследованиями
		 * @returns {Array}
		 */
		makeBarcodes: function(labTests) {
			var collection = this;
			var barcodes = [];

			_.each(labTests, function(labTest) {

				barcodes.push({
					datetime: labTest.jobTicket.date,
					barcode: labTest.takenTissueJournal,
					name: labTest.patient.name,
					appealNumber: labTest.appealNumber
				});

			});

			return barcodes;
		},

		parse: function(response) {
			checkForWarnings(response.requestData, "requestData was not found in the JSON");
			this.requestData = response.requestData || {};
			this.requestData.filter = this.requestData.filter || {};

			return this.normilize(response.data);
		},

		normilize: function(response) {
			return _.map(response, function(model) {

				model.urgent = model.actions[0].urgent ? model.actions[0].urgent : false;

				var volume = 0;

				if(model.label === '##Ошибка отправки в ЛИС##'){
					model.highlight = 'highlight-it';
				}

				_.each(model.actions, function(action, key, list) {
					var sexShortName = '';

					switch (action.patient.sex) {
						case 'male':
							sexShortName = 'М'
							break;
						case 'female':
							sexShortName = 'Ж';
							break;
						default:
							//sexShortName = 'не определён!';
					}
					action.patient.sexShortName = sexShortName;

					action.appealNumber = model.appealNumber;
					action.bed = model.bed;
					action.department = model.department;
					action.jobTicket = {
						'id': model.id,
						'date': model.date,
						'label': model.label,
						'note': model.note
					}

					volume = volume + action.biomaterial.amount;

				});

				model.volume = volume;
				if (model.actions[0].patient.sex == 'male') {
					model.patient.sex = 'М';
				}
				if (model.actions[0].patient.sex == 'female') {
					model.patient.sex = 'Ж';
				}


				if (model.status == 0) {
					model.statusName = 'ожидание';
				}
				if (model.status == 1) {
					model.statusName = 'выполнение';
				}
				if (model.status == 2) {
					model.statusName = 'закончено';
				}

				model.selected = false;

				return model;
			});

		}


	});

	return Biomaterials;
});
