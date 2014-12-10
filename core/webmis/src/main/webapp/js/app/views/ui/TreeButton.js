define(function (require) {

    var BaseView = require('views/prescriptions/views/BaseView');

    var TreeButton = BaseView.extend({
        template: '<button type="button" class="" data-icon-primary="icon-print">Печать</button>',
        render: function () {
            BaseView.prototype.render.call(this);

            this.$el.attr('id', this.cid);
            return this;
        },

        getMenuItems: function () {
            return {};
        },

        getMenuCallback: function (key, options) {},

        addMenu: function () {
            var self = this;
            console.log('addMenu', this.cid)

            if (_.isEmpty(this.getMenuItems())) {
                this.$el.find('button').button().button('disable');
                return;
            } else {
                this.$el.find('button').button().button('enable');
            }
            // $.contextMenu( 'destroy');
            // $.contextMenu( 'destroy', '#'+this.cid+ 'button');

            $.contextMenu({
                className: 'webmis-menu',
                position: function (contextMenu, x, y) {
                    var $printButton = contextMenu.$trigger;
                    var left = $printButton.offset().left;
                    var top = $printButton.offset().top + $printButton.innerHeight();

                    contextMenu.$menu.offset({
                        left: left,
                        top: top
                    });
                },
                appendTo: '#' + self.cid,
                selector: '#' + self.cid + ' button',
                trigger: 'left',
                callback: function (key, options) {
                    self.getMenuCallback(key, options);
                },
                items: self.getMenuItems()
            });

        },

        afterRender: function () {
            this.addMenu();
        }
    });

    return TreeButton;
});
