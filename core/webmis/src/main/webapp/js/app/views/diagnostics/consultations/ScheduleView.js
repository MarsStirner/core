define(function(require) {
    var template = require('text!templates/diagnostics/consultations/time-list.html');

    return View.extend({
        initialize: function() {
            this.collection = new(Collection.extend({
                model: Model
            }))();

            pubsub.on('date:selected consultation:selected', function() {
                this.$el.html('');
            }, this);
        },

        events: {
            'click input': 'onClickTime'
        },

        template: template,

        onClickTime: function(e) {
            var $target = this.$(e.target);
            var checked = $target.prop('checked');
            var time = $target.val();
            var id = $target.data('id');
            var index = $target.data('index');

            this.$('#timetable-items input').prop('checked', false);
            $target.prop('checked', checked);

            if(checked){
              pubsub.trigger('time:selected', {
                time: time,
                id:id,
                index:index
              });
            }else{
              pubsub.trigger('time:unselected');
            }

        },

        resetAll: function(){
            this.$('#timetable-items input').prop('checked', false);
            pubsub.trigger('time:unselected');
        },

        disableAll: function(){
            this.$('#timetable-items input').prop('disabled', true);
        },

        enableAll: function(){
            this.$('#timetable-items input').prop('disabled', false);
        },

        disable: function(){
          this.resetAll();
          this.disableAll();
        },

        enable: function(){
          this.enableAll();
        },

        render: function() {
            console.log('shedule render');
            this.$el.html(_.template(this.template, {
                items: this.collection.toJSON()
            }));

            this.trigger('after:render');

            return this;
        },

        close: function() {
            pubsub.off('date:selected');
            pubsub.off('consultation:selected');
            this.collection.off();
            this.$el.remove();

        }
    });
});
