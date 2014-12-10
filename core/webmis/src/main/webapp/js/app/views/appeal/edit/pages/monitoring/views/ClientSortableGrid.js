define(function(require) {
    /**
     * Базовый класс для виджетов-таблиц сортируемых на клиенте
     * @type {*}
     */
    var ClientSortableGrid = Backbone.View.extend({
        events: {
            "click th.sortable": "onThSortableClick"
        },

        initialize: function() {
            //вызывается и после фетча и после сорта
            this.collection.on("reset", this.render, this).fetch();
            //в нашей версии бэкбона - нету :(
            //this.collection.on("sort", this.renderItems, this);
        },

        onThSortableClick: function(event) {
            var $target = $(event.currentTarget);

            var sortConditions = this.updateSortConditions($target);
            this.applySort(sortConditions);
        },

        /**
         * Применяет сортировку коллекции по переданным памметрам
         * @param sortConditions {{sortField: string, sortType: string, sortDirection: "desc" || "asc"}}
         */
        applySort: function(sortConditions) {
            this.collection.comparator = this.getComparator(sortConditions.sortField, sortConditions.sortType, sortConditions.sortDirection);
            this.collection.sort({
                sortRequest: true
            });
        },

        /**
         * Добавляет визуальную индикацию текущей сортировки, извлекает и возвращает выбранные параметры сортировки
         * @param $targetTh
         * @returns {{sortField: string, sortType: string, sortDirection: "desc" || "asc"}}
         */

        updateSortConditions: function($targetTh) {
            if (!this.$caret) {
                this.$caret = $('<i/>');
            }

            this.$caret.detach().removeClass();

            if ($targetTh.hasClass("sorted")) {
                if ($targetTh.hasClass("asc")) {
                    $targetTh.removeClass("asc").addClass("desc");
                    this.$caret.addClass("icon-caret-down");
                } else if ($targetTh.hasClass("desc")) {
                    $targetTh.removeClass("desc").addClass("asc");
                    this.$caret.addClass("icon-caret-up");
                }
            } else {
                this.$("th").removeClass("sorted asc desc");
                $targetTh.addClass("sorted asc");
                this.$caret.addClass("icon-caret-up");
            }

            this.$caret.appendTo($targetTh);

            return {
                sortField: $targetTh.data("sort-field"),
                sortType: $targetTh.data("sort-type"),
                sortDirection: ($targetTh.hasClass("desc") ? "desc" : "asc")
            };
        },

        /**
         * Возвращает фукцию сортировки коллекции по заданным параметрам
         * @param fieldName
         * @param sortType
         * @param sortDirection
         * @returns {Function}
         */
        getComparator: function(fieldName, sortType, sortDirection) {
            switch (sortType) {
                case "datetime":
                case "numeric":
                    return function(itemA, itemB) {
                        var a = parseFloat(itemA.get(fieldName)),
                            b = parseFloat(itemB.get(fieldName));

                        if (a > b || isNaN(b)) return sortDirection === "asc" ? 1 : -1;
                        else if (a < b || isNaN(a)) return sortDirection === "asc" ? -1 : 1;
                        else return 0;
                    };
                    break;
                case "bloodPressure":
                    var fieldsNames = fieldName.split("/");
                    var firstField = fieldsNames[0];
                    var secondField = fieldsNames[1];
                    return function(itemA, itemB) {
                        var a1 = parseFloat(itemA.get(firstField)),
                            a2 = parseFloat(itemA.get(secondField)),
                            b1 = parseFloat(itemB.get(firstField)),
                            b2 = parseFloat(itemB.get(secondField));

                        if (a1 > b1 || isNaN(b1)) {
                            return sortDirection === "asc" ? 1 : -1;
                        } else if (a1 < b1 || isNaN(a1)) {
                            return sortDirection === "asc" ? -1 : 1;
                        } else if (((a1 === b1) && (a2 > b2)) || ((a1 === b1) && isNaN(b2))) {
                            return sortDirection === "asc" ? 1 : -1;
                        } else if (((a1 === b1) && (a2 < b2)) || ((a1 === b1) && isNaN(a2))) {
                            return sortDirection === "asc" ? -1 : 1;
                        } else {
                            return 0;
                        }
                    };
                    break;
                default:
                    return function(itemA, itemB) {
                        var a = itemA.get(fieldName).toString(),
                            b = itemB.get(fieldName).toString();

                        if (a > b) return sortDirection === "asc" ? 1 : -1;
                        else if (a < b) return sortDirection === "asc" ? -1 : 1;
                        else return 0;
                    };
            }
        },

        /**
         * Перерисовывает только ряды таблицы
         */
        renderItems: function() {
            /*this.$("tbody").empty().append(this.collection.map(function (item) {
             return _.template(this.itemTemplate, {item: item});
             }, this));*/
            this.$("tbody").empty().append(_.template(this.itemTemplate, this.data()));
        },
        data: function() {
            return {};
        },

        render: function(c, options) {
            //этот параметр передаётся только при сортировке, и в этом случае
            //мы хотим отрендерить только ряды
            options = options || {
                sortRequest: false
            };
            if (!options.sortRequest) {
                //сейчас в теле таблицы данных нет
                this.$el.html(_.template(this.template, this.data()));
                //this.$el.html(_.template(this.template));
            }

            this.renderItems();

            return this;
        }
    });

    return ClientSortableGrid;
});
