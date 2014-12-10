/**
 * User: FKurilov
 * Date: 05.03.13
 */
define(function () {
	var Widget = {};

	////////////////////////////////////////////////////////
	// Поля для даты/времени
	////////////////////////////////////////////////////////

	/**
	 * Предоставляет обёртку для поля ввода даты
	 * @type {*}
	 */
	Widget.Date = Backbone.View.extend({
		tag: "input",

		attributes: {
			"type": "text",
			"class": "date-input",
			"data-min-date": "",
			"data-max-date": ""
		},

		events: {
			"change": "onChange"
		},

		onChange: function (event) {

		},

		onDateSelect: function (event) {

		},

		setMinDate: function (date) {

		},

		render: function () {
			this.$el
				.prop("placeholder", "дд.мм.гггг")
				.mask("99.99.9999")
				.datepicker({
					minDate: this.$el.data("min-date"),
					mxnDate: this.$el.data("max-date"),
					onSelect: this.onDateSelect
				})
				.wrap("<div class='date'></div>")
				.after(
					$("<i class='icon-calendar date-icon'></i>").on("click", function () {
						$(this).before().datepicker("show");
					})
				);

			return this;
		}
	});

	/**
	 * Предоставляет обёртку для поля ввода диапазона дат
	 * @type {*}
	 */
	Widget.DateRange = Backbone.View.extend({
		initialize: function (options) {
			this.start = new Widget.Date({el: this.$("daterange-start")});
			this.end = new Widget.Date({el: this.$("daterange-end")});
		}
	});

	/**
	 * Предоставляет обёртку для поля выбора времени
	 * @type {*}
	 */
	Widget.Time = Backbone.View.extend({
		tag: "input",

		attributes: {
			"type": "text",
			"class": "time-input",
			"data-default-time": "00:00"
		},

		render: function () {
			this.$el
				.prop("placeholder", "чч:мм")
				.mask("99:99")
				.timepicker({
					showPeriodLabels: false,
					defaultTime: this.$el.data("default-time") || "00:00"
				})
				.wrap("<div class='time'></div>")
				.after(
					$("<i class='icon-time time-icon'></i>").on("click", function () {
						$(this).before().timepicker("show");
					})
				);

			return this;
		}
	});

	/**
	 * Предоставляет обёртку для выбора даты с указанием времени
	 * @type {*}
	 */
	Widget.DateTime = Backbone.View.extend({
		className: "datetime",

		initialize: function (options) {
			this.date = new Widget.Date({el: this.options.dateEl});
			this.time = new Widget.Time({el: this.options.timeEl});




			/*this.dateEl = options.dateEl;
			this.timeEl = options.timeEl;

			$(this.dateEl)
				.datepicker({onSelect: this.onDateSelect})
				.mask("99.99.9999");
			$(this.timeEl)
				.timepicker({onSelect: this.onTimeSelect})
				.mask("99:99");

			$(this.dateEl).on("change", this.onDateChange);
			$(this.timeEl).on("change", this.onTimeChange);

			this.datetime = options.datetime || new Date();*/
		},

		render: function () {

			return this;
		},

		commitDatetime: function (event) {
			this.trigger("change", {datetime: this.datetime});
		},

		onDateSelect: function (event) {
			console.log(event);
		},

		onDateChange: function (event) {
			console.log(event);
		},

		onTimeSelect: function (event) {
			console.log(event);
		},

		onTimeChange: function (event) {
			console.log(event);
		}

	});

	/**
	 * Предоставляет обёртку для выбора дипозона дат с указанием времени
	 * @type {*}
	 */
	Widget.DateTimeRange = Backbone.View.extend({
		initialize: function (options) {

		}
	});

	////////////////////////////////////////////////////////
	// Навигация
	////////////////////////////////////////////////////////

	Widget.Nav = Backbone.View.extend({});

	Widget.Breadcrumbs = Backbone.View.extend({});

	Widget.Paginator = Backbone.View.extend({});

	////////////////////////////////////////////////////////
	// Списки/Таблицы
	////////////////////////////////////////////////////////

	Widget.Grid = Backbone.View.extend({});

	Widget.Select = Backbone.View.extend({});

	////////////////////////////////////////////////////////
	// Кнопки
	////////////////////////////////////////////////////////

	Widget.Button = {};

	Widget.Button.Base = Backbone.View.extend({});

	Widget.Button.Main = Backbone.View.extend({});

	Widget.Button.Add = Backbone.View.extend({});

	Widget.Button.Edit = Backbone.View.extend({});

	Widget.Button.Delete = Backbone.View.extend({});

	Widget.Button.Search = Backbone.View.extend({});

	Widget.Button.Filter = Backbone.View.extend({});

	Widget.Button.Print = Backbone.View.extend({});

	Widget.Button.Cancel = Backbone.View.extend({});

	Widget.ButtonDropdown = Backbone.View.extend({});

	Widget.ButtonGroup = Backbone.View.extend({});

	////////////////////////////////////////////////////////
	// Тултипы
	////////////////////////////////////////////////////////

	Widget.Tooltip = Backbone.View.extend({});

	Widget.ErroTooltip = Backbone.View.extend({});

	////////////////////////////////////////////////////////
	// Нотификация
	////////////////////////////////////////////////////////

	return Widget;
});