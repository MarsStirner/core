define(["text!templates/pages/prints.tmpl",
	"models/patient",
	"models/print/appeal",
	"models/dynamic/examination-old",
	"models/print/form007",
	"views/print"], function (template) {

	return App.Views.Prints = View.extend({
		events: {
			"click .Actions.PrintAppeal": "showPrintAppeal",
			"click .Actions.PrintPatient": "showPrintPatient",
			"click .Actions.Print007": "showPrint007",
			"click .Actions.PrintConsentExam": "showPrintConsentExam",
			"click .Actions.PrintExam": "showPrintExam",
			"click .Actions.Decrease": "decreaseDate",
			"click .Actions.Increase": "increaseDate"
		},

		showPrint007: function () {

			console.log('start-date',$("#start-date").datepicker("getDate").getTime());
			console.log('end-date',$("#end-date").datepicker("getDate").getTime());
			console.log('departmentId',$("#department-id").val())

			var form007 = new App.Models.PrintForm007({
				departmentId: $("#department-id").val(),
				beginDate: $("#start-date").datepicker("getDate").getTime(),
				endDate: $("#end-date").datepicker("getDate").getTime()
			});


			new App.Views.Print({
				model: form007,
				template: "007"
			});

			form007.fetch();
		},

		increaseDate: function (event) {
			event.preventDefault();
			this.setDate(1);
		},

		decreaseDate: function (event) {
			event.preventDefault();
			this.setDate(-1);
		},
		setDate: function (increment) {
			increment = increment || 0;

			var $startDateInput = this.$("#start-date");
			var $endDateInput = this.$("#end-date");

			var startDate = $startDateInput.datepicker("getDate");
			var endDate = $endDateInput.datepicker("getDate");

			if (_.isDate(startDate)) startDate.setDate(startDate.getDate() + increment);
			if (_.isDate(endDate)) endDate.setDate(endDate.getDate() + increment);

			$startDateInput.datepicker("setDate", startDate);
			$endDateInput.datepicker("setDate", endDate);

			this.$("#start-date").change();

		},

		initFromToDatepicker: function(){
			var now = new Date();
			var startDate = new Date();
			var endDate = new Date();

			//если между 0 и 8 часами, то начальная дата минус один день
			if (now.getHours() >= 0 && now.getHours() < 8) {
				startDate.setDate(startDate.getDate() - 1);
			}

			//конечная дата равна начальная плюс один день
			endDate.setDate(startDate.getDate() + 1);

			//чё эта?
			this.$("#start-date").datepicker("setDate", startDate);
			this.$("#end-date").datepicker("setDate", endDate);

			this.setDate();

		},

		showPrintPatient: function () {
			var Patient = new App.Models.Patient({
				id: $("#patient-id").val()
			});
			new App.Views.Print({
				model: Patient,
				template: $("#template-name").val().length ? $("#template-name").val() : "processing-agreement"
			});
			Patient.fetch();
		},

		showPrintAppeal: function () {
			var PrintAppeal = new App.Models.PrintAppeal({
				id: $("#appeal-id").val()
			});
			new App.Views.Print({
				model: PrintAppeal,
				template: $("#template-name").val().length ? $("#template-name").val() : "003"
			});
			PrintAppeal.fetch();
		},

		showPrintConsentExam: function () {
			var Patient = new App.Models.Patient({
				id: $("#patient-id").val()
			});
			new App.Views.Print({
				model: Patient,
				template: $("#template-name").val().length ? $("#template-name").val() : "consent_to_the_examination"
			});
			Patient.fetch();
		},

		showPrintExam: function () {
			var examId = $("#exam-id").val();
			var patientId = $("#patient-id").val();
			var appealId = $("#appeal-id").val();

			var templateName = $("#template-name").val();

			var exam = new App.Dynamic.ExaminationOld({id: examId});

			exam.on("change", function () {
				var summaryAttrs = exam.toJSON()[0]["group"][0]["attribute"];

				var examName = summaryAttrs[1]["properties"][0]["value"];
				var examEndDate = summaryAttrs[3]["properties"][0]["value"];
				var examDoctorName = [
					summaryAttrs[4]["properties"][0]["value"],
					summaryAttrs[5]["properties"][0]["value"],
					summaryAttrs[6]["properties"][0]["value"]
				].join(" ");
				var examDoctorSpecs = summaryAttrs[7]["properties"][0]["value"];

				var data = {
					patientId: patientId,
					appealId: appealId,
					examId: examId,
					examName: examName,
					examEndDate: examEndDate,
					examDoctorName: examDoctorName,
					examDoctorSpecs: examDoctorSpecs,
					attributes: exam.getFilledAttrs()
					/*_(exam.getFlatAttrs())
					 .map(function (a, k) { return {name: k, value: a}; })
					 .filter(function (a) { return a.value && a.value.length && a.value !== "0.0"; })*/
				};

				console.log(data);

				new App.Views.Print({
					data: data,
					template: templateName.length ? templateName : "examination"
				});
			}, this);

			exam.fetch();
		},

		render: function () {
			this.$el.append(template);

			UIInitialize(this.el);


			var now = new Date();
			var startDate = new Date();
			var endDate = new Date();

			//если между 0 и 8 часами, то начальная дата минус один день
			if (now.getHours() >= 0 && now.getHours() < 8) {
				startDate.setDate(startDate.getDate() - 1);
			}

			//конечная дата равна начальная плюс один день
			endDate.setDate(startDate.getDate() + 1);

			//чё эта?
			this.$("#start-date").datepicker("setDate", startDate);
			this.$("#end-date").datepicker("setDate", endDate);

			this.setDate();



			return this
		}
	});
});
