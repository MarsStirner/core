define(function(require) {
    var template = require('text!templates/diagnostics/instrumental/research-groups-list-item.html');

    return View.extend({
        initialize: function() {
            var view = this;

            view.collection.on('reset', function() {
                view.render();
            });

            view.collection.on('fetch', function() {
                view.$el.html('<div class="msg">Загрузка...</div>');
            });

        },
        renderAll: function(treeData) {
            var view = this;

            view.$el.html('<div class="tree"></div>');
            view.$groups_list = view.$('.tree');

            view.$groups_list.append(_.template(template, {
                items: treeData,
                template: template
            })).on('click', 'li', function(event) {
                event.stopPropagation();
                var $this = $(this);
                var code = $this.data('code');

                $this.siblings().removeClass('open').addClass('closed');
                $this.toggleClass('open').toggleClass('closed').addClass('clicked');

                view.$groups_list.find('.clicked').removeClass('clicked');
                $this.addClass('clicked');

                if ($this.hasClass('parent')) {
                    pubsub.trigger('group:parent:click');
                } else {
                    pubsub.trigger('group:click', code);
                }
                pubsub.trigger('research:deselected');

                var code = $(this).data('code');
                console.log('code', code, treeData);
            });
        },
        renderNoResult: function() {
            var view = this;
            view.$el.html('<div class="msg">Нет результатов</div>');
        },

        render: function() {
            var view = this;
            var treeData = view.collection.toJSON();

            if (treeData.length > 0) {
                view.renderAll(treeData);
            } else {
                view.renderNoResult();
            }

            return this;
        },
        close: function() {
            this.collection.off();
            this.$el.remove();
            this.remove();
        }

    });

});
