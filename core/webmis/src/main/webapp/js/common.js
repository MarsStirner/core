// Глобальные константы
var GUI_VERSION = "1.3.45.3";
var CORE_VERSION;

DEBUG_MODE = true;

ROLES = {
	DEFAULT: "default",
	NURSE_RECEPTIONIST: "nurse-receptionist",
	DOCTOR_RECEPTIONIST: "doctor-receptionist",
	NURSE_DEPARTMENT: "nurse-department",
	DOCTOR_DEPARTMENT: "doctor-department",
	CHIEF: "chief"
};

DEFAULT_ANIMATION_TIME = 300;
//DATA_PATH = "http://10.1.2.106:8080/tmis-ws-medipad/rest/tms-registry/";
DATA_PATH = "/data/";


APPOINTMENTS_HOST = "10.128.225.200:5000";
APPOINTMENTS_PATH = "http://" + APPOINTMENTS_HOST + "/schedule/appointment/";

App = {};
App.Models = {};
App.Dynamic = {};
App.Collections = {};
App.Views = {};

//глобальный pub sub
pubsub = _.extend({}, Backbone.Events);


// Загруженная информация
Data = {};
Cache = {};

// use models/mode-base.js instead
Model = Backbone.RelationalModel.extend({
	idAttribute: "_id",

	connect: function (key, $inputs, context) {
		var model = this;

		if (typeof $inputs == "string") {
			$inputs = $(":input[name='" + $inputs + "']", context);
		}

		checkForWarnings($inputs.length ? 1 : null, $inputs.selector + " was not found" + (context ? " in " + context.toArray() : ""));

		function setValue ($input) {
			if (model.clean) {
				model.clean();
			}

			// Вносим изменения для даты
			if ($input.is(".SelectDate")) {
				// Если помимо даты есть ещё и время
				var relation = $input.data("relation");
				if (relation) {
					var $relation = $(relation, context);

					var value = $relation.val();
					if (Core.Date.toInt($input.val() + " " + value) == parseInt(model.get(key), 10)) {
						return false;
					}
				} else {
					if (Core.Date.toInt($input.val()) == parseInt(model.get(key), 10)) {
						return false;
					}
				}
				$input.val(model.get(key) ? Core.Date.format(model.get(key)) : "");

				return true;
			}

			// Если время
			if ($input.is(".HourPicker") && model.get(key)) {
				var DateTime = new Date(model.get(key));
				var formattedTime = Core.Date.zeroFirst(DateTime.getHours()) + ":" + Core.Date.zeroFirst(DateTime.getMinutes());

				if (formattedTime != $input.val()) {
					$input.val(formattedTime);
				}

				return true;
			}

			if ($input.is(":radio")) {
				var $radio = $inputs.filter(":radio");

				if (!model.isNew()) {
					$radio.removeAttr("checked").each(function () {
						var $this = $(this);

						if ($this.val() == model.get(key)) {
							$this.attr("checked", "checked");
							$this.change();
						}
					});
				}

				return true;
			}

			/*if ($input.is("select")) {
			 var founded = false;

			 $input.find("option").each(function () {
			 var $option = $(this);

			 if ($option.val() == model.get(key)) {
			 $option
			 .attr ("selected", "selected")
			 .change()
			 .siblings()
			 .removeAttr("selected");
			 $input.change();

			 founded = true;

			 return false;
			 }
			 });
			 if (!founded) {
			 $input.find("option").eq(0).attr("selected", "selected").change().siblings().removeAttr ("selected");
			 $input.change();
			 }

			 return true;
			 }*/

			if ($input.is(":checkbox")) {
				$input.prop("checked", !!model.get(key));
			}

			if ($input.val() === model.get(key)) {
				return false;
			}

			$input.val(model.get(key)).change();

			return true;
		}

		$inputs.each(function () {
			var $input = $(this);

			$input.data("connect:disconnected", false);

			if ($input.data("connect:model") == model && $input.data("connect:key") == key) {
				return true
			}

			$input.data("connect:model", model);
			$input.data("connect:key", key);

			var relation = $input.data("relation");
			if (relation) {
				var $relation = $(relation, context);
			}

			$input.on("change keyup click _update", function (event) {
				// Это проще, чем удалять и навешивать события каждый раз
				if ($input.data("connect:disconnected")) {
					return;
				}

				var object = {};

				if ($input.is(".SelectDate")) {
					if (relation) {
						var value = $relation.val();

						object[key] = Core.Date.toInt($input.val() + " " + value) || "";
					} else {
						object[key] = Core.Date.toInt($input.val()) || "";
					}
				} else if ($input.is(".HourPicker")) {

					if (relation) {
						// Если модель (а соответственно и поле) пустые — выставляем текущую дату
						if (!model.get(key)) {
							var currentDate = new Date();
							$relation.val(Core.Date.format(currentDate.getTime()));
						}

						$relation.trigger("_update");
					}

					return;
				} else if ($input.is(":checkbox")) {
					object[key] = !!$input.prop("checked");
				} else {
					object[key] = $input.val();
				}

				if (!_.isEmpty(object)) {
					model.set(object);
				}
			});


			model.on("change", function () {
				if ($input.data("connect:disconnected")) {
					return;
				}

				setValue($input);
			});
			setValue($input);
		});
	},

	// Отключение привязки. Не удаляет сами обработчики, но выключает реакцию в них.
	disconnect: function (model, key, $inputs, context) {
		if (typeof $inputs == "string") {
			$inputs = $(":input[name='" + $inputs + "']", context);
		}
		$inputs.each(function () {
			var $input = $(this);

			$input.data("connect:disconnected", true);
		});
	},


	parse: function (data) {
		if (data.requestData && data.requestData.coreVersion) {
			CORE_VERSION = data.requestData.coreVersion;
			VersionInfo.show();
		}
		return data.data ? data.data : data
	},

	fetch: function (options) {
		options = options || {};
		var self = this;

		return Backbone.Model.prototype.fetch.call(self, options);
	},

	// Переопределяем стандартный метод, чтобы JSON не содержал ссылок на другие модели, а парсил вообще всё.
	toJSON: function () {
		return JSON.parse(JSON.stringify(_.clone(this.attributes)))
	},

	sync: function (method, model, options) {
		options.dataType = "jsonp";
		options.url = model.url();
		options.contentType = 'application/json';

		if (method == "create" || method == "update") {
			options.data = JSON.stringify({
				requestData: {},
				data: model.toJSON()
			});
		}
		return Backbone.sync(method, model, options);
	},
	errorHandler: function (model, xhr) {
		if (xhr.responseText && xhr.responseText.length) {
			try {
				var json = JSON.parse(xhr.responseText);

				if (json.errorCode == 3) {
					Core.Cookies.clear();
					//window.location.href = "/auth/";
					return;
				} else if (json.errorCode == 261) {
					json.errorMessage += ", обновите страницу."
				}

				if (json && json.errorMessage) {

					showError(json.errorMessage);
					return true;
				}
			} catch (e) {
			}

			showError(xhr.responseText);
		}
	},
	initialize: function () {
		this.on("error", this.errorHandler, this);
	}
});

Collection = Backbone.Collection.extend({
	initialize: function () {
		this._params = {
			filter: {},
			sortingField: "id",
			sortingMethod: "asc",
			limit: 10,
			page: 1,
			recordsCount: 0
		};
	},

	sync: function (method, model, options) {
		options.dataType = options.dataType ? options.dataType : "jsonp";

		return Backbone.sync(method, model, options);
	},

	getParams: function () {
		return this._params
	},
	setParams: function (obj) {
		this._params = $.extend(this._params, obj);

		return this._params
	},


	parse: function (data) {
		checkForWarnings(data.requestData, "requestData was not found in the JSON");
		this.requestData = data.requestData || {};
		this.requestData.filter = this.requestData.filter || {};

		if (data.requestData && this.requestData.coreVersion) {
			CORE_VERSION = data.requestData.coreVersion;
			VersionInfo.show();
		}

		return data.data
	},

	fetch: function (options) {
		options = options || {};

		this.trigger("fetch", this);

		var data = $.extend(this.getParams(), options.data);

		var errorHandler = $.extend(function (model, xhr) {
			// TODO Отрабатывать ошибки
			if (xhr && xhr.responseText && xhr.responseText.length) {
				var json;
				try {
					json = JSON.parse(xhr.responseText);
				} catch (e) {

				}
				if (json && json.errorCode == 3) {
					Core.Cookies.clear();
					window.location.href = "/auth/";
					return;
				}
				showError(xhr.responseText);
			}
		}, options.error);


		return Backbone.Collection.prototype.fetch.call(this, $.extend(options, {
			data: data,
			error: errorHandler
		}));
	}
});

Backbone.View.prototype.assign = function (selector, view) {
	var selectors;
	if (_.isObject(selector)) {
		selectors = selector;
	}
	else {
		selectors = {};
		selectors[selector] = view;
	}
	if (!selectors) return;
	_.each(selectors, function (view, selector) {
		if(view){
			view.setElement(this.$(selector)).render();
		}
	}, this);
};

View = Backbone.View.extend({
	collectionLoaded: function () {
	},
	modelLoaded: function () {
	},
	templateLoaded: function () {
	},

	_dataLoaded: function () {
		this._loadingQueue--;
		if (this.collection) {
			this.collection.off("reset", this._dataLoaded);
			this.collectionLoaded();
		} else if (this.model) {
			this.off("model:loaded", this._dataLoaded);
			this.modelLoaded();
		}


		if (this._loadingQueue < 1) {
			this.ready();
		}
	},
	_templateLoaded: function () {
		this._loadingQueue--;
		this.off("template:loaded", this._templateLoaded);
		this.templateLoaded();

		if (this._loadingQueue < 1) {
			this.ready();
		}
	},
	// Дожидаемся загрузки и шаблона и коллекции
	autoLoadHandler: function () {
		this._loadingQueue = 1;
		if (this.collection) {
			this._loadingQueue = 2;
			this.collection.on("reset", this._dataLoaded, this);
		} else if (this.model) {
			this._loadingQueue = 2;
			this.on("model:loaded", this._dataLoaded, this);
		}

		this.on("template:loaded", this._templateLoaded, this);
	},
	queue: function (queue, callback, context) {
		var queueLength = queue.length;
		var loaded = 0;

		for (var i = 0; i < queueLength; i++) {
			queue[i].context.on(queue[i].event, function handler () {
				loaded++;

				if (loaded >= queueLength) {
					this.context.off(this.event, handler);
					callback.call(context);
				}
			}, queue[i]);
		}
	},

	loadTemplate: function (templateName) {
		var $body = $(document.body),
			view = this;

		Core.Templates.load(templateName, function (template) {
			view.trigger("template:loaded", template);
		});
	},
	init: function () {

	},
	separateRoles: function (role, callback, scope) {
		var userInRole = false;

		if (role instanceof Array) {
			userInRole = role.indexOf(Core.Data.currentRole()) !== -1;
		} else {
			userInRole = Core.Data.currentRole() === role;
		}

		if (userInRole || role == Core.Data.DEFAULT) {
			scope ? callback.call(scope) : callback();
		}
	},
	depended: function (view) {
		if (!this._dependedViews) {
			this._dependedViews = [];
		}
		this._dependedViews.push(view);
	},
	destroy: function () {
		this.undelegateEvents();
		this.$el.remove();
		this.clear();

		delete this
	},
	clearAll: function () {
		_(Data).each(function (view) {
			view.destroy();
		});
		Data = {};
	},
	clear: function () {

		_(this._dependedViews).each(function (view) {
			view.destroy();
		});
		this._dependedViews = [];

	},
	render: function () {
		return this
	},
	assign: function (selector, view) {
		var selectors;
		if (_.isObject(selector)) {
			selectors = selector;
		}
		else {
			selectors = {};
			selectors[selector] = view;
		}
		if (!selectors) return;
		_.each(selectors, function (view, selector) {
			view.setElement(this.$(selector)).render();
		}, this);
	},
	initWithDictionaries: function (dicts, callback, scope, returnAsJSON) {
		if (!dicts || !dicts.length) callback.call(scope);

		var promises = [];
		var dictionaries = {};

		_(dicts).each(function (dict) {
			var dictionary;

			if (dict.fd) {
				dictionary = new App.Models.FlatDirectory().set("id", dict.id);
			} else if (dict.thesaurus) {
				dictionary = new App.Collections.ThesaurusTerms();
				dictionary.parentGroupId = dict.id;
			} else if (dict.collection) {
				dictionary = new dict.collection({data: {limit: 0}});
			} else {
				dictionary = new App.Collections.DictionaryValues([], {name: dict.pathPart});
			}

			if (dict.collection) {
				promises.push(dictionary.fetch({data: {limit: 0}}));
			} else {
				promises.push(dictionary.fetch());
			}
			dictionaries[dict.name] = dictionary;
		});

		$.when.apply($, promises).done(function () {
			if (returnAsJSON) {
				/*dictionaries = _(dictionaries).map(function (d) {
				 return d.toBeautyJSON ? d.toBeautyJSON() : d.toJSON();
				 });*/

				_(dictionaries).each(function (d, key) {
					dictionaries[key] = d.toBeautyJSON ? d.toBeautyJSON() : d.toJSON();
				});
			}

			callback.call(scope, dictionaries);
		});
	}
});

Form = View.extend({
	cancel: function (event) {
		event.preventDefault();
		App.Router.navigate(this.options.referrer, {trigger: true});
	},

	save: function (event, options) {
		if (event) event.preventDefault();

		var readyToSave = this.validate();

		if (readyToSave) {
			this.model.save(options);
		}

		return readyToSave;
	},

	validate: function () {
		var validity = true,
			$firstFoundedError;

		this.$(".Mandatory:not(select,.ComboWrapper)").each(function () {
			var $this = $(this);

			if ($this.hasClass("Combo")) {
				$this.closest(".ComboWrapper").removeClass("WrongField");
			} else {
				$this.removeClass("WrongField");
			}

			var $input;
			if ($this.is(":input"))
				$input = $this;
			else if ($this.hasClass("DDSelect"))
				$input = $this.prev();
			else if ($this.hasClass("select2") || $this.hasClass("RichTextWrapper"))
				$input = $this.next();
			else
				$input = $this.find(":input").eq(0);
			if (!$input.val() || !$input.val().length || $input.hasClass("invalid")) {
				if ($this.hasClass("Combo")) {
					$this.closest(".ComboWrapper").addClass("WrongField");
				} else {
					$this.addClass("WrongField");
				}

				$firstFoundedError = $firstFoundedError || $this;
				if ($firstFoundedError.hasClass("DDSelect"))
					$firstFoundedError = $firstFoundedError.prev();
				else if ($firstFoundedError.hasClass("select2"))
					$firstFoundedError = $firstFoundedError.prev();
				validity = false;
			}
		});

		/*this.$("select.Mandatory").removeClass("WrongField").each(function () {
		 if (!$(this).val()) {
		 $(this).addClass("WrongField");
		 $firstFoundedError = $firstFoundedError || $(this);
		 validity = false;
		 }
		 });*/

		this.$("[data-validate-size]").removeClass("WrongField").each(function () {
			if (parseFloat($(this).val()) > parseFloat($(this).data("maxsize")) || parseFloat($(this).val()) < parseFloat($(this).data("minsize"))) {
				$(this).addClass("WrongField");
				$firstFoundedError = $firstFoundedError || $(this);
				validity = false;
			}
		});

		if ($firstFoundedError) {
			//$firstFoundedError.focus();
			$('html, body').animate({ scrollTop: $($firstFoundedError).offset().top - 30 }, 'fast');
		}

		return validity
	}
});

// НЕ ДУМАЙ ПРО СОХРАНЕНИЕ!
DynamicView = View.extend({
	renderStructure: function (structure) {
		var view = this;

		_.each(structure.group, function (block) {
			var Block = new DynamicViewBlock({
				structure: block
			});

			view.$el.append(Block.render().el);
		});
	},
	initialize: function () {
		this.loadStructure();
		this.on("structure:loaded", this.renderStructure, this);
	},


	loadStructure: function () {
		var view = this;

		$.ajax({
			url: this.url(),
			dataType: "jsonp",
			success: function (json) {
				var data = json.data;

				view.trigger("structure:loaded", data);
			}

		});
	}
});
DynamicViewBlock = View.extend({
	render: function () {
		var view = this;

		_.each(this.options.structure.attribute, function (attribute) {
			var Item = new DynamicViewBlockItem({
				structure: attribute
			});

			view.$el.append(Item.render().el);
		});
		return this
	}
});
DynamicViewBlockItem = View.extend({
	render: function () {
		var view = this;

		view.$el.html("<b>" + this.options.structure.name + "</b>: ");

		_.each(this.options.structure.properties, function (property) {
			if (property.name == "value") {
				view.$el.append(property.value);
			}
		});

		return this
	}
});

//
//	Ошибки и предупреждения
//
function showError (message) {
	var $message = $("<div/>");
	var $iframe = $("<iframe/>").css({
		width: "100%",
		height: "100%"
	}).appendTo($message);

	setTimeout(function () {
		var doc = $iframe.get(0).contentWindow.document;
		doc.write(message);
	}, 1);

	$message.dialog({
		title: "Ошибка!",
		width: 400,
		modal: true
	});
}

function checkForErrors (variables, errorText) {
	if (variables instanceof Array) {
		for (var i = 0; i < variables.length; i++) {

			var variable = variables [i];
			if (variable == undefined || variable == null) {
				throw new Error(errorText);
			}
		}
	}
	else {
		if (variables == undefined || variables == null) {
			throw new Error(errorText);
		}
	}
}
function checkForWarnings (variables, warningText) {
	if (variables instanceof Array) {
		for (var i = 0; i < variables.length; i++) {

			var variable = variables [i];
			if (variable == undefined || variable == null) {
				console.warn(warningText);
			}
		}
	}
	else {
		if (variables == undefined || variables == null) {
			console.warn(warningText);
		}
	}
}


//
//  Расширение тегов jQuery templates
//
$.extend($.tmpl.tag, {
	"phone": {
		_default: { $1: "$data" },
		open: "if($notnull_1){__.push(Core.Numbers.makePhone($.encode($1a)));}"
	},
	"formatDate": {
		_default: { $1: "$data" },
		open: "if($notnull_1){__.push(Core.Date.format($.encode($1a)));}"
	},
	"formatDateTime": {
		_default: { $1: "$data" },
		open: "if($notnull_1){__.push(Core.Date.formatDateTime($.encode($1a)));}"
	},
	"countDays": {
		open: "if($notnull_1){__.push(Core.Date.countDays($.encode($1a)));}"
	},
	"getYear": {
		_default: { $1: "$data" },
		open: "if($notnull_1){__.push(Core.Date.getYear($.encode($1a)));}"
	},
	"age": {
		_default: { $1: "$data" },
		open: "if($notnull_1){__.push(Core.Date.getAge($.encode($1a)));}"
	},
	"ageString": {
		_default: { $1: "$data" },
		open: "if($notnull_1){__.push(Core.Date.getAgeString($.encode($1a)));}"
	},
	"decorate": {
		open: "if($notnull_1){__.push(Core.Strings.decorate($2));}"
	},
	"plural": {
		open: "if($notnull_1){__.push(Core.Language.plural($2));}"
	}
});


function showThrobber () {
	var $throbber = $('.Throbber');
	$throbber = $throbber.length ? $throbber : $('<div class="Throbber">Загружается</div>').appendTo('body');
	$throbber.fadeIn();
}

function hideThrobber () {
	$(".Throbber").fadeOut();
}

var throbberHideTimeout, showErrorTimeout, requestQueue = [];

jQuery.ajaxSetup(
	{
		showThrobberTimeout: null,
		beforeSend: function () {
			clearTimeout(throbberHideTimeout);
			clearTimeout(showErrorTimeout);
			this.showThrobberTimeout = setTimeout(showThrobber, 700);
			showErrorTimeout = setTimeout(function () {
				hideThrobber();
				showError("Превышено время ожидания ответа от сервера. Повторите попытку.");
			}, 240000);
			requestQueue.push(1);
		},
		complete: function () {
			requestQueue.pop();
			if (!requestQueue.length) {
				throbberHideTimeout = setTimeout(hideThrobber, 200);
			}
			clearTimeout(showErrorTimeout);
			clearTimeout(this.showThrobberTimeout);

		},
		error: function () {
			requestQueue.pop();
			throbberHideTimeout = setTimeout(hideThrobber, 200);
			clearTimeout(showErrorTimeout);
			clearTimeout(this.showThrobberTimeout);
		}
	});

VersionInfo = {
	_getElement: function () {
		if (!this._element) {
			this._element = $("<div/>").css({
				position: "fixed",
				bottom: 8,
				right: 8,
				padding: 10,
				opacity: "0",
				fontSize: "10px",
				border: "1px solid #81B5C1",
				background: "#DEEDF2"
			});
			$(document.body).append(this._element);

			var toggler = false;

			this._element
				.hover(
				function () {
					$(this).stop(true, true).animate({
						opacity: 1
					}, DEFAULT_ANIMATION_TIME);
				},
				function () {
					if (!toggler) {
						$(this).stop(true, true).animate({
							opacity: "0"
						}, DEFAULT_ANIMATION_TIME);
					}
				})
				.click(function () {
					toggler = !toggler;
				});
		}
		return this._element
	},

	show: function () {
		if (DEBUG_MODE) {
			var html = "Версия GUI: <b>" + GUI_VERSION + "</b>";

			if (CORE_VERSION) {
				html += "<br/>Версия ядра: " + "<b>" + CORE_VERSION + "</b>";
			}
			this._getElement().html(html);
		}
	},
	hide: function () {
		this._getElement().fadeOut(DEFAULT_ANIMATION_TIME);
	}
};

$(function () {
	VersionInfo.show();
});
