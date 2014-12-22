/**
 * User: FKurilov
 * Date: 21.08.12
 */
define([
	"text!templates/appeal/edit/popups/thesaurus.tmpl",
	"collections/thesaurus-terms"
], function (tmpl) {
	var termDispatcher = _({}).extend(Backbone.Events);

	var ThesaurusTreeNode = View.extend({
		tagName: "li",

		events: {
			"click span": "onClick",
			"click": "toggleNode",
			"mouseover": "onMouseOver",
			"mouseout": "onMouseOut"
		},

		isLeafNode: false,

		initialize: function (options) {
			this.parent = this.options.parent;
		},

		toggleNode: function (event) {
			if (event) event.stopPropagation();
			if (!this.$el.hasClass("Opened") && !this.isLeafNode && this.model.get("childrenTerms").isEmpty()) {
				this.model.get("childrenTerms").on("reset", this.onChildrenTermsReset, this).fetch();
			} else if (this.isLeafNode) {
				this.$el.removeClass("Opened");
			} else {
				this.$el.toggleClass("Opened");
			}
		},

		onClick: function (event) {
			if (event) event.stopPropagation();
			if (!this.isLeafNode) {
				this.termSelected();
			}
		},

		onMouseOver: function (event) {
			$(event.target).css({
				"background-color": "#f0f3fc",
				"cursor": "pointer"
			});

		},

		onMouseOut: function (event) {
			$(event.target).css("background-color", "");
		},

		onChildrenTermsReset: function () {
			var self = this;

			if (this.model.get("childrenTerms").isEmpty()) {
				this.isLeafNode = true;
				this.termSelected();
			} else {
				this.createBranch();
			}
		},

		createBranch: function () {
			new ThesaurusTree({collection: this.model.get("childrenTerms"), parent: this}).render().$el.appendTo(this.$el.addClass("Opened"));
		},

		termSelected: function () {
			var parentNode = this.options.parent;
			var termsChain = [this.model.get("name")];

			if ((this.model.get("template").split("%s:").length) > 1) {
				while (parentNode) {
					termsChain.push(parentNode.model.get("name"));
					parentNode = parentNode.parent;
				}

			}

			termsChain = termsChain.reverse().join(" ");

			//term += " " + this.model.get("name");

			termDispatcher.trigger("term:selected", {term: termsChain});
		},

		render: function () {
			this.$el.html($("<span/>").text(this.model.get("name")));

			return this;
		}
	});

	var ThesaurusTree = View.extend({
		tagName: "ul",
		isRoot: false,
		//className: "MKBList",

		/*events: {
			"click li span": "onTermSelected"
		},*/

		initialize: function (options) {
			this.collection.on("reset", this.onTermTreeReset, this);
			this.isRoot = options.isRoot;
			if (this.isRoot) {
				this.collection.comparator = function (a, b) {
					return a.get("code").length - b.get("code").length;
				}
			}
		},

		onTermTreeReset: function () {
			if (this.isRoot && this.collection.length) {
				this.collection.setParams({
					filter: {code: ""}
				});
				this.collection.parentGroupId = this.collection.first().get("id");
				this.isRoot = false;
				this.collection.fetch();
			} else {
				this.render();
			}
		},

		onTermSelected: function (event) {
			//this.trigger("term:selected", $(event.currentTarget).text());
			//this.trigger("term:selected", event.term);
		},

		render: function () {
			this.$el.empty();

			this.collection.each(function (term) {
				var termTreeItem = new ThesaurusTreeNode({model: term, parent: this.options.parent});
				termTreeItem.on("term:selected", this.onTermSelected, this);
				this.$el.append(termTreeItem.render().el);
			}, this);

			return this;
		}
	});

	var Thesaurus = App.Views.Thesaurus = View.extend({
		className: "popup",
		template: tmpl,
		
		events: {
			"click .ShowHidePopup": "onCancel",
			"click .Confirm": "onConfirm",
			"keyup .selectedTerms": "onSelTermsKeyUP",
			"change .selectedTerms": "onSelTermsKeyUP"
		},
		
		initialize: function () {
			this.model = new (Model.extend({
				rootCode: "",
				selectedTerms: "",
				attrId: ""
			}))();

			this.model.on("change:rootCode", this.onRootCodeChange, this);
			this.model.on("change:selectedTerms", this.onSelectedTermsChange, this);

			this.termTree = new ThesaurusTree({
				collection: new App.Collections.ThesaurusTerms(),
				className: "Tree",
				isRoot: true
			});

			termDispatcher.on("term:selected", this.appendTerm, this);
		},

		/*setRootCode: function (code) {
			this.model.set({rootCode: code});
		},*/

		/*setSelectedTerms: function (terms) {
			this.model.set({selectedTerms: terms});
		},*/



		appendTerm: function (event) {
			var term = event.term;
			var selectedTerms = this.model.get("selectedTerms");

			if (selectedTerms.length) {
				// if (!Core.Strings.endsWithPunctuationChar(selectedTerms)) {
				// 	selectedTerms += ", ";
				// } else {
				// 	selectedTerms += " ";
				// }
				selectedTerms += " ";
				selectedTerms += term;
			} else {
				selectedTerms = term;
			}

			this.model.set({
				//selectedTerms: (selectedTerms.length ? selectedTerms + ", " + term.get("name") : term.get("name"))
				selectedTerms: (selectedTerms)
			});
		},

		onRootCodeChange: function () {
			var self = this;
			if (this.model.get("rootCode")) {
				this.termTree.isRoot = true;
				this.termTree.collection.setParams({
					filter: {code: self.model.get("rootCode")},
					limit: 0,
					page: 0
				});
				this.termTree.collection.parentGroupId = undefined;

				this.termTree.collection.reset().fetch();
			}
		},

		onSelectedTermsChange: function () {
			this.$(".selectedTerms").html(this.model.get("selectedTerms")).change();
		},

		onSelTermsKeyUP: function (event) {
			this.model.set({
				selectedTerms: $(event.currentTarget).html()
			}, {
				silent: true
			});
		},

		onConfirm: function () {
			this.trigger("thesaurus:confirmed", this.model.toJSON());
			this.close();
		},

		onCancel: function () {
			this.close();
		},

		render: function () {
			this.$el.html($.tmpl(this.template, {}));
			this.termTree.setElement(this.$(".ThesaurusTermTree")).render();

			if (!this.$el.hasClass("webmis")) {
				$(this.el).dialog({
					autoOpen: false,
					width: "60em",
					modal: true,
					dialogClass: "webmis",
					title: "Тезаурус",
					buttons: [
						{
							text: "Добавить",
							"class": "Confirm button-color-green",
							click: _.bind(this.onConfirm, this)
						},
						{
							text: "Отмена",
							"class": "ShowHidePopup",
							click: _.bind(this.onCancel, this)
						}
					]
				});

				this.$("a").click(function (event) {
					event.preventDefault();
				});

				//UIInitialize(this.el);
			}

			return this;
		},

		open: function (opts) {
			this.model.set({
				rootCode: opts.code || "",
				selectedTerms: opts.terms || "",
				attrId: opts.attrId || "",
				propertyType: opts.propertyType || "value"
			});

			//$(".ui-dialog-titlebar").hide();
			this.$el.dialog("open");
		},

		close: function () {
			//$(".ui-dialog-titlebar").show();
			this.$el.dialog("close");
		}
	});

	return {
		Thesaurus: Thesaurus,
		ThesaurusTree: ThesaurusTree,
		ThesaurusTreeNode: ThesaurusTreeNode,
		termDispatcher: termDispatcher
	};
});