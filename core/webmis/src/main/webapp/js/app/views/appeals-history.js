define(["collections/appeals"], function ()
{
	App.Views.AppealsHistory = View.extend({
		template:
			'<ul class="TreatmentHistory"></ul>' +
			'<div class="AllTreatments">' +
				'<a href="/patients/${patient.id}/appeals/">Все обращения пациента</a> <span class="Quant">(${quantity})</span>' +
			'</div>',

		initialize: function () {
			this.appealsHistoryList = new AppealsHistoryList({collection: this.options.appeals});
		},

		render: function () {
			//console.log('appeals',this.options.appeals)
			this.$el.html($.tmpl(this.template, {
				quantity: this.options.appeals.requestData.recordsCount,
				patient: this.options.appeals.patient
			}));

			this.assign({
				".TreatmentHistory": this.appealsHistoryList
			});

			return this;
		}
	});

	var AppealsHistoryList = View.extend({
		render: function () {
			_(this.collection.first(5)).each(function (a) {
				var appealsHistoryItem = new AppealsHistoryItem({model: a});
				this.$el.append(appealsHistoryItem.render().el);
			}, this);

			return this;
		}
	});

	var AppealsHistoryItem = View.extend({
		tagName: "li",

		template:
			'<a href="/appeals/${id}/">№ ${number}</a><span class="Label" style="float:right">${finance.name}</span>' +
			'<span class="Label">{{formatDate rangeAppealDateTime.start}}{{if rangeAppealDateTime.end}} — {{formatDate rangeAppealDateTime.end}}{{/if}}</span>' +
			'{{if department && department.name}}<span>${department.name}</span>{{/if}}' +
			'{{if execPerson.doctor && execPerson.doctor.name}}' +
				'<span>' +
				'${execPerson.doctor.name.last} ' +
				'{{if execPerson.doctor.name.first && execPerson.doctor.name.first.length}}${execPerson.doctor.name.first[0]}.{{/if}} ' +
				'{{if execPerson.doctor.name.middle && execPerson.doctor.name.middle.length}}${execPerson.doctor.name.middle[0]}.{{/if}}' +
				'</span>' +
			'{{/if}}',

		render: function () {
			//this.$el.html( $("#patient-card-page-history-item" ).tmpl( this.model.toJSON() ) );
			this.$el.html($.tmpl(this.template, this.model.toJSON()));

			return this;
		}
	});
} );
