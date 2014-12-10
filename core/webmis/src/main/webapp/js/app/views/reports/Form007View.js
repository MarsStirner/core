define(["text!templates/reports/f007.html",
	"views/view-base",
	"views/ui/SelectView",
	"views/documents/documents",
	"collections/moves/BedProfilesDir",
	"collections/departments",
	"models/print/form007"
], function (tmpl, ViewBase, SelectView, Documents, BedProfilesDir) {

	var BedProfileFilter = ViewBase.extend({
		template: _.template("<div class='span6 bed-profile-list'></div>"),
		initialize: function () {
			this.bedProfilesDir = new BedProfilesDir();
			this.listenTo(this.bedProfilesDir, "selected", this.onBedProfileSelected);
			this.listenTo(this.bedProfilesDir, "deselected", this.onBedProfileDeselected);
		},
		onBedProfileSelected: function (event) {
			this.collection.add(event.bedProfile);
			console.log("bedProfile added", event);
		},
		onBedProfileDeselected: function (event) {
			this.collection.remove(event.bedProfile.id);
			console.log("bedProfile removed", event);
		},
		render: function () {
			Documents.Views.Base.prototype.render.call(this, {
				".bed-profile-list": new BedProfileList({collection: this.bedProfilesDir})
			});
		}
	});

	var BedProfileList = ViewBase.extend({
		template: _.template(
			"<select multiple data-placeholder='Профили коек (все по умолчанию)' class='bed-profiles'>" +
				"<%bedProfiles.each(function(bp){%>" +
				"<option value='<%=bp.id%>'><%=bp.get('name')%></option>" +
				"<%});%>" +
			"</select>"),
		data: function () {
			return {bedProfiles: this.collection};
		},
		initialize: function () {
			this.listenTo(this.collection, "reset", this.onCollectionReset);
			this.collection.fetch();
		},
		onCollectionReset: function () {
			this.render();
		},
		onListChange: function (event) {
			if (event.added) {
				this.collection.trigger("selected", {bedProfile: this.collection.get(event.added.id)});
			} else if (event.removed) {
				this.collection.trigger("deselected", {bedProfile: this.collection.get(event.removed.id)});
			}
		},
		render: function () {
			Documents.Views.Base.prototype.render.apply(this);

			this.$(".bed-profiles")
				.width("100%")
				.select2()
				.on("change", _.bind(this.onListChange, this));
			this.$(".select2-container").css("font-size", "1.2em");
			this.$(".select2-input").css("box-sizing", "content-box");
			this.$(".select2-choices").css({"padding": ".3em"});

			return this;
		}
	});

	return View.extend({
		template: tmpl,
		events: {
			"click .print": "print"
		},
		initialize: function () {
			_.bindAll(this);
			this.selectedBedProfiles = new Backbone.Collection([]);
		},
		initDepartments: function () {
			this.departments = new App.Collections.Departments();

			this.departments.setParams({
				filter: {
					hasBeds: true
				},
				limit: 0,
				sortingField: 'name',
				sortingMethod: 'asc'
			});

			this.departmentSelect = new SelectView({
				collection: this.departments,
				el: this.$('#departments'),
				selectText: 'name'
			});

			this.depended(this.departmentSelect);
		},
		print: function () {
			this.$(".print").button("disable");

			var endDate = Math.floor(moment(this.$(".report-end-date").datepicker("getDate"))
				.hour(this.$(".report-end-time").timepicker("getHour"))
				.minute(this.$(".report-end-time").timepicker("getMinute"))
				.toDate()
					.getTime());

			var form007 = new App.Models.PrintForm007({
				departmentId: this.$("#departments").val(),
				endDate: endDate,
				bedProfiles: this.selectedBedProfiles.pluck("id")
			});

			form007.fetch({
				success: this.displayReport,
				error: function () {
					this.$(".print").button("enable");
					App.Models.PrintForm007.prototype.errorHandler.apply(this, Array.prototype.slice.apply(arguments));
				}
			});
		},
		displayReport: function (form007) {
   //         var range = form007.get('rangeReportDateTime');
    //        var endDate = form007.get('endDate');//*1000;
//
  //          form007.set('endDate', endDate);
//            range.end = endDate;
 //           range.start = endDate - (60*60*24*1000);

			var self = this;
			var $iframe = $("<iframe id='myiframe'  name='myiframe' src='/pdf/' style='width: 100%;height: 400px;'></iframe>");
			var loaded = false;

			this.$(".print").button("enable");
			this.$("#iframe").html($iframe);

			$iframe.load(function () {
				if (!loaded) {
					loaded = true;
					var printData = form007.toJSON();
					var form = self.make("form", {
						"accept-charset": "utf-8",
						"action": "/pdf/",
						"method": "post"
					});
					var textarea = self.make("textarea", {
						name: "data"
					});
					var input = self.make("input", {
						name: "template",
						value: "f007"
					});
					textarea.innerHTML = JSON.stringify(printData);
					var doc = $("#myiframe").contents()[0];
					$(doc.body).html($(form).append(textarea).append(input)).hide();
					$(doc.body).find('form').trigger('submit');
				}
			});
		},
		render: function () {
			this.$el.html(_.template(this.template));
			this.$(".print").button();
			this.$(".date-input").datepicker();
			this.$(".time-input").timepicker({showPeriodLabels: false, defaultTime: 'now'});

			this.assign({
				".bed-profile-filter": new BedProfileFilter({collection: this.selectedBedProfiles})
			});

			this.initDepartments();

			return this;
		},
		cleanUp: function () {
			this.departmentSelect.cleanUp();
		}
	});
});
