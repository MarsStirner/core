define(function() {

	return Model.extend({
		idAttribute: 'id',
		initialize: function() {



		},

		onChange: function() {
			this.on('change:MKB', this.onChangeMkb, this);
			this.on('change:quotaType_id', this.onChangeQuotaTypeId, this);
			this.on('change:pacientModel_id', this.onChangePacientModelId, this);
		},
		onChangeMkb: function() {
			this.resetQuotaType();
			this.resetPacientModel();
			this.resetTreatment();
		},
		onChangeQuotaTypeId: function() {
			this.resetPacientModel();
			this.resetTreatment();
		},
		onChangePacientModelId: function() {
			this.resetTreatment();
		},
		offChange: function() {
			this.off('change:MKB', this.onChangeMkb, this);
			this.off('change:quotaType_id', this.onChangeQuotaTypeId, this);
			this.off('change:pacientModel_id', this.onChangePacientModelId, this);
		},

		resetQuotaType: function() {
			this.set('quotaType_id', '');
			this.set('quotaTypeName', '');
		},

		resetPacientModel: function() {
			this.set('pacientModel_id', '');
			this.set('patientModelName', '');
		},

		resetTreatment: function() {
			this.set('treatment_id', '');
			this.set('treatmentName', '');
		},

		urlRoot: function() {
			return '/api/v1/appeals/' + this.appealId + '/client_quoting';
		},
		parse: function(raw) {

			return raw.data;
		}


	})
});
