
function UIInitialize ()
{
	$("select.Styled").each ( function ()
	{
		var $this = $(this),
			$title = $("<div/>", { "class": "Title" }),
			$span = $("<span/>", { "class": "Actions" }),
			$ul = $("<ul/>"),
			$content = $("<div/>", { "class": "Content" });

		// Предотвращаем повторное навешивание событий
		if ( $this.data ( "styled-select" ) )
		{
			return false
		}
		$this.data ( "styled-select", true );


		// Составляем список из опшнов селекта
		$this.find ( "option" ).each ( function ()
		{
			var $option = $(this);
			var $li = $("<li/>").html ( $option.html () );

			// События, вызываемые при выборе элемента списка
			$li.click ( function ()
			{
				$option.prop ( "selected", true ).change().siblings ().prop ( "selected", false );
				$li.addClass ( "Selected" ).siblings ().removeClass ( "Selected" );

				$span.html ( $option.html () );
			});

			// Начальная установка выбранного элемента
			if ( $option.prop ( "selected" ) )
			{
				$li.addClass ( "Selected" );
			}

			$ul.append ( $li );
		});

		$span.html ( $ul.find(".Selected").html () );

		var $styled = $("<div/>", { "class": "DDList DDSelect" })
					.append (
						$title.append ( $span )
					)
					.append (
						$content.append ( $ul )
					);

		$styled.insertAfter ( $this );

		// Инициализация кастомного селекта
		if ( $this.prop ( "disabled" ) )
		{
			$styled.addClass ( "Disabled" );
		}


		$this.hide ();
	});


	$(".DDList").each ( function ()
	{
		var $title = $(this).children (".Title");
		
		if ( $title.data ( "show-dd-list" ) )
		{
			return true
		}
		$title.data ( "show-dd-list", true );

		$title.click ( function ( event )
		{
			var $this = $(this),
				$ddList = $this.closest(".DDList");

			$(".DDList.Active").not ( $ddList ).removeClass ( "Active" );

			$ddList.toggleClass( "Active" );
			$this.siblings( ".Content" ).css (
			{
				top: $this.height ()
			});

			event.stopPropagation ();
		});
	});

	/*
	*	ShowMore
	 */
	$(".Actions.ShowMore").each ( function (){
		var $this = $(this);

		// Предотвращаем повторное навешивание событий
		if ( $this.data ( "show-more" ) )
		{
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
	$(".MKBList span").click(function () {
		$(this).toggleClass("Selected");
	});

}


$(document).ready(function(){
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

	$(document).click (function ()
	{
		$(".DDList.Active").removeClass ( "Active" );
	});






	$(".ShowHidePopup").click(function () {
		$("#popup-overlay").toggle();
		$(".popup").toggle();
		//$("body").toggleClass("NoScroll");
	});
	
	
	
	$(".DrugList td.Active").click(function () {
		$(this).toggleClass("Selected");
		$(".DrugList td.Active").not(this).removeClass("Selected");
	});

/*Смена цветовой схемы*/

	$(".ChangeTheme").click(function () {
			$("html").toggleClass("VarColor");
	});
	
/*Тезаурус*/
	
	$(".TezList a").click(function () {
		$(this).toggleClass("Selected");
	});
	
	$(".EventList li>label").click(function () {
		$(this).toggleClass("Selected");
	});
/*Календарь*/
	// Datepicker
	$("#datepicker").datepicker({
		inline: true
	});
	$(".BedList input").change(function () {
		$(this).parent("label").parent("li").toggleClass("Selected");
	});
	/*Show/Hide tesaurus textarea*/
	
	$(".SetComment input").change(function () {
		$(this).parent("label").parent(".SetComment").toggleClass("Active");
	});	
	
});


/*
jQuery.ajaxSetup (
{
	complete: function ()
	{
		setTimeout (UIInitialize, 5000);
	}
});
*/