var UI = UI || {};

UI.CustomSelect = function ( $select ){
	var $title, $span, $ul, $content, $input;

	function _render () {
		$title = $("<div/>", { "class": "Title" });
		$span = $("<span/>", { "class": "Actions" });
		$ul = $("<ul/>");
		$content = $("<div/>", { "class": "Content" });

		if ( $select.hasClass("ComboBox") ) {
			$input = $("<input/>", { type: "text", name: $select.attr("name")+"-input", class: "ComboBoxInput" });

			$span.html($input);
		}

		// Составляем список из опшнов селекта
		$select.find ( "option" ).each ( function ()
		{
			var $option = $(this);
			var $li = $("<li/>").html ( $option.html ()).addClass($option.attr("class"));

			// События, вызываемые при выборе элемента списка
			$li.click ( function (){
				$option.prop ( "selected", true ).change().siblings().prop( "selected", false );
				$li.addClass ( "Selected" ).siblings ().removeClass ( "Selected" );

				_setValue($option.html ());
				$select.focus(); // возвращаем фокус селекту
			});

			// Начальная установка выбранного элемента
			if ( $option.prop ( "selected" ) ) {
				$li.addClass ( "Selected" );
			}

			$ul.append ( $li );
		});

		$select.change(function(){
			$select.find ( "option" ).each ( function () {
				var $option = $(this);

				if ( $option.attr ( "selected" ) ) {
					$ul.find("li").eq($option.index()).addClass ( "Selected" ).siblings().removeClass("Selected");
					_setValue ( $option.html () );
				}
			});
		} ).focus(function () {
			$styled.addClass("Focused");
		} ).blur(function (){
			$styled.removeClass("Focused");
		});

		// Особые условия для комбобокса
		if ( $input ) {
			$input.focus(function () {
				$styled.addClass("Focused");
			} ).blur(function (){
				$styled.removeClass("Focused");
			});

			// Правильно расставляем фокусы
			$select.keydown(function(){
				$select.blur();
				$input.focus();
			});

			// Выберем пункт меню, если такой же забили в инпут ручками
			$input.keyup(function(event){
				var value = $input.val();

				$select.find( "option" ).each(function(){
					var $option = $(this);

					if ( jQuery.trim($option.text() ).toLowerCase() == value.toLowerCase() ) {
						$option.prop("selected", true ).siblings().prop( "selected", false );
					}
				});
			});


			// Поведение инпута выбора времени
			if ( $select.hasClass("HourPicker") ) {
				$input.timePicker({
					select: $select
				});
			}
		}
		_setValue( $select.data("string-value") || $ul.find(".Selected").html () );

		var $styled = $("<div/>", { "class": "DDList DDSelect" }).append (
				$title.append ( $span )
		).append (
				$content.append ( $ul )
		);

		$styled.insertAfter ( $select );

		// Инициализация кастомного селекта
		if ( $select.prop ( "disabled" ) ) {
			$styled.addClass ( "Disabled" );
			if ( $input ) {
				$input.prop("disabled", true);
			}
		}

		if ( $select.hasClass ( "Mandatory" ) ) {
			$styled.addClass ( "Mandatory" );
		}

		$select.css ({
			opacity: 0,
			width: 0,
			height: 0,
			position: "absolute"
		});
	}

	function _initialize () {
		// Предотвращаем повторное навешивание событий
		if ( $select.data( "styled-select" ) ) {
			return false
		}
		$select.data ( "styled-select", true );

		_render();
	}

	function _setValue( value ) {
		if ( $select.hasClass("ComboBox") ) {
			$input.val(value);

			return;
		}
		$span.html(value);
	}

	_initialize();
};

/*(function ($) {
	$.fn.WMSelect = function () {
		var $this = this;

		// Get wrapper elements (create if rendering for the first time)
		//--------------------------------------------------------------
		var $wrapper = this.next().is(".DDList.DDSelect") ?
			this.next() :
			$('<div class="DDList DDSelect"/>').insertAfter(this);

		var $title   = $wrapper.find(".Title")[0]   || $('<div class="Title"/>').appendTo($wrapper);
		var $span    = $title.find(".Actions")[0]   || $('<span class="Actions"/>').appendTo($title);
		var $content = $wrapper.find(".Content")[0] || $('<div class="Content"/>').appendTo($wrapper);
		var $ul      = $content.find('ul')[0]       || $('<ul/>').appendTo($content);
		//--------------------------------------------------------------

		console.log(this.find("option"));

		$ul.empty().append(this.find("option").map(function () {
			return $("<li/>")
				.html($(this).html() || "...")
				.attr("data-value", $(this).val())
				.addClass($(this).attr("class"));
		}).get());

		$span.html(this.val() || "...");

		$wrapper.on("click", function () {
			console.log("asd");
			$(this).toggleClass("Active");
		});

		*//*$ul.find("li").on("click", function () {
			$this.val($(this).data("value")).change().focus();

			$("li", $ul).removeClass("Selected");
			$(this).addClass("Selected");
		});*//*

		return this.hide();

		*//*this.find("option").each(function () {
			var $option = $(this);
			var $li = $("<li/>").html($option.html()).addClass($option.attr("class"));

			// События, вызываемые при выборе элемента списка
			$li.click (function () {
				$option.prop ( "selected", true ).change().siblings().prop( "selected", false );
				$li.addClass ( "Selected" ).siblings ().removeClass ( "Selected" );

				_setValue($option.html ());
				$select.focus(); // возвращаем фокус селекту
			});

			// Начальная установка выбранного элемента
			if ( $option.prop ( "selected" ) ) {
				$li.addClass ( "Selected" );
			}

			$ul.append ( $li );
		});*//*
	};
})(jQuery);*/




function UIInitialize (context) {
	/*$(".btn").each(function () {
		if ($(this).hasClass("btn-add")) {
			$(this).button({icons: {primary: "icon-plus icon-color-green"}});
		} else if ($(this).hasClass("btn-print")) {
			$(this).button({icons: {primary: "icon-print"}});
		} else if ($(this).hasClass("btn-filter")) {
			$(this).button({icons: {primary: "icon-filter"}});
		} else {
			$(this).button();
		}
	});*/

	/*var $buttons = $(".btn").button();

	$(".btn-add", $buttons).button("option", "icons", {primary: "icon-plus icon-color-green"});
	$(".btn-print", $buttons).button("option", "icons", {primary: "icon-print"});
	$(".btn-filter", $buttons).button("option", "icons", {primary: "icon-filter"});*/

	$(".date-icon").on("click", function () {
		$(this).prev().datepicker("show");
	});

	$("input.Clearable").on("keyup", function() {
		if ($(this).val().length > 0) {
			$(this).parent().find(".ClearButton").fadeIn(300).css("display","inline-block");
		} else {
			$(this).parent().find(".ClearButton").fadeOut(300);
		}
	});

	$(".ClearButton").on("click", function () {
		$(this).parent().find("input.Clearable").val('').change();
		$(this).fadeOut(300);
	});

	$("select.Styled", context).each(function () {
		//new UI.CustomSelect($(this));
		$(this).width("100%").addClass("select2").select2({minimumResultsForSearch: 6,allowClear: true});
	});

	$(".Combo", context).each(function () {
		var $comboInput = $(this).wrap('<div class="DDList DDSelect ComboWrapper"><div class="Title"><span class="Actions"></span></div></div>');
		var $wrapper = $comboInput.parents(".DDList").append('<div class="Content"><ul></ul></div>');

		if ($comboInput.hasClass("Mandatory")) $wrapper.addClass("Mandatory");

		$wrapper.find("ul").append($comboInput.data("options").split("|").map(function (opt) {
			return $('<li>' + opt + '</li>');
		}));

		$wrapper.on("click", function (event) {
			event.stopPropagation();
			$(".DDList.Active").not ($wrapper).removeClass ( "Active" );
			$(this).toggleClass("Active");
		});

		$wrapper.find("li").on("click", function () {
			$comboInput.val($(this).html()).change();
		});

		$comboInput.on("keyup", function () {
			$wrapper.removeClass("Active");
		});
	});


	$(".DDList:not(.ComboWrapper)", context).each(function () {
		var $title = $(this).children (".Title");

		if ( $title.data ( "show-dd-list" ) )
		{
			return true
		}
		$title.data ( "show-dd-list", true );

		$title.on ("click keydown", function ( event )
		{
			if ( !$title.find(":input" ).length ) {
				event.preventDefault();
			}

			event.stopPropagation ();
			if ( event.keyCode && event.keyCode != 13 ) {
				return true
			}

			var $this = $(this),
				$ddList = $this.closest(".DDList");

			$(".DDList.Active").not ( $ddList ).removeClass ( "Active" );

			$ddList.toggleClass( "Active" );
			$this.siblings( ".Content" ).css ({
				top: $this.height ()
			});
		});
	});

	/*
	*	ShowMore
	 */
	$(".Actions.ShowMore", context).each ( function (){
		var $this = $(this);

		// Предотвращаем повторное навешивание событий
		if ( $this.data ( "show-more" ) ) {
			return false
		}
		$this.data ( "show-more", true );

		$this.click(function()
		{
			var $this = $(this);
			$this.toggleClass("Open");

			var relation = $this.attr("rel");
			if ( relation.length )
			{
				$(relation).find(".More").toggle();

				return false
			}

			$this.findNear(".More:first").toggle();

			return false;
		});
	});

	$(".SelectDate", context ).each(function(){
		var $this = $(this);

		if ( $this.data ( "datepicker" ) )
		{
			return true
		}
		$this.data ( "datepicker", true );


		$this.datepicker( {
			inline: true,
			changeYear: true,
			changeMonth: true,
			maxDate: $this.data("maxdate"),
			minDate: $this.data("mindate")
		} );

		$this.mask("99.99.9999");

		$this.focus(function () {
			$this.closest(".DatePeriod" ).addClass("Focused");
		} ).blur(function() {
			$this.closest(".DatePeriod" ).removeClass("Focused");
		});

		$this.closest(".FromTo" ).siblings(".DateIcon" ).click(function () {
			if ( $this.is(":disabled") ) {
				return false;
			}
			$("#ui-datepicker-div" ).stop(true, true);
			$this.focus();
		} );
		$this.keydown(function (event) {
			if ( event.keyCode == 40 ) { // DOWN
				$this.datepicker("show");
			}
			if ( event.keyCode == 38 ) { // UP
				$this.datepicker("hide");
			}
		} );

		$this.attr("autocomplete","off");

		var $relation = $($this.data("relation"));

		if ( $relation ) {
			$relation.timePicker();
		}
	});

	$(".Button.Disabled, [disabled]", context ).click(function(event){
		event.preventDefault();
		event.stopPropagation();
		return false
	});

	$('.HasToolTip', context).each(function() {
		var tip = $($(this).data("tooltip-content"));
		var dx = -tip.width()/2 - 35,
			dy = 15;
		tip.css('position', 'absolute');
		tip.hide();

		function position(e) {
			tip.css('left', e.pageX + dx + 'px').css('top', e.pageY + dy + 'px');
		}
		$(this).mousemove(position);

		$(this).hover(function(e) {
			position(e);
			tip.show();
		}, function(e) {
			tip.hide();
		});
	});

	$(".RichText", context).each(function () {
		var $input = $(this).hide();
		var $richtextWrapper = $('<div class="RichTextWrapper"><div contenteditable="true" class="RichText"><br></div></div>');
		var $richtext = $richtextWrapper.find(".RichText");

		if ($input.hasClass("Resizable")) $richtextWrapper.resizable({maxWidth: "100%"});
		if ($input.hasClass("Mandatory")) $richtextWrapper.addClass("Mandatory");
		if ($input.is("textarea")) {
			$richtextWrapper.css({"min-height": "9.6em"});
			$richtext.css({"min-height": "8em"});
		}

		$richtext.html($input.val() || "<br>");

		$richtext.on("input", function () {
			$input.val((($(this).html() === "<br>" || $(this).html() === "<br/>") ? "" : $(this).html().replace("<br>", ""))).change();
		});

		$input.on("change", function () {
			if ($richtext.html().replace("<br>", "") !== $input.val())
				$richtext.html($input.val() || "<br>");
		});

		$(this).before($richtextWrapper);
	});

	$("body .ToolTip").hide();
}


$.fn.timePicker = function (options) {
	return this.each(function(){
		var $input = $(this);

		$input.keydown(function(event){
			var position = Core.getCaretPosition(this);

			var numbers = Core.execAll(/(\d)/g, $input.val())[0];
			var character = String.fromCharCode( event.keyCode );

			if ( /\d|:/i.test(character) ) {
				var numbersArray = numbers.split("");

				var newPosition = position;
				if ( /:/i.test($input.val()) && position > 2 ) {
					newPosition--;
				}

				numbersArray[newPosition] = character;

				$input.val((numbersArray[0] || "0") + (numbersArray[1] || "0") + ":" + (numbersArray[2] || "0") + (numbersArray[3] || "0"));

				if ( position == 2 ) {
					position ++;
				}
				Core.setCaretPosition(this, position+1);

				if ( options && options.select ) {
					options.select.trigger("_update");
				}

				return false
			}
		});
	})
}


$(document).ready(function(){
	UIInitialize(document.body);

	$(".Grid .SortCol").click(function () {
		$(".Grid th").removeClass("Active");
		$(this).parent("th").addClass("Active");
		$(this).parent("th").toggleClass("Desc");
	});

	/*TABS*/
	$(".Tabs li").click(function () {
		$(".Tabs li").removeClass("Active");
		$(this).addClass("Active");
	});



	/*
	// Перенаправляем все внутренние ссылки на контроллер
	$("a[href]").live ( "click",  function ()
	{
		var $this = $(this);

		var path = $this.prop ( "pathname" );

		// Нас интересуют только внутренние ссылки
		if ( path && path.indexOf ("//") < 0 ) {
			window.appRouter.navigate ( path, true );
		}
		return false
	});

	UIInitialize();
	*/

	$(document).click (function ()
	{
		$(".DDList.Active").removeClass ( "Active" );
	});






	$(".ShowHidePopup").click(function () {
		$("#popup-overlay").toggle();
		$(".popup").toggle();
		//$("body").toggleClass("NoScroll");
	});

	$(".ToolTipHolder").click(function () {
		$(".ToolTipHolder").not($(this)).removeClass("Active");
		$(this).toggleClass("Active");
	});




/*
	$(".DrugList td.Active").click(function () {
		$(this).toggleClass("Selected");
		$(".DrugList td.Active").not(this).removeClass("Selected");
	});
*/
/*Смена цветовой схемы*/

	$(".ChangeTheme").click(function () {
			$("html").toggleClass("VarColor");
	});

/*Тезаурус*/

	$(".TezList a").click(function () {
		$(this).toggleClass("Selected");
	});

	$(".EventList input").change(function () {
		$(this).parent("label").toggleClass("Selected");
	});

	UI.ErrorTooltip = View.extend({
		className: "ToolTip",

		template: "<p class='Error'></p>",

		initialize: function (options) {

		},

		showAt: function (el) {
			var $target = $(el);
			this.setPosition($target);
			this.$(".Error").text($target.data("tooltip-text") || "Введены неверные данные");
			this.$el.fadeIn("fast");
		},

		hide: function () {
			this.$el.hide();
		},

		setPosition: function ($target) {
			if ($target.is("select"))
				$target = $target.next();

			var $tip = this.$el;
			var pos = $target.data("tooltip-position") || "right";
			var p = $target.offset();
			var x, y;

			//console.log($target.offset(), $target.width(), $target.height());

			$tip.removeClass("LeftTail BottomTail RightTail TopTail");

			if (pos === "right") {
				$tip.addClass("LeftTail");
				x = p.left + $target.outerWidth(true) + 10;
				y = p.top - $target.height()/2;
			} else if (pos === "top") {
				x = p.left - ($target.outerWidth(true) + ($target.outerWidth(true) - $tip.outerWidth(true)))/2;
				y = p.top - $target.outerHeight(true) - $tip.outerHeight(true);
				$tip.addClass("BottomTail");
			} else if (pos === "left") {
				$tip.addClass("RightTail");
				x = p.left - $target.outerWidth(true) - ($tip.outerWidth(true) - $target.outerWidth(true)) -10;
				y = p.top - $target.height()/2;
			} else if (pos === "bottom") {
				$tip.addClass("TopTail");
				x = p.left - ($target.outerWidth(true) + ($target.outerWidth(true) - $tip.outerWidth(true)))/2;
				y = p.top + $target.outerHeight(true) + 10;
			}

			$tip.css('position', 'absolute').css('z-index', '999').css('left', x + 'px').css('top', y + 'px');

			return $tip;
		},

		render: function () {
			this.$el.html(this.template);

			return this;
		}
	});


/*Календарь*/
	// Datepicker



	/*
	$(".BedList input").change(function () {
		$(this).parent("label").parent("li").toggleClass("Selected");
	});
	*/
});

jQuery.ajaxSetup (
{
	complete: function ()
	{
		setTimeout (UIInitialize, 5000);
	}
});
