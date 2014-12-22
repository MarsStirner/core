define(function (require) {
    var BaseView = require('views/prescriptions/views/BaseView');
    var template = require('text!views/prescriptions/templates/prescriptionsExecution/interval.html');
    require('qtip');
    var intervalTooltipTemplate = _.template(require('text!views/prescriptions/templates/interval-tooltip.html'), null, {
        variable: 'data'
    });
    var IntervalEdit = require('views/prescriptions/views/prescriptionsExecution/IntervalEdit');

    return BaseView.extend({
        template: template,
        events: {
            'click': 'openEditPopup'
        },
        tagName: 'span',

        initialize: function () {
            var prescriptionId = this.model.get('actionId');
            this.prescription = this.options.mainCollection.get(prescriptionId);
        },

        data: function () {
            var data = {};
            var rangeStart = this.options.mainCollection._filter.dateRangeMin * 1000;
            var rangeEnd = this.options.mainCollection._filter.dateRangeMax * 1000;

            data = this.model.toJSON();

            var coordinates = this.model.getIntervalCoordinates(rangeStart, rangeEnd);

            _.extend(data, coordinates);

            return data;
        },

        getTooltipText: function (id) {

            var html = intervalTooltipTemplate({
                prescription: this.prescription.toJSON(),
                interval: this.model.toJSON()
            });

            return html;
        },

        getContextMenuExecuteItem: function () {
            var self = this;
            var item = {};

            item.name = "Выполнить";
            item.callback = function (key, opt) {
                self.model.execute().then(function () {
                    self.options.mainCollection.fetch();
                });
            };

            return item;
        },

        getContextMenuCancelItem: function () {
            var self = this;
            var item = {};

            item.name = "Отменить";
            item.callback = function (key, opt) {
                self.model.cancel().then(function () {
                    self.options.mainCollection.fetch();
                });
            };

            return item;
        },

        getContextMenuCancelExecutionItem: function () {
            var item = {};
            var self = this;
            item.name = "Отменить исполнение";
            item.callback = function () {
                self.model.cancelExecution().then(function () {
                    self.options.mainCollection.fetch();
                });
            };

            return item;
        },

        getContextMenuEditItem: function () {
            var self = this;
            var item = {};
            item.name = "Редактировать";
            item.callback = function () {
                self.openEditPopup();
            };
            // item.disabled = true;
            return item;
        },

        openEditPopup: function () {
            if (!this.model.canBeEdited()) {
                return;
            }

            var intervalEdit = new IntervalEdit({
                model: this.options.model
            });

            intervalEdit.render().open();
        },

        getContextMenuItems: function () {
            var items = {};

            if (this.model.canBeExecuted()) {
                items.execute = this.getContextMenuExecuteItem();
            }

            if (this.model.canBeCanceled()) {
                items.cancel = this.getContextMenuCancelItem();
            }

            if (this.model.canBeCanceledExecution()) {
                items.cancelExecution = this.getContextMenuCancelExecutionItem();
            }

            if (this.model.canBeEdited()) {
                items.edit = this.getContextMenuEditItem();
            }

            return items;
        },

        afterRender: function () {
            var self = this;
            this.$el.qtip({
                content: {
                    // title: prescription.get('name'),
                    text: this.getTooltipText()
                },
                style: 'qtip-light',
                position: {
                    my: 'bottom left',
                    at: 'top left',
                    viewport: $('.groups')
                }
            });

            if (!_.isEmpty(this.getContextMenuItems())) {
                $.contextMenu({
                    autoHide: true,
                    selector: '[data-prescription="' + self.model.get("id") + '"]',
                    callback: function (key, options) {},
                    items: self.getContextMenuItems()
                });
            }
        },

        tearDown: function () {
            $.contextMenu('destroy');
            BaseView.prototype.tearDown.call(this);
        },

    });
});
