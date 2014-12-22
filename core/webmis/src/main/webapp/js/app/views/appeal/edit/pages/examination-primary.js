define([
	"text!templates/appeal/edit/pages/examination-primary.tmpl",
	"text!templates/appeal/edit/pages/examination-primary-repeated.tmpl",
	"models/dynamic/examination-old",
	"models/flat-directory",
	"collections/dictionary-values",
	"views/appeal/edit/popups/thesaurus",
	"views/mkb-directory",
	"views/appeal/edit/pages/examination-primary-preview"
], function (primaryTmpl, primaryRepeatedTmpl, ExaminationOldModule) {

	var thesaurus = require("views/appeal/edit/popups/thesaurus");

	App.Views.ExaminationPrimary = Form.extend({
		className: "ContentHolder",

		canPrint: false,

		dictionariesToLoad: [
			{name: "hospitalizationPointTypes", id: 19, fd: true},
			{name: "stateTypes", id: 8, thesaurus: true}
		],

		dictionaries: [],

		//TODO: TEMP
		templates: {
			139: primaryTmpl,
			2456: primaryRepeatedTmpl
		},

		events: {
			"change .SetComment :checkbox": "toggleComment",
			"click .FormLink": "toggleThesaurus",
			"click .MKBLauncher": "toggleMKB",
			"change .ExamAttr": "onExamAttrChange",
			"click .SaveBtn": "onSaveExamClick",
			"click .CopyFromPrevious": "onCopyFromPreviousClick",
			"click .Cancel": "onCancelClick",
			"keyup [name='diagnosis[mkb][code]']": "onMKBCodeKeyUp"
		},

		initialize: function (options) {
			/*this.struct = new App.Dynamic.ExaminationOld({
				type: "primary",
				appealId: this.options.appealId,
				structId: this.options.structId
			});*/

			if (!this.model) {
				this.model = new App.Dynamic.ExaminationOld({
					type: "primary",
					appealId: this.options.appealId,
					structId: this.options.structId
				});
			}

			this.modelIsNew = this.model.isNew();

			this.model.on("change:id", this.onStructLoaded, this);
			this.model.on("sync", function () {
				this.model.initializeRelations();

				pubsub.trigger('noty', {text:'Документ ' + (this.modelIsNew ? 'создан' : 'изменен')});

				this.trigger("change:viewState", {type: "examination-primary-preview", options: {model: this.model}});
			}, this);

			this.model.on("error", this.onSaveError, this);

			this.thesaurus = new App.Views.Thesaurus();
			this.thesaurus.on("thesaurus:confirmed", this.onThesaurusConfirmed, this);

			this.mkbDirectory = new App.Views.MkbDirectory();
			this.mkbDirectory.on("selectionConfirmed", this.onMKBConfirmed, this);

			this.initWithDictionaries(this.dictionariesToLoad, this.onDictionariesLoaded, this, true);
		},

		cleanUp: function () {
			this.thesaurus.off(null, null, this);
			this.thesaurus.model.off(null, null, this.thesaurus);
			thesaurus.termDispatcher.off(null, null, this.thesaurus);
		},

		onDictionariesLoaded: function (dicts) {
			this.dictionaries = dicts;

			this.loadStruct();
		},

		loadStruct: function () {
			if (this.model.get("id"))
				this.onStructLoaded();
			else
				this.model.fetch();
		},

		onStructLoaded: function () {
			this.examAttributes = this.model.get("group").at(1).get("attribute");

			var generalAttrs = this.model.get("group").at(0).get("attribute");

			this.examBeginDate = generalAttrs.find(function (attr) {
				return attr.get("name") === "assessmentBeginDate";
			}).get("properties").first();
			this.examEndDate = generalAttrs.find(function (attr) {
				return attr.get("name") === "assessmentEndDate";
			}).get("properties").first();

			if (this.model.isNew()) {
				this.examBeginDate.set("value", new Date().getTime());
			}

			this.render();
		},

		onExamAttrChange: function (event) {
			this.setExamAttr({
				attrId: $(event.currentTarget).data("examattr-id"),
				propertyType: $(event.currentTarget).hasClass("ValueId") ? "valueId" : "value",
				//propertyType: "value",
				value: $(event.currentTarget).val()
			});
		},

		setExamAttr: function (opts) {
			var examAttr = this.examAttributes.find(function (a) {
				return a.get("typeId") == opts.attrId;
			});

			if (examAttr) {
				// теперь ищем property по имени
				// value для текстовых значений, valueId для айдишников из справочников
				var valueProperty = examAttr.get("properties").find(function (p) {
					return p.get("name") == opts.propertyType;
				});

				if (!valueProperty) {
					valueProperty = new ExaminationOldModule.Property({name: opts.propertyType, value: ""});
					examAttr.get("properties").add(valueProperty);
				}

				var $input = this.$("[data-examattr-id="+opts.attrId+"]");

				//$input.removeClass("WrongField");

				var isValid = valueProperty.set("value", opts.value);

				$input.toggleClass("WrongField", !isValid);

				// if set from popup
				if ($input.val() != opts.value || opts.displayText) {
					$input.val(opts.displayText || opts.value).change();
				}
			}
		},

		onThesaurusConfirmed: function (event) {
			this.setExamAttr({
				attrId: event.attrId,
				propertyType: event.propertyType,
				value: event.selectedTerms
			});
		},

		onMKBConfirmed: function (event) {
			var sd = event.selectedDiagnosis;
			this.setExamAttr({
				attrId: this.mkbAttrId,
				propertyType: "valueId",
				value: sd.get("id") || sd.get("code"),
				displayText: sd.get("code") || sd.get("id")
			});

			this.$("input[name='diagnosis[mkb][diagnosis]']").val(sd.get("diagnosis"));
		},

		onMKBCodeKeyUp: function (event) {
			$(event.currentTarget).val(Core.Strings.toLatin($(event.currentTarget).val()));
		},

		toggleThesaurus: function (event) {
			event.preventDefault();
			var thesaurusCode = $(event.currentTarget).parent().find(".ExamAttr").data("thesaurus-code");
			if (thesaurusCode) {
				this.thesaurus.open({
					code: thesaurusCode,
					terms: $(event.currentTarget).parent().find(".ExamAttr").val(),
					attrId: $(event.currentTarget).parent().find(".ExamAttr").data("examattr-id"),
					//propertyType: $(event.currentTarget).next().is("select") ? "valueId" : "value"
					propertyType: "value"
				});
			}
		},

		toggleMKB: function (event) {
			event.preventDefault();

			this.mkbAttrId = $(event.currentTarget).data("mkb-examattr-id");

			this.mkbDirectory.open();
		},

		toggleComment: function (event) {


			//if (confirmedClear) {
			var $cb = $(event.currentTarget);
			var commentIsEmpty = $cb.parents(".SetComment").find(".ExamAttr").val().length === 0;
			var confirmedClear = true;

			if (!commentIsEmpty) {
				confirmedClear = confirm("Поле будет очищено и скрыто. Продолжить?");
			}

			if (confirmedClear) {
				var $toggleTargets = $cb.parents(".SetComment").find(".FormLink, .RichTextWrapper");
				$toggleTargets.toggle();
				$cb.parents(".SetComment").find(".ExamAttr").val("").change();
			} else {
				$cb.prop("checked", !$cb.prop("checked"));
			}

			//}
		},

		save: function (event) {
			var datesValid = (this.examBeginDate && this.examEndDate && this.examEndDate.get("value")) ?
				parseInt(this.examBeginDate.get("value")) < parseInt(this.examEndDate.get("value")) :
				true;

			this.$("#exam-begin-date, #exam-end-date").closest(".DateTime").find(".DatePeriod, .HourPicker").toggleClass("WrongField", !datesValid);

			if (!datesValid) {
				$('html, body').animate({ scrollTop: this.$("#exam-begin-date").offset().top - 30 }, 'fast');
			}

			return datesValid && Form.prototype.save.apply(this, event);
		},

		onSaveExamClick: function (event) {
			if (this.model.isValid()) {
				if (this.examBeginDate) this.examBeginDate.off(null);
				if (this.examBeginDate) this.examBeginDate.off(null);
				if (this.examEndDate) this.examEndDate.off(null);
				if (this.examEndDate) this.examEndDate.off(null);

				var readyToSave = this.save(event);
				if (readyToSave) {
					/*if (!this.examEndDate.get("value")) {
						this.examEndDate.set("value", new Date().getTime());
					}*/
					this.toggleSaveBtn(false);
				} else {
					this.showInputs();
				}
			} else {
				// show error, highlight invalid fields
			}
		},

		onSaveError: function () {
			this.connectDates();
			this.toggleSaveBtn(true);
		},

		onCancelClick: function (event) {
			event.preventDefault();

			this.trigger("change:viewState", {type: "examinations", options: {}});
		},

		onCopyFromPreviousClick: function (event) {
			$(event.currentTarget).attr("disabled", true);

			var prevExam = new App.Dynamic.ExaminationOld({
				type: "primary",
				appealId: this.model.appealId,
				structId: this.options.structId || this.model.get("typeId"),
				copy: true
			});

			prevExam.on("change:id", function () {
				var prevExamAttrs = prevExam.get("group").at(1).get("attribute");
				var newExamAttrs = this.model.get("group").at(1).get("attribute");

				newExamAttrs.each(function (attr) {
					var prevAttr = prevExamAttrs.find(function (prevAttr) {
						return prevAttr.get("typeId") === attr.get("typeId");
					});

					if (prevAttr) {
						attr.set("properties", prevAttr.get("properties").toJSON());
					}
				});

				this.model.get("group").at(1).set("attribute", prevExam.toJSON()[0].group[1].attribute);

				console.log(this.model.toJSON());

				this.render();
			}, this);

			prevExam.fetch();
		},

		showInputs: function () {
			this.$(".SetComment").each(function () {
				$(this).find(".FormLink,.RichTextWrapper").hide();
				if ($(this).find("textarea").val() || $(this).find("textarea").hasClass("Mandatory")) {
					$(this).find(".FormLink,.RichTextWrapper").show();
					$(this).find(":checkbox").attr("checked", true);
				}
			});
		},

		connectDates: function () {
			if (this.examBeginDate) this.examBeginDate.connect("value", this.$("#exam-begin-date"), this.$el);
			if (this.examBeginDate) this.examBeginDate.connect("value", this.$("#exam-begin-time"), this.$el);
			if (this.examEndDate) this.examEndDate.connect("value", this.$("#exam-end-date"), this.$el);
			if (this.examEndDate) this.examEndDate.connect("value", this.$("#exam-end-time"), this.$el);
		},

		toggleSaveBtn: function (enabled) {
			this.$(".SaveBtn").attr("disabled", !enabled).toggleClass("Disabled", !enabled);
		},

		render: function() {
			var self = this;

			var examFlatJSON = this.model.getFlatAttrs();

			this.$el.html($.tmpl(this.templates[this.options.structId || this.model.get("typeId")], {dicts: this.dictionaries, exam: examFlatJSON, appealId: this.model.appealId}));

			this.thesaurus.render();
			this.mkbDirectory.render();

			UIInitialize(this.$el);

			this.showInputs();
			this.connectDates();

			this.$(".CopyFromPrevious").button({icons: {primary: "icon-copy"}});
			this.$(".MKBLauncher").button({icons: {primary: "icon-book"}});
			this.$(".SaveBtn").button();

			// Ограничение ввода для полей формата Double
			self.$('.RestrictFloat').keypress(function(eve) {
				if ((eve.which != 46 || $(this).val().indexOf('.') != -1) && (eve.which < 48 || eve.which > 57) || (eve.which == 46 && $(this).caret().start == 0) ) {
					eve.preventDefault();
				}
				// this part is when left part of number is deleted and leaves a . in the leftmost position. For example, 33.25, then 33 is deleted
				self.$('.RestrictFloat').keyup(function(eve) {
					if($(this).val().indexOf('.') == 0) {
						$(this).val($(this).val().substring(1));
					}
				});
			});

			this.$(".HourPicker").mask("99:99");

			var i = 0;
			this.$("input[type=text],select,.RichText").each(function () {
				$(this).prop("tabindex", ++i);
			});

			var patientSex = Cache.Patient.get("sex").length ? (Cache.Patient.get("sex") == "male" ? 1 : 2) : 0;

			this.$("input[name='diagnosis[mkb][code]']").autocomplete({
				source: function (request, response) {
					$.ajax({
						url: "/data/dir/mkbs/",
						dataType: "jsonp",
						data: {
							filter: {
								view: "mkb",
								code: request.term,
								sex: patientSex
							}
						},
						success: function (raw) {
							response($.map(raw.data, function (item) {
								return {
									label: item.code + " " + item.diagnosis,
									value: item.code,
									id: item.id,
									diagnosis: item.diagnosis
								}
							}));
						}
					});
				},
				minLength: 2,
				select: function (event, ui) {
					self.mkbAttrId = $(this).data("mkb-examattr-id");

					self.setExamAttr({
						attrId: self.mkbAttrId,
						propertyType: "valueId",
						value: ui.item.id,
						displayText: ui.item.value
					});

					self.$("input[name='diagnosis[mkb][diagnosis]']").val(ui.item.diagnosis);
				}
			}).on("keyup", function () {
					if (!$(this).val().length) {
						self.setExamAttr({
							attrId: self.mkbAttrId,
							propertyType: "valueId",
							value: "",
							displayText: ""
						});

						self.$("input[name='diagnosis[mkb][diagnosis]']").val("");
					}
				});

			return this;
		}
	});

	return App.Views.ExaminationPrimary;
});
