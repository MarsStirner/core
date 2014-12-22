/**
 * User: FKurilov
 * Date: 10.07.12
 */
//TODO: коменты, поиск диагноза
define(["text!templates/mkb-directory.tmpl", "models/mkb-directory"], function (tmpl) {
	var MkbTreeNode = View.extend({
		tagName: "li",
		template: "<span><i class='bullet'>${bullet}</i>{{decorate(title, searchString)}}</span>",

		childrenLoaded: false,
		isSelectedDiagnosis: false,

		events: {
			"click": "onNodeClick"
		},

		onNodeClick: function (event) {
			if (event) event.stopPropagation();

			if (this.isSelectedDiagnosis) {
				this.deselectDiagnosis();

				return false;
			}

			if (this.model.hasChildren) {
				if (!this.childrenLoaded) {
					var childNodeList = new MkbTreeNodeList({
						model: this.model,
						holder: this.options.holder
					});

					childNodeList.on("diagnosisDetected", this.onDiagnosisDetected, this);

					this.$el.append(childNodeList.render().el);

					this.childrenLoaded = true;
				}
			} else {
				this.selectDiagnosis();
				this.notifyNodeSelectionChange();

				return false;
			}

			this.$el.toggleClass("Opened");

			this.notifyNodeSelectionChange();
		},

		onDiagnosisDetected: function () {
			this.model.hasChildren = false;
			this.selectDiagnosis();
		},

		selectDiagnosis: function () {
			this.isSelectedDiagnosis = true;
			this.$el.addClass("Opened").find("span").addClass("Selected");

			this.options.holder.trigger("diagnosisSelected", {
				selectedNode: this
			});
		},

		deselectDiagnosis: function () {
			this.isSelectedDiagnosis = false;
			this.$el.removeClass("Opened").find("span").removeClass("Selected");

			this.options.holder.trigger("diagnosisDeselected", {
				selectedNode: this
			});
		},

		notifyNodeSelectionChange: function () {
			this.options.holder.trigger("nodeSelected", {
				nodeModel: this.model
			});
		},

		render: function (searchString) {
			this.$el.html($.tmpl(this.template, {bullet: this.model.getBreadcrumbTitle(), title: this.model.getTitle(), searchString: searchString || ""}));

			return this;
		}
	});

	var MkbTreeNodes = View.extend({
		tagName: "ul",
		className: "MKBList",
		initialize: function () {
			this.collection.on("reset", this.ready, this);
		},

		ready: function () {
			var view = this;

			if (this.collection.length !== 0) {
				this.collection.each(function(model) {
					var node = new MkbTreeNode({
						model: model,
						holder: view.options.holder
					});

					view.$el.append(node.render(view.marked).el);
				});
				this.marked = null;
			} else {
				view.$el.append("<li><b>Диагноз не найден!</b></li>");
			}
		}
	});

	var MkbTreeNodeList = View.extend({
		tagName: "ul",

		initialize: function (options) {
			var child = _.find(this.model.relations, function (rel) {
				return rel.isChild;
			});

			if (child) {
				var childCollection = this.model.get(child.key);
				if (!childCollection.length) {
					childCollection.on("reset", this.onCollectionLoaded, this);
					childCollection.fetch();
				} else {
					this.onCollectionLoaded(childCollection);
				}
			}
		},

		onCollectionLoaded: function (collection) {
			if (collection.length) {
				collection.each(this.addNode, this);
			} else {
				this.trigger("diagnosisDetected");
			}
		},

		addNode: function (model) {
			if (model.get("id") || model.get("diagnosis")) {
				var node = new MkbTreeNode({
					model: model,
					holder: this.options.holder
				});
				this.$el.append(node.render().el);
			} else {
				console.log("MKB entry corrupted", model);
			}
		},

		render: function () {
			return this;
		}
	});

	var MkbHolder = View.extend({
		className: "MKBHolder",

		initialize: function () {
			this.model = new App.Models.MkbDirectory();

			this.classNodeList = new MkbTreeNodeList({
				model: this.model,
				className: "MKBList",
				holder: this
			});
		},

		render: function () {
			this.$el.append(this.classNodeList.render().el);

			return this;
		}
	});

	var MkbBreadcrumbs = View.extend({
		tagName: "ul",
		className: "BreadCrumb",

		render: function () {
			this.push("МКБ");

			return this;
		},

		onNodeSelected: function (event) {
			this.$el.empty();

			var model = event.nodeModel;

			while (model.get("parent")) {
				this.push(model.getBreadcrumbTitle());
				model = model.get("parent");
			}

			this.push("МКБ");
		},

		push: function (title) {
			$("<li/>").text(title).prependTo(this.$el);
		},

		pop: function () {
			this.$("li").last().remove();
		}
	});

	App.Views.MkbDirectory = View.extend({
		template: tmpl,
		className: "popup",

		selectedDiagnosis: undefined,

		events: {
			"click .ShowHidePopup": "close",
			"click #confirmSelection": "onConfirmSelectionClick",
			"keyup [name='search']": "onSearchPressKey",
			"click #searchButtonMKB": "onSearch",
			"change [name='typeSearchMKB']": "onChangeTypeSearch"
		},

		initialize: function () {
			this.mkbHolder = new MkbHolder();
			this.mkbBreadcrumbs = new MkbBreadcrumbs();

			this.mkbHolder.on("nodeSelected", this.mkbBreadcrumbs.onNodeSelected, this.mkbBreadcrumbs);
			this.mkbHolder.on("diagnosisSelected", this.onDiagnosisSelected, this);
			this.mkbHolder.on("diagnosisDeselected", this.onDiagnosisDeselected, this);
			this.mkbHolder.on("diagnosisDeselected", this.mkbBreadcrumbs.pop, this.mkbBreadcrumbs);
		},

		onDiagnosisSelected: function (event) {
			if (this.currentNode && this.currentNode !== event.selectedNode) {
				this.currentNode.$el.removeClass("Opened").find("span").removeClass("Selected");
				this.currentNode.isSelectedDiagnosis = false;
			}

			this.currentNode = event.selectedNode;

			//this.selectedDiagnosis = event.selectedNode.model.get("id") || event.selectedNode.model.get("code");
			this.selectedDiagnosis = event.selectedNode.model;
		},

		onDiagnosisDeselected: function (event) {
			this.selectedDiagnosis = undefined;
		},

		onConfirmSelectionClick: function () {
			// TODO: имя ивента поменять на 'mkb:confirmed', возвращать .toJSON()
			if (this.diagnosis) {
				this.diagnosis.trigger("selectionConfirmed", {selectedDiagnosis: this.selectedDiagnosis});
			} else {
				this.trigger("selectionConfirmed", {selectedDiagnosis: this.selectedDiagnosis});
			}


			//this.close();
		},

		onSearchPressKey: function (event) {
			if (event && event.keyCode === 13) {
				this.onSearch(event);
			} else if (this.typeSearchMKB === 2) {
				$(event.currentTarget).val(Core.Strings.toLatin($(event.currentTarget).val()));
			}
		},

		onSearch: function(event) {
			var $input = this.$('.MKBSearch input[name="search"]');
			var view = this;

			view.mkbBreadcrumbs.$("li:first").nextAll().remove();

			if ($input && !$input.val().length) {
				// Вернём всё обратно, если поле пустое
				view.mkbHolder.$el.empty();
				view.mkbHolder.initialize();
				view.mkbHolder.render();

				return;
			}

			var options = (this.typeSearchMKB === 2) ?
				{subGroupId: $input.val()} :
				{diagnosis: $input.val()};

			if (this.searchNodes) this.searchNodes.collection.off(null, null, this.searchNodes);

			this.searchNodes = new MkbTreeNodes({
				collection: new App.Collections.Mkbs([], options),
				holder: view.mkbHolder
			});

			this.searchNodes.marked = $input.val();

			view.mkbHolder.$el.empty().append(this.searchNodes.render().el);

			this.searchNodes.collection.fetch();

			/*if ($input.data("refresh-timeout")) {
				clearTimeout ( $input.data("refresh-timeout") );
			}

			var timeout,
				DELAY = 700;

			if ( $input.data("old-value") && $input.data("old-value") == $input.val() ) {
				clearTimeout ( $input.data("refresh-timeout") );
				return;
			}
			$input.data("old-value", $input.val());

			timeout = setTimeout(function () {

				view.mkbBreadcrumbs.$("li:first").nextAll().remove();

				if ( $input && !$input.val().length ) {
					// Вернём всё обратно, если поле пустое
					view.mkbHolder.$el.empty();
					view.mkbHolder.initialize();
					view.mkbHolder.render();

					return;
				}

				var collection = new App.Collections.Mkbs([], {
					diagnosis: $input.val(),
					sex: 1
				});

				view.mkbHolder.$el.empty();
				var nodes = new MkbTreeNodes({
					collection: collection,
					holder: view.mkbHolder
				});

				view.mkbHolder.$el.append(nodes.render().el);

				collection.fetch();
			}, DELAY);

			$input.data ( "refresh-timeout", timeout );*/


		},

		onChangeTypeSearch: function (event) {
			 this.typeSearchMKB = parseInt($(event.currentTarget).val());
		},

		render: function () {
			var view = this;
			if ($(this.$el.parent()).length === 0) {
				this.$el.html($.tmpl(this.template, {}));

				//$("body").append(this.el);

				$(view.el).dialog({
					autoOpen: false,
					width: "86em",
					title: "МКБ 10",
					dialogClass: "webmis MKBPopup",
					resizable: false,
					open: function () {
						/*var self = this;
						if ($('.MKBPopup .ui-dialog-buttonset .Cancel').length === 0) {
							$('.MKBPopup .ui-dialog-buttonset').append('<a href="#" class="Actions Cancel">Отмена</a>');
						}*/
						/*$(document).on('click', '.MKBPopup .ui-dialog-buttonset .Cancel', function (event) {
							event.preventDefault();
							$(self).dialog("close");
						});*/
					},

					buttons: [
						{
							text: "Выбрать",
							id: "confirmSelection",
							click: function () {
								view.onConfirmSelectionClick();
								$(this).dialog("close");
							}}
						,
						{
							text: "Отмена",
							click: function () {
								$(this).dialog("close");
							}
						}
					]
				});

				this.$("a").click(function (event) {
					event.preventDefault();
				});

				this.$(".MKB").append(this.mkbBreadcrumbs.render().el, this.mkbHolder.render().el);
			}

			return this;
		},

		open: function (options) {
			this.diagnosis = options ? options.diagnosis : undefined;
			//$(".ui-dialog-titlebar").hide();
			this.$el.dialog("open");

			// Повторно вызываем функцию для стилизации элементов формы
			UIInitialize(this.el);
		},

		close: function () {
			//$(".ui-dialog-titlebar").show();
			this.$el.dialog("close");
		}
	});

	return App.Views.MkbDirectory;
});
