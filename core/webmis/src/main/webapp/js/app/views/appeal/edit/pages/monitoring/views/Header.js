define(function(require) {
    var shared = require('views/appeal/edit/pages/monitoring/shared');

    var headerTmpl = require('text!templates/appeal/edit/pages/monitoring/header.tmpl');

    var BaseView = require('views/appeal/edit/pages/monitoring/views/BaseView');
    var CloseAppealView = require('views/appeal/edit/popups/CloseAppealView');

    /**
     * Заголовок страницы
     * @type {*}
     */
    var Header = BaseView.extend({
        template: headerTmpl,
        initialize: function(options){
            BaseView.prototype.initialize.apply(this);
            this.appeal = options.appeal;

        },

        data: function() {
            //console.log('canClose',this.canClose())
            var appeal = this.appeal;
            return {
                appealNumber: appeal.get("number"),
                appealIsUrgent: appeal.get("urgent"),
                appealIsClosed: appeal.get('closed'),
                canClose: this.canClose()
            };
        },

        events: {
            'click .close-appeal': 'openCloseAppealPopup'
        },
        canClose: function() {
            if (this.appeal.get('closed') || ((Core.Data.currentRole() === ROLES.NURSE_RECEPTIONIST) || (Core.Data.currentRole() === ROLES.NURSE_DEPARTMENT))) {
                return false;
            } else {
                return true;
            }
        },

        openCloseAppealPopup: function() {
            //console.log('openCloseAppealPopup');
            this.closeAppealView = new CloseAppealView({
                title: 'Закрытие истории болезни',
                width: '50em',
                saveText: 'Закрыть',
                appeal: shared.models.appeal
            });
            this.closeAppealView.render().open();
        },

        render: function() {
            BaseView.prototype.render.apply(this);
            this.$('.close-appeal').button();
            return this;
        }
    });

    return Header;
});
