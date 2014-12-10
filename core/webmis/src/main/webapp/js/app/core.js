Core = {
	Cookies: {
		getAll: function () {
			var pairs = document.cookie.split("; "),
				pair,
				cookies = {};

			for (var i = 0; i < pairs.length; i++) {
				pair = pairs[i].split("=");
				cookies[pair[0]] = pair[1];
			}
			return cookies;
		},

		clear: function () {
			var cookies = document.cookie.split(";");

			for (var i = 0; i < cookies.length; i++) {
				var cookie = cookies[i];
				var eqPos = cookie.indexOf("=");
				var name = eqPos > -1 ? cookie.substr(0, eqPos) : cookie;
				document.cookie = name + "=;expires=Thu, 01 Jan 1970 00:00:00 GMT; path=/";
			}

			return true
		},

		get: function (name) {
			var cookies = this.getAll();
			return cookies[name];
		},

		set: function (name, value, time) {
			var expire = new Date();
			time = time ? time : 360000024;
			expire.setTime(( new Date() ).getTime() + time);
			document.cookie = name + "=" + value + ";expires=" + expire.toGMTString() + "; path=/";
		}
	},

	Data: {
		currentRole: function (value) {
			if (value == undefined) {
				return Core.Cookies.get("currentRole");
			} else {
				Core.Cookies.set("currentRole", value);
			}
		}
	},

	Queue: {
		queuesArray: {},

		add: function (queueName, value) {
			var queue = this.queuesArray [ queueName ];

			if (!(queue instanceof Array)) {
				this.queuesArray [ queueName ] = [];
			}
			this.queuesArray [ queueName ].push(value);
		},

		get: function (queueName) {
			return this.queuesArray [ queueName ]
		},

		remove: function (queueName, value) {
			var queue = this.queuesArray [ queueName ];

			for (var i = 0; i < queue.length; i++) {
				if (queue [i] == value) {
					queue.splice(i, 1);
					break;
				}
			}
		}
	},

	Templates: {
		loadedArray: [],

		/**
		 * Проверка, загружен ли вызываемый шаблон
		 *
		 * @param {String} name
		 *
		 * @return {String}
		 */
		getLoaded: function (name) {
			for (var i = 0; i < this.loadedArray.length; i++) {
				if (this.loadedArray [i].name == name) {
					return this.loadedArray [i].template
				}
			}
			return false
		},

		/**
		 * Загрузка шаблона аяксом
		 *
		 * @param {String} name
		 * @param {Function} callback
		 */
		load: function (name, callback) {
			var self = this;
			var template = this.getLoaded(name);

			if (!template) {
				// TODO: Включить кеширование шаблонов
				$.get("/js/app/templates/" + name + ".tmpl" + "?" + Math.random(), function (data) {
					self.loadedArray.push(
						{
							name: name,
							template: data
						});

					$(document.body).append(data);

					callback(data);
				});
			}
			else {
				callback(template);
			}
		}
	},

	Language: {
		/**
		 * Возвращает правильный падеж для заданного числа
		 *
		 * > plural(1, "рука", "руки", "рук") // рука
		 *
		 * @param {Number} number
		 * @param {String} one    Одна «рука»
		 * @param {String} two    Две «руки»
		 * @param {String} many    Много «рук»
		 *
		 * @return {String}
		 */
		plural: function (number, one, two, many) {
			if (number > 10 && number < 20) {
				return many;
			}

			if (number == 1 || (number % 10) == 1) {
				return one
			}

			if ((number % 10) > 1 && (number % 10) < 5) {
				return two
			}
			else {
				return many
			}
		}
	},

	Objects: {
		/**
		 * Рекурсивно сливает два объекта
		 *
		 * @param source
		 * @param destination
		 */
		mergeAll: function (source, destination) {
			var result = source;

			for (var prop in destination) {
				if (destination.hasOwnProperty(prop)) {
					if (destination[prop] === Object(destination[prop]) && result[prop] === Object(result[prop])) {
						result[prop] = this.mergeAll(result[prop], destination[prop]);
					} else {
						if (destination[prop] != null) {
							result[prop] = destination[prop];
						}
					}

				}
			}


			return result
		}
	},
	
	Numbers: {

		/**
		 * Разбивает число заданным разделителем.
		 *
		 * > separate(10000) // 10 000
		 * > separate(10000, "'") // 10'000
		 * > separate(10000, "'", 2) // 1'00'00
		 *
		 * @param {Number} number
		 * @param {String} [separator]  Разделитель    <пробел>
		 * @param {Number} [counter]  Разделять по <3>
		 *
		 * @return {String}
		 */
		separate: function (number, separator, counter) {
			number = number.toString();
			counter = counter || 3;
			separator = separator || " ";

			var parts = Math.floor(number.length / counter);
			var resultArray = [];

			if (parts > 0) {
				for (var i = 0; i < parts; i++) {
					resultArray[resultArray.length] = number.substring(number.length - counter, number.length);
					number = number.substring(0, number.length - counter);
				}
				if (number.length) {
					resultArray[resultArray.length] = number;
				}

				var result = "";

				// Reversing array and making toString
				var resultArrayLength = resultArray.length;
				for (i = 0; i < resultArrayLength; i++) {
					result += resultArray[resultArrayLength - i - 1];

					// Is not last
					if (i < resultArrayLength - 1) {
						result += separator;
					}
				}
				return result
			}
			else {
				return number
			}

		},
		/**
		 * > makePhone ( "79149990022" ); // +7 914 999 00 22
		 *
		 * @param {String} phone
		 *
		 * @return {String}
		 */
		makePhone: function (phone) {
			var parsePhone = /^(\d{1})(\d{3})(\d{3})(\d{2})(\d{2})/i.exec(phone),
				newPhone = phone;

			if (parsePhone) {

				newPhone = "+" + parsePhone [1];
				for (var i = 2; i < parsePhone.length; i++) {
					newPhone += " " + parsePhone [i]
				}
			}
			return newPhone
		}
	},

	Forms: {
		serializeToObject: function ($element) {
			var o = {};

			var $clone = $element.clone();

			if (!$clone.is("form") && !$clone.find("form").length) {
				$clone = $("<form/>").append($clone);
			}

			// Отдельные условия для дэйтпикеров
			$clone.find(".SelectDate, .date-input").each(function () {
				var $this = $(this);
				var relation = $this.data("relation");
				var duplicate = $this.data("duplicate");

				if (relation) {
					$this.val(Core.Date.toInt($this.val() + " " + $clone.find(relation).val()) || "");
				} else {
					$this.val(Core.Date.toInt($this.val()) || "");
				}

				/*if ( duplicate ) {
				 $this.parent().append( $this.clone().attr( "name", duplicate ) );
				 }*/
			});

			// $.clone не копирует значения селектов, присваиваем вручную
			function setSelectValue (i) {
				$clone.find("select").eq(i).val($(this).val());
			}

			$element.filter("select").each(setSelectValue);
			$element.find("select").each(setSelectValue);

			var a = $clone.serializeArray();

			$.each(a, function () {
				this.value = this.value.trim();

				if (this.value.length) {
					if (o[this.name] !== undefined) {
						if (!o[this.name].push) {
							o[this.name] = [o[this.name]];
						}
						o[this.name].push(this.value || "");
					} else {
						o[this.name] = this.value || "";
					}
				}
			});
			return o;
		}
	},

	Strings: {
		decorate: function (whereString, whatString) {
			whereString = whereString + "";
			whatString = (whatString || "") + "";

			var result = whereString;
			var wordsArray = whatString.split(" ");

			if (wordsArray.length) {
				for (var i = 0; i < wordsArray.length; i++) {
					var regExp = new RegExp(wordsArray[i], "i");

					if (regExp.test(whereString)) {
						result = whereString.replace(regExp, function (entry) {
							return "<b>" + entry + "</b>"
						});
						break;
					}
				}
			}

			return result
		},

		toLatin: function (string) {
			var listChars = {
				"Й": "Q", "й": "q", "Ц": "W", "ц": "w", "У": "E", "у": "e", "К": "R", "к": "r", "Е": "T", "е": "t", "Н": "Y",
				"н": "y", "Г": "U", "г": "u", "Ш": "I", "ш": "i", "Щ": "O", "щ": "o", "З": "P", "з": "p", "Х": "{", "х": "[",
				"Ъ": "}", "ъ": "]", "Ф": "A", "ф": "a", "Ы": "S", "ы": "s", "В": "D", "в": "d", "А": "F", "а": "f", "П": "G",
				"п": "g", "Р": "H", "р": "h", "О": "J", "о": "j", "Л": "K", "л": "k", "Д": "L", "д": "l", "Ж": ":", "ж": ";",
				"Э": "\"", "э": "'", "Я": "Z", "я": "z", "Ч": "X", "ч": "x", "С": "C", "с": "c", "М": "V", "м": "v", "И": "B",
				"и": "b", "Т": "N", "т": "n", "Ь": "M", "ь": "m", "Б": "<", "б": ",", "Ю": ">", "ю": "."
			};

			return string.length > 1 ?
				_.map(string,function (c) {
					return listChars[c] || c;
				}).join("") :
				(listChars[string] || string);
		},
		
		endsWith: function (str, chr) {
			return str.length ? str[str.length - 1] === chr : false;
		},

		endsWithPunctuationChar: function (str) {
			var punctChars = [".", ",", "!", "?", ";", ":"];
			var punctCharFound = false;

			for (var i = 0; i < punctChars.length; i++) {
				//console.log(str, punctChars[i], Core.Strings.endsWith(str, punctChars[i]));
				if (Core.Strings.endsWith(str, punctChars[i])) {
					punctCharFound = true;
					break;
				}
			}

			console.log(punctCharFound);

			return punctCharFound;
		},

		startsWith: function (str, chr) {
			return str.length ? str[0] === chr : false;
		},

    collectTextNodes: function (element, texts) {
      for (var child = element.firstChild; child !== null; child = child.nextSibling) {
        if (child.nodeType === 3)
          texts.push(child);
        else if (child.nodeType === 1)
          this.collectTextNodes(child, texts);
      }
    },

    getTextWithSpaces: function (element) {
      var texts = [];
      this.collectTextNodes(element, texts);
      for (var i = texts.length; i-- > 0;)
        texts[i] = texts[i].data;
      return texts.join(' ');
    },

    cleanTextMarkup: function (text) {
      return this.getTextWithSpaces($("<div/>").html(text)[0]);
    }
	},

	Url: {
		getReferrer: function () {
			var referrer = document.referrer || "/";
			referrer = referrer.replace(window.location.protocol + "//" + window.location.host, "");

			return referrer
		},

		extractPage: function (query) {
			var matches = query ? query.match(/^p([0-9]+).*/) : null;
			return matches && matches.length > 1 ? matches[1] : 1;
		},

		compileUri: function (uri, options) {
			return uri.replace(/:([^/]*)/g, function (enter, fragment) {
				checkForErrors(options[fragment], "\"" + fragment + "\" was not found");

				return options[fragment]
			});
		},

		/**
		 * Парсит URL и возвращает объект с GET-параметрами
		 * @return Object Объект, где ключи – это имена параметров, а значения – это значения.
		 */
		extractUrlParameters: function () {
			var query = window.location.search.replace(/^\?/, ""),
				pairs = query.split("&"),
				params = {};

			for (var i = 0; i < pairs.length; i++) {
				var pair = pairs[i].split("=");

				var obj = /(.*?)\[(.*?)\]/i.exec(decodeURIComponent(pair[0]));

				if (obj && obj.length) {
					params[obj[1]] = params[obj[1]] || {};
					params[obj[1]][obj[2]] = decodeURIComponent(pair[1]);
				}
				else {
					if (pair[1]) {
						params[pair[0]] = decodeURIComponent(pair[1]);
					}
				}
			}


			return params == {} ? null : params;
		}
	},

	Date: {
		differenceBetweenDates: function (DateObject1, DateObject2) {
			var date1 = DateObject1.getTime();
			var date2 = DateObject2.getTime();
			var resultDate = date1 > date2 ? date1 - date2 : date2 - date1;

			return {
				date: new Date(resultDate),
				difference: resultDate
			};
		},

		zeroFirst: function (number) {
			var zero = "";
			if (number < 10) {
				zero = "0";
			}

			return zero + number;
		},

		getYear: function (dateString) {
			var date = new Date(parseInt(dateString));
			return date.getFullYear();
		},

		getAge: function (dateString) {
			var date = this.differenceBetweenDates(new Date(parseInt(dateString)), new Date());

			return date.date.getFullYear() - 1970;
		},

		getAgeString: function (dateString) {
			var now = moment();
			var birthDate = moment(parseInt(dateString));

			var years = now.diff(birthDate, 'year');
			var months = now.diff(birthDate, 'month');
			var days = now.diff(birthDate, 'day');


			if (years > 0) {
				if (years < 3) {
					return years + " " + Core.Language.plural(years, "год", "года", "лет") + " и " +
						(months - years * 12) + " " + Core.Language.plural((months - years * 12), "месяц", "месяца", "месяцев");
				} else {
					return years + " " + Core.Language.plural(years, "год", "года", "лет");
				}
			} else {
				if (months < 1) {
					return days + " " + Core.Language.plural(days, "день", "дня", "дней");
				} else {
					return months + " " + Core.Language.plural(months, "месяц", "месяца", "месяцев");
				}
			}
		},

		countDays: function (dateString) {
			var date = this.differenceBetweenDates(new Date(parseInt(dateString)), new Date());

			return Math.floor(date.difference / 1000 / 60 / 60 / 24)
		},

		format: function (dateString) {
			var date = new Date(parseInt(dateString));
			return this.zeroFirst(date.getDate()) + "." + this.zeroFirst(date.getMonth() + 1) + "." + date.getFullYear();
		},

		formatDateTime: function (dateString) {
			var formattedDate = this.format(dateString);

			var date = new Date(parseInt(dateString));
			return formattedDate + " " + this.zeroFirst(date.getHours()) + ":" + this.zeroFirst(date.getMinutes());
		},

		toInt: function (formattedDateString) {
			var dateTime = formattedDateString.split(" "); // separate date from time
			var dateArray = dateTime[0].split(".");

			var date;
			if (dateTime[1]) { // В строке найдено время
				var timeArray = dateTime[1].split(":");
				date = new Date(parseInt(dateArray[2], 10), parseInt(dateArray[1], 10) - 1, parseInt(dateArray[0], 10), parseInt(timeArray[0], 10), parseInt(timeArray[1], 10));
			} else {
				date = new Date(parseInt(dateArray[2], 10), parseInt(dateArray[1], 10) - 1, parseInt(dateArray[0], 10));
			}

			return date.getTime();
		}
	},

	Models: {
		toInputs: function ($inputs, model) {
			var self = this;
			$inputs.each(function () {
				var $this = $(this), name = $this.attr("name"), value = self.getValueByInputName(model, name);
				$this.val(value);
			});
		},

		getValueByInputName: function (model, name) {
			{
				var base, value, subs, sub;
				if (/^.*?\[/.test(name)) {
					base = /^(.*?)\[/.exec(name)[1];
					value = model.get(base);
					subs = name.match(/\[(.*?)\]/g);
					for (var i = 0; i < subs.length; i++) {
						sub = subs[i].replace("[", "").replace("]", "");
						if (!value || !value[sub]) {
							value = "";
							break;
						}
						value = value[sub];
					}
				}
				else {
					value = model.get(name);
				}
				return value;
			}
		}
	},

	execAll: function (regexp, source) {
		var executed,
			resultArray = [""];

		while (executed = regexp.exec(source)) {
			resultArray [0] += executed [0];
			resultArray.push(executed [1]);
		}

		return resultArray
	},

	getCaretPosition: function (input) {
		if ('selectionStart' in input) {
			// Standard-compliant browsers
			return input.selectionStart;
		} else if (document.selection) {
			// IE
			input.focus();
			var sel = document.selection.createRange();
			var selLen = document.selection.createRange().text.length;
			sel.moveStart('character', -input.value.length);
			return sel.text.length - selLen;
		}
	},

	setCaretPosition: function (input, position) {
		if (input.createTextRange) {
			var textRange = input.createTextRange();
			textRange.collapse(true);
			textRange.moveEnd(position);
			textRange.moveStart(position);
			textRange.select();
			return true;
		} else if (input.setSelectionRange) {
			input.setSelectionRange(position, position);
			return true;
		}
	}
};