define([
	"text!templates/ui/date.tmpl",
	"text!templates/ui/datetime.tmpl",
	"text!templates/appeal/edit/pages/examination-edit.tmpl",
	"models/dynamic/examination-edit",
	"views/grid"
], function (dateTemplate, dateTimeTemplate, examinationEditTemplate) {

	App.Views.ExaminationEdit = Form.extend({
		className:"ContentHolder",
		template: examinationEditTemplate,
		events: {
			"click .Actions.Cancel": "cancel"
		},

		initialize:function () {
			var view = this;

			this.model = new App.Dynamic.ExaminationEdit;
			this.model.fetch({
				success: function(){
					var GuiElementList = view.model.get("guiElementList" );
					var GroupList = view.model.get("groupList");
					var MainGroup = GroupList.at(0);

					var Groups = new GroupsView ({
						collection: MainGroup.get("childList" ),
						rootModel: view.model
					});


					view.$("h2").html(MainGroup.get("title"));


					view.$(".EditForm li").html(Groups.render().el);

				}
			});

		},

		render:function () {
			this.$el.html( $.tmpl(this.template) );

			return this;
		}
	});


	var GroupsView = View.extend({
		render: function(){
			var view = this;

			this.collection.each(function(model){
				var Group = new GroupView({
					model: model,
					rootModel: view.options.rootModel
				});

				view.$el.append( Group.render().el );
			});

			return this
		}
	});
	var GroupView = View.extend({
		className: "LineBlock",
		render: function(){
			var view = this;

			var RelatedGroup = view.options.rootModel.get("groupList").find(function(group){
				return group.get("id") == view.model.get("id")
			});

			if ( RelatedGroup ) {
				RelatedGroup.get("childList").each(function(child){

					var Element;

					var groupClass;

					switch ( child.get("layoutProperties" ).get("hSize") ) {
						case "half-quarter" : {
							groupClass = "QuartCol";

							break
						}
						case "quarter" : {
							groupClass = "HalfCol";

							break
						}
						case "half" : {
							groupClass = "LineCol";

							break
						}
						case "full" : {
							groupClass = "WideCol";

							break
						}
						default: {
							groupClass = "";
						}

					}

					if ( child.get("type") == "hGroup" || child.get("type") == "vGroup" ) {
						Element = new GroupView({
							model: child,
							className: groupClass,
							rootModel: view.options.rootModel
						});
					} else {
						var RelatedGuiElement = view.options.rootModel.get("guiElementList").find(function(guiElement){
							return guiElement.get("id") == child.get("id");
						});

						Element = new GuiElementView({
							model: RelatedGuiElement,
							className: groupClass,
							rootModel: view.options.rootModel
						});
					}

					view.$el.append( Element.render().el );
				});
			}


			return this
		}
	});

	var GuiElementView = View.extend({
		render: function(){
			var view = this;


			view.$el.append( view.make("label", {}, this.model.get("title")) );

			var element;

			switch ( this.model.get("type") ) {
				case "editBox": {
					element = view.make("input", {"type": "text"});
					if ( view.model.get("mandatory") == "true" ) {
						element.className = "Mandatory";
					}
					if ( view.model.get("readOnly") == "true" ) {
						element.className += " Disabled";
						element.disabled = true;
					}

					break
				}
				case "textMemo": {
					element = view.make("textarea", {rows:"5"});
					if ( view.model.get("mandatory") == "true" ) {
						element.className = "Mandatory";
					}
					if ( view.model.get("readOnly") == "true" ) {
						element.className += " Disabled";
						element.disabled = true;
					}

					break
				}
				case "editDate": {
					element = $.tmpl(dateTemplate, {
						mandatory: view.model.get("mandatory") == "true",
						readOnly: view.model.get("readOnly") == "true"
					});

					break
				}
				case "dateTimeEdit": {
					element = $.tmpl(dateTimeTemplate, {
						mandatory: view.model.get("mandatory") == "true",
						readOnly: view.model.get("readOnly") == "true"
					});

					break
				}
			}

			view.$el.append(element);

			UIInitialize(this.el);
			return this
		}
	});



	return App.Views.Examinations
});