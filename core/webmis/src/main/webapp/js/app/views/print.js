define([], function (){
	App.Views.Print = View.extend({
		tagName: "html",

		// TODO: Отдельный запрос для получения каждой печатной формы

		initialize: function () {
			console.log('init print',this.options.data)
			checkForErrors (this.options.template, "template is mandatory");

			var view = this;

			var $window = $(window);
			var width = Math.min($window.width(), 728 ),
				height = Math.min($window.height(), 967);

			this.window = window.open("", "Печать", "width="+ width +",height="+ height +",menubar=0,toolbar=0,status=0,location=0,left="+ Math.max(($window.width() - width)/2, 0) +",top="+Math.max(($window.height() - height)/2, 0));


			if ( this.model ) {
				this.model.on("change", this.ready, this);
			} else {
				this.ready.call(this);
			}


		},
		ready: function () {
			var object = this.model || this.collection || this.options.data;
			var json = object ? (object.toJSON ? object.toJSON() : object) : {};
			var view = this;

			//создаём новую форму
			var form = new PDFForm({
				data: JSON.stringify(json),
				template: this.options.template
			});

			view.window.document.charset = "utf-8";
			//вставляем форму в страницу
			view.window.document.write(form.render().$el.html());
			//вставляем скрипт который отправит форму...................
			view.window.document.write('<script>document.getElementsByTagName("form")[0].submit();</script>');

		}
	});


	var PDFForm = View.extend({
		render: function(){

			//создаём форму
			var form = this.make("form", {"action": "/pdf/", method: "post", style: "visibility: hidden"});
			//создаём текстовую область для данных
			var textarea = this.make("textarea", {name: "data"});
			//создаём поле ввода для имени шаблона
			var input = this.make("input", {name: "template", value: this.options.template});

			//вставляем данные в текстовую область
			textarea.innerHTML = this.options.data;

			//вставляем форму, а в форму текстовую область с данными для печати и инпут с именем шаблона
			this.$el.append($(form).append(textarea ).append(input));

			return this
		}
	});

	return App.Views.Print;
});
