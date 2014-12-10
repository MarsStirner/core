define(function (require) {
    var template = require('text!views/prescriptions/templates/appeal/create-prescription-button.html');

    var BaseView = require('views/prescriptions/views/BaseView');

    var PrescriptionNewView = require('views/prescriptions/views/appealPrescriptions/PrescriptionNewView');
    var Types = Collection.extend({
        url: '/api/v1/prescriptions/types'
    });

    var ItemView = BaseView.extend({
        events: {
            'click': 'clickHandler'
        },
        tagName: 'li',
        data: function () {
            return this.model.toJSON();
        },
        template: '<%= data.title %>',
        clickHandler: function () {
            var popup = new PrescriptionNewView({
                actionTypeId: this.model.get('id'),
                appeal: this.options.appeal,
                collection: this.options.collection
            });

            popup.render();
            popup.open();


        }

    });

    var CreatePrescriptionButton = BaseView.extend({
        template: template,
        initialize: function () {
            this.childs = [];

            this.collection = new Types();
            this.listenTo(this.collection, 'reset', this.render);
            this.collection.fetch();
        },
        data: function () {
            var data = {};
            return data;
        },
        closeChilds: function () {
            _.invoke(this.childs, 'tearDown');
        },

        tearDown: function () {
            this.closeChilds();
            BaseView.prototype.tearDown.call(this);
        },

        renderChilds: function () {
            this.closeChilds();

            var $itemsEl = this.$el.find('.items');

            this.collection.each(function (model) {
                var itemView = new ItemView({
                    model: model,
                    appeal: this.options.appeal
                });
                this.childs.push(itemView);
                $itemsEl.append(itemView.render().el);

            }, this);

        },

        render: function () {
            BaseView.prototype.render.call(this);
            this.renderChilds();
            return this;
        },

        afterRender: function () {
            var self = this;
            this.ui = {};
            this.ui.$mainButton = this.$el.find('.main-button');
            // this.ui.$dropDownTriggerButton = this.$el.find('.dropdown-trigger-button');
            this.ui.$dropDown = this.$el.find('.DDList');

            this.ui.$mainButton.button().click(function () {
                self.ui.$dropDown.position({
                    my: "left top",
                    at: "left bottom",
                    of: $(this).parent().parent()
                }).toggleClass("Active");

                return false;
            });

            // this.ui.$dropDownTriggerButton.button({
            //     text: false,
            //     icons: {
            //         primary: "ui-icon-triangle-1-s"
            //     }
            // }).click(function () {
            //     self.ui.$dropDown.position({
            //         my: "left top",
            //         at: "left bottom",
            //         of: $(this).parent().parent()
            //     }).toggleClass("Active");

            //     return false;
            // }).parent().buttonset();

        }


    });

    return CreatePrescriptionButton;
});
