define(function(require) {
    var template = require('text!templates/diagnostics/consultations/consultants-list-item.html');

    return View.extend({
        initialize: function() {
            _.bindAll(this, 'isSelected');

            this.collection.on('reset', function() {
                this.render();
            }, this);

            this.collection.on('fetch', function() {
                this.renderOnFetch();
            }, this);

        },

        isSelected: function(code){
            return ((this.selectedItemCode && code) && (this.selectedItemCode == code));
        },

        renderAll: function(data) {
            var view = this;
            view.$el.unbind('click');

            view.$el.html(_.template(template, {
                items: data,
                template: template,
                selected: this.isSelected
            })).on('click', 'li', function() {
                var $this = $(this);
                view.$el.find('.clicked').removeClass('clicked');
                $this.addClass('clicked');

                var id = $this.data('id');
                pubsub.trigger('consultant:selected', id);
            });
        },

        renderNoResults: function() {
            this.$el.html('<div class="msg">Нет результатов</div>');
        },

        renderOnFetch: function() {
            this.$el.html('<div class="msg">Загрузка...</div>');
        },

        render: function() {
            var data = this.collection.toJSON();

            if (_.isArray(data) && data.length > 0) {
                this.renderAll(data);
                this.selectedItemCode = false;
            } else {
                this.renderNoResults();
            }


            return this;

        },

        close: function(){

            this.collection.off(null,null,this);
            this.$el.remove();

        }
    });
});
