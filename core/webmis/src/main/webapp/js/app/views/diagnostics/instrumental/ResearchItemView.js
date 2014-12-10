define(function(require) {

    var itemTemplate = require('text!templates/diagnostics/instrumental/researchs-list-item.html')

    var ItemView = Backbone.View.extend({
        className: 'item',

        events: {
            'change input': 'toggleSelect'
        },
        toggleSelect: function(e) {
            var $target = this.$(e.target);

            if ($target.prop('checked')) {
                pubsub.trigger('research:selected', this.model.get('code'));
            } else {
                pubsub.trigger('research:deselected', this.model.get('code'));
            }
            //console.log('toggleSelect');
        },
        initialize: function() {
            this.$el.attr('data-code', this.model.get('code'));
            this.$el.attr('data-cid', this.model.cid);
        },

        modelData: function() {
            var data = _.extend(this.model.toJSON(), {
                cid: this.model.cid
            });
            return data;
        },

        render: function() {
            this.$el.html(_.template(itemTemplate, this.modelData(), {
                variable: 'data'
            }));

            this.ui = {};
            this.ui.$checkbox = this.$el.find('input');
            return this;
        },
        close: function(){
            this.$el.remove();
            this.remove();
        }

    });

    return ItemView;

});
