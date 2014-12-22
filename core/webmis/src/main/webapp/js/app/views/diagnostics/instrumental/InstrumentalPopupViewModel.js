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
			code: false
		},
		initialize: function(attr, options) {


			//лечаший врач
			var appealDoctor = options.appeal.get('execPerson');

			if ((Core.Cookies.get('currentRole') === 'nurse-department') || (Core.Cookies.get('currentRole') === 'nurse-receptionist')) {
				//юзер не врач,назначивший доктор = лечащий врач

				this.set({
					'assignerId': appealDoctor.id,
					'assignerFirstName': appealDoctor.name.first,
					'assignerLastName': appealDoctor.name.last,
					'assignerMiddleName': appealDoctor.name.middle
				}, {
					validate: false
				});

			} else {
				//юзер врач ,назначивший = врач
				this.set({
					'assignerId': Core.Cookies.get('userId'),
					'assignerFirstName': Core.Cookies.get('doctorFirstName'),
					'assignerLastName': Core.Cookies.get('doctorLastName'),
					'assignerMiddleName': ''
				}, {
					validate: false
				});
			}

			this.set({
				'appealId': options.appeal.get('id'),
				'createDay': moment().toDate(),
				'createTime': moment().format('HH:mm'),
				'createDatetime': moment().format('YYYY-MM-DD HH:mm:ss'),
				'finance': options.appeal.get('appealType').get('finance').get('id'),
				'patientId': options.appeal.get('patient').get('id'),
				'plannedDay': moment().add('day', 1).toDate(),
				'plannedTime': '07:00',
				'plannedDatetime': moment().add('day', 1).hour(7).minutes(0).seconds(0).format('YYYY-MM-DD HH:mm:ss')
			}, {
				validate: false
			});


			this.instrumntalGroups = new InstrumntalGroups();
			this.instrumntalGroups.parents = true;
			this.instrumntalGroups.setParams({
				'filter[code]': 3,
				'filter[view]': 'tree'
			});

			this.instrumntalResearchs = new InstrumntalGroups();

			this.on('change:createDay change:createTime', this.calculateCreateDatetime, this);
			this.on('change:plannedDay change:plannedTime', this.calculatePlannedDatetime, this);

		},

		calculateCreateDatetime: function() {
			var createDay = moment(this.get('createDay')).format('YYYY-MM-DD');
			var createTime = this.get('createTime') + ':00';
			this.set('createDatetime', createDay + ' ' + createTime);
		},

		calculatePlannedDatetime: function() {
			var plannedDay = moment(this.get('plannedDay')).format('YYYY-MM-DD');
			var plannedTime = this.get('plannedTime') + ':00';
			this.set('plannedDatetime', plannedDay + ' ' + plannedTime);
		},

		validateModel: function(attrs) {
			var errors = [];
			console.log('attrs', attrs);

			if (!attrs.code) {
				errors.push({
					name: 'code',
					message: 'Не выбрано исследование. '
				});
			}
			if (!attrs.plannedTime) {
				errors.push({
					name: 'plannedTime',
					message: 'Не задано планируемое время исследования. '
				});
			}
			if (!attrs.createTime) {
				errors.push({
					name: 'createTime',
					message: 'Не задано время создания направления. '
				});
			}
			if (!attrs.createDay) {
				errors.push({
					name: 'createDay',
					message: 'Не задан день создания направления. '
				});
			}

			if (!attrs.createDatetime || !moment(attrs.createDatetime, 'YYYY-MM-DD HH:mm:ss').isValid()) {
				// errors.push({
				// 	name: 'createDatetime',
				// 	message: 'Некорректные дата или время создания направления'
				// });
			} else {
				if ((moment(attrs.createDatetime, 'YYYY-MM-DD HH:mm:ss').startOf('day').diff(moment().startOf('day'), 'days') < 0)) {
					errors.push({
						name: 'createDatetime',
						message: 'Дата создания не может быть меньше текущей. '
					});
				}
			}

			if (!attrs.plannedDatetime || !moment(attrs.plannedDatetime, 'YYYY-MM-DD HH:mm:ss').isValid()) {
				// errors.push({
				// 	name: 'plannedDatetime',
				// 	message: 'Некорректные планируемые дата или время выполнения исследования'
				// });
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
