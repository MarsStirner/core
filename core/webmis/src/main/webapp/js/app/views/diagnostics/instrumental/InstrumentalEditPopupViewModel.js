define(function(require) {
	//var Backbone = require('backbone');
	var InstrumntalGroups = require('collections/diagnostics/instrumental/InstrumntalGroups');
	var InstrumentalResearchs = require('collections/diagnostics/instrumental/InstrumentalResearchs');

	return Backbone.Model.extend({
		defaults: {
			mkbId: '',
			urgent: false,
			saveButtonState: 'disable',
			doctorFirstName: '',
			doctorMiddleName: '',
			doctorLastName: '',
		},
		initialize: function(attr, options) {

			this.model = options.model;

			var createDate = moment(this.model.getProperty('assessmentBeginDate'), 'YYYY-MM-DD HH:mm:ss');
			var createDay = createDate.toDate();
			var createTime = createDate.format('HH:mm');

			var plannedDate =  moment(this.model.getProperty('plannedEndDate'), 'YYYY-MM-DD HH:mm:ss');
			var plannedDay = plannedDate.toDate();
			var plannedTime = plannedDate.format('HH:mm');

			//Диагноз
			var diagnosis = this.model.getProperty('Направительный диагноз', 'value');
			var mkbCode, mkbText;
			if (diagnosis) {
				diagnosis = diagnosis.split(/\s+/);
				var mkbCode = diagnosis[0];
				var mkbText = (diagnosis.splice(1)).join(' ');
			}

			this.set({
				'createDay': createDay,
				'createTime': createTime,

				'plannedDay': plannedDay,
				'plannedTime': plannedTime,
				'plannedDatetime': this.model.getProperty('plannedEndDate'),

				'assignerId': this.model.getProperty('assignerId'),
				'assignerFirstName': this.model.getProperty('assignerFirstName'),
				'assignerLastName': this.model.getProperty('assignerLastName'),
				'assignerMiddleName': this.model.getProperty('assignerMiddleName'),

				'executorId': this.model.getProperty('executorId'),
				'executorFirstName': this.model.getProperty('doctorFirstName'),
				'executorLastName': this.model.getProperty('doctorLastName'),
				'executorMiddleName': this.model.getProperty('doctorMiddleName'),

				'mkbId': this.model.getProperty('Направительный диагноз', 'valueId'),
				'mkbText': mkbText,
				'mkbCode': mkbCode,

				'finance': this.model.getProperty('finance'),
				'urgent': this.model.getProperty('urgent')
			}, {
				validate: false
			});


			this.on('change:plannedDay change:plannedTime', this.calculatePlannedDatetime, this);

			//this.on('change', this.updateSaveButtonState, this);
		},

		calculatePlannedDatetime: function() {
			var plannedDay = moment(this.get('plannedDay')).format('YYYY-MM-DD');
			var plannedTime = this.get('plannedTime') + ':00';
			this.set('plannedDatetime', plannedDay + ' ' + plannedTime);
		},

		validateModel: function(attrs) {
			var errors = [];
			console.log('attrs', attrs);

			if (!attrs.plannedTime) {
				errors.push({
					name: 'plannedTime',
					message: 'Не задано планируемое время исследования. '
				});
			}

			if (!attrs.plannedDatetime || !moment(attrs.plannedDatetime, 'YYYY-MM-DD HH:mm:ss').isValid()) {
				//
			} else {
				if (!(moment(attrs.plannedDatetime, 'YYYY-MM-DD HH:mm:ss').diff(moment()) > -(60 * 1000))) {
					errors.push({
						name: 'plannedDatetime',
						message: 'Планируемая дата выполнения не может быть меньше текущей. '
					});
				}
			}


			return errors.length > 0 ? errors : false;
		}

	});

});
