define(function(require){
    var BaseView = require('views/prescriptions/views/BaseView');
    var template = require('text!views/prescriptions/templates/prescriptionsExecution/group.html');
    var PrescriptionView = require('views/prescriptions/views/prescriptionsExecution/Prescription');

    return BaseView.extend({
        template: template,

        initialize: function(){
            this.childs = []; 
        },

        closeChilds: function(){
			_.invoke(this.childs, 'tearDown');
        },

		tearDown: function() {
            this.closeChilds();
            BaseView.prototype.tearDown.call(this);
        },

        renderChilds: function(){
            this.closeChilds();

            var prescriptions = this.options.group;
            var $prescriptionsEl = this.$el.find('.prescriptions');
            
            _.each(prescriptions, function(prescription){
                var prescriptionView = new PrescriptionView({
                    model: prescription,
                    mainCollection: this.options.mainCollection
                });
                this.childs.push(prescriptionView);
                $prescriptionsEl.append(prescriptionView.render().el);

            }, this);
        
        },

        render: function(){
            BaseView.prototype.render.call(this); 
            this.renderChilds();
            return this;
        },

 
        data: function(){
            var data = {};
            data.groupName = this.options.groupName;
            // console.log('group data', data); 
            return data;
        }
    });

});
