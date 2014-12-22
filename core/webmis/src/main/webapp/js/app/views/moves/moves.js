define(function (require) {
    var template = require('text!templates/moves/moves-page.tmpl');
    var Moves = require('collections/moves/moves');
    var GridView = require('views/grid');
    //var PaginatorView= require('views/paginator');
    var SendToDepartmentView = require('views/moves/send-to-department');
    var HospitalBedView = require('views/moves/HospitalBedView');
    var Documents = require('views/documents/documents');

    function days_between(date1, date2) {

        // The number of milliseconds in one day
        var ONE_DAY = 1000 * 60 * 60 * 24;


        // Calculate the difference in milliseconds
        var difference_ms = Math.abs(date1 - date2);

        // Convert back to days and return
        return Math.round(difference_ms / ONE_DAY);

    }


    App.Views.Moves = View.extend({
        className: "ContentHolder",
        template: template,

        events: {
            "click #new-move": "toggleMoveTypes",
            "click #move-type-list li": "createNewMove"
        },

        initialize: function () {
            this.collection = new Moves();
            this.collection.appealId = this.options.appealId;

            this.appealIsClosed = this.options.appeal.closed;

            this.collection.on('remove', function () {
                this.collection.fetch();
            }, this);

            this.collection.on('reset', this.onCollectionReset, this);

            var allowToMove = ((Core.Data.currentRole() === ROLES.NURSE_RECEPTIONIST) || (Core.Data.currentRole() === ROLES.NURSE_DEPARTMENT));

            var gridTemplateId = "#moves-grid-department",
                rowTemplateId = "#moves-grid-row-department",
                lastRowTemplateId = "#moves-grid-last-row-department",
                defaultTemplateId = "#moves-grid-department-default";

            if (allowToMove && !this.appealIsClosed) {
                gridTemplateId = "#moves-grid-department-with-move";
                rowTemplateId = "#moves-grid-row-department-with-move";
                lastRowTemplateId = "#moves-grid-last-row-department-with-move";
            }

            this.grid = new GridView({
                popUpMode: true,
                collection: this.collection,
                template: "moves/moves-grid",
                gridTemplateId: gridTemplateId,
                rowTemplateId: rowTemplateId,
                lastRowTemplateId: lastRowTemplateId,
                defaultTemplateId: defaultTemplateId
            });


            this.moveTypeConstrs = {
                department: this.newSendToDepartment,
                hospitalbed: this.newHospitalBed
            };

            this.depended(this.grid);

            this.collection.fetch();
        },

        toggleMoveTypes: function (event) {
            this.$(".DDList").toggleClass("Active");
            event.stopPropagation();
        },

        onCollectionReset: function () {
            this.countBedDays();
            this.toggleDirectionText();
            this.toggleHospitalbedMenu();
            this.checkLeavedDocExists();
        },

        toggleDirectionText: function () {
            this.directionText = 'Направление в отделение';

            if (this.collection.length > 1) {
                this.directionText = 'Перевод в отделение';
            }
        },

        toggleHospitalbedMenu: function () {
            var lastMove = this.collection.last();
            var $hospitalbedAction = this.$('#hospitalbed-action');

            if (lastMove.get('bed') == 'Положить на койку') {
                $hospitalbedAction.show();
            } else {
                $hospitalbedAction.hide();
            }

            this.$('.direction').text(this.directionText);
        },

        checkLeavedDocExists: function () {
            var coll = this.collection;
            var shouldCheck = coll.length && coll.last().get("admission") && !coll.last().get("leave");

            if (shouldCheck) {
                var self = this;
                var leavedDocs = new Documents.Collections.DocumentList([], {
                    appeal: this.options.appeal,
                    appealId: this.options.appealId
                });
                leavedDocs.fetch({
                    data: {
                        filter: {
                            flatCode: "leaved"
                        }
                    }
                }).then(_.bind(function () {
                    this.$(".last-move-leave-col")
                        .append(
                            $("<button style='font-size: .8em;'>Закрыть движение</button>")
                            .prop("disabled", !leavedDocs.length)
                            .prop("title", "Для закрытия движения необходимо наличие выписки.")
                            .button()
                            .one("click", function () {
                                console.log("Закрыть движение");
                                $(this).prop("disabled", true);
                                $.ajax({
                                    type: "POST",
                                    url: DATA_PATH + ["appeals", self.options.appealId, "closemove"].join("/"),
                                    contentType: "application/json",
                                    complete: function () {
                                        console.log("Moving has been closed.");
                                        coll.fetch();
                                    }
                                })
                            })
                    );

                    /*if (leavedDocs.length > 0) {
						//this.$(".last-move-leave-col").css({color:"green"}).text("BTN [ACTIVE]");
						this.$(".close-last-move").enable();
					} else {
						this.$(".close-last-move").disable();
					}*/
                }, this));
            }
        },

        onCloseLastMoveClick: function (event) {
            console.log('aaaaaa');
        },

        createNewMove: function (event) {
            this.$(".DDList").removeClass("Active");
            var moveType = $(event.currentTarget).data("move-type");

            // Вызываем метод для создания соответствующего вида
            if ($.isFunction(this.moveTypeConstrs[moveType])) {
                this.moveTypeConstrs[moveType].call(this, event);
            }
        },

        //подсчёт дней, койко-дней - для последнего движения
        countBedDays: function () {

            //если госпитализация закрыта, то ничего не делаем
            if (this.appealIsClosed) return;

            var lastMove = this.collection.last();
            var days = days_between(new Date().getTime(), lastMove.get('admission'));
            var bedDays = days - 1;

            if (bedDays < 0) {
                bedDays = 0;
            }

            if ((lastMove.get('bed') !== 'Положить на койку') && (lastMove.get('bed') !== null)) {
                lastMove.set('days', days);
                lastMove.set('bedDays', bedDays);
            }

        },

        /***
         * Отмена прописки на койке
         * @param move
         */
        cancelMove: function (move) {
            var view = this;
            //@todo перенести в move.js
            var url = DATA_PATH + 'appeals/' + view.collection.appealId + '/hospitalbed/' + move.get('id');

            $.ajax({
                type: 'DELETE',
                url: url,
                dataType: 'jsonp',
                //beforeSend: function(jqXHR, settings){
                //	console.log('jqXHR',jqXHR);
                //	console.log('settings',settings);
                //},
                success: function (data) {
                    view.collection.trigger('remove');
                    pubsub.trigger('noty', {
                        text: 'Регистрация отменена',
                        type: 'warning'
                    });
                },
                error: function (data) {
                    pubsub.trigger('noty', {
                        text: 'Ошибка при попытке отменены регистрации',
                        type: 'error'
                    });
                }
            });


        },

        //Новое мероприятие/направление или перевод в отделение
        newSendToDepartment: function (event) {


            var collectionLength = this.collection.length;
            var previousMove = this.collection.models[collectionLength - 2];
            var lastMove = this.collection.models[collectionLength - 1];

            var moveDatetime = lastMove.get('leave') || lastMove.get('admission') || previousMove.get('leave') || previousMove.get('admission');

            if (!lastMove.get('leave') && lastMove.get('admission')) {
                moveDatetime = new Date().getTime();
            }
            var previousDepartmentName = false;
            var previousDepartmentDate = false;

            if (this.collection.length > 1) {
                previousDepartmentName = lastMove.get('unit');
                previousDepartmentDate = lastMove.get('admission');
            }


            var sendPopUp = new SendToDepartmentView({
                previousDepartmentName: previousDepartmentName,
                previousDepartmentDate: previousDepartmentDate,
                appealId: this.options.appeal.get("id"),
                clientId: this.options.appeal.get("patient").get("id"),
                moveDatetime: moveDatetime,
                popupTitle: this.directionText
            }).render().open();

            sendPopUp.on("closed", function () {
                this.collection.fetch();
            }, this);

        },

        //Новое мероприятие/регистрация на койку
        newHospitalBed: function () {
            this.trigger("change:viewState", {
                type: 'hospitalbed',
                options: {}
            });
            App.Router.updateUrl("appeals/" + this.options.appealId + "/hospitalbed/");
        },

        render: function () {

            this.grid.on('grid:rowClick', function (move, event) {
                event.preventDefault();

                if (_.indexOf(event.target.classList, 'cancel-bed-registration') >= 0) {
                    this.cancelMove(move);
                }
                if (_.indexOf(event.target.classList, 'bed-registration') >= 0) {
                    this.newHospitalBed(move);
                }
            }, this);

            var self = this;
            var allowToMove = ((Core.Data.currentRole() === ROLES.NURSE_RECEPTIONIST) || (Core.Data.currentRole() === ROLES.NURSE_DEPARTMENT));

            this.$el.html($.tmpl(this.template, {
                allowToMove: allowToMove,
                isClosed: self.options.appeal.isClosed()
            }));
            this.$("#lab-grid").html(this.grid.el);
            console.log(this.$("#new-move"));
            this.$("#new-move").button({
                icons: {
                    primary: "icon-plus icon-color-green",
                    secondary: "icon-caret-down"
                }
            });

            this.delegateEvents();
            this.grid.delegateEvents();

            return this;
        }
    });

    return App.Views.Moves
});
