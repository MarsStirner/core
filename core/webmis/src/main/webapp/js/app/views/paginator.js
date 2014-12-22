define(["text!templates/paginator.tmpl"], function (template) {
	return App.Views.Paginator = View.extend ({
		events: {
			"click a": "navigate"
		},

		template: template,

		navigate: function ( event ){
			var $target = $(event.target);

			this.collection.setParams({
				page: Core.Url.extractPage( $target.attr("href") )
			});
			this.collection.fetch();

			return false
		},

		initialize: function (){
			this.options = $.extend({
				path: ""
			}, this.options);

			// Создаём очередь, блокирующую рендер
			//Core.Queue.add ("paginator:loading", "collection");
			//Core.Queue.add ("paginator:loading", "template");

			this.collection.on ("reset", this.loaded, this);
			//this.on ("template:loaded", this.loaded, this);

			//this.loadTemplate("paginator");
		},

		loaded: function ( type ) {
			//if ( type == this.collection )
			//{
				//type = "collection";
			//}
			//Core.Queue.remove ("paginator:loading", type);

			this.ready();
		},

		ready: function () {
			//if ( Core.Queue.get ( "paginator:loading" ).length ) {
				//return false
			//}

			// Создаём пэйджинатор
			var collection = this.collection,
				page = parseInt(collection.requestData ? (collection.requestData.page || 1) : 1),
				recordsCount = parseInt(collection.requestData ? (collection.requestData.recordsCount || 0) : 0),
				pagesCount = Math.ceil ( recordsCount / parseInt(collection.requestData ? collection.requestData.limit : 10) );

			var prevPage = false,
				nextPage = false;

			this.$el.empty();
			if ( pagesCount > 1 ) {
				if ( page > 1 )
				{
					prevPage = page - 1;
				}

				if ( page < pagesCount )
				{
					nextPage = page + 1;
				}

				var pagesArray = [];
				for ( var i = 1; i <= pagesCount; i++ ) {
					pagesArray.push (i);
				}

				var path = this.options.path;

				//this.$el.html ( $("#paginator-template").tmpl({
				this.$el.html ( $(this.template).tmpl({
					prevPage: prevPage,
					nextPage: nextPage,
					Pages: pagesArray,
					page: page,
					path: path,
					pagesCount: pagesCount
				}) );

			}
		},
		render: function () {
			return this
		}
	});
});