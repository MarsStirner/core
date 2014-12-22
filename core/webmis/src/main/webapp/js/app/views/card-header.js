/**
 * User: FKurilov
 * Date: 22.11.12
 */
define(function (require) {
    var template = require('text!templates/card-header.tmpl')
    require('models/appeal');
    var userPermissions = require('permissions');
    var CardNav = require('views/CardNav');
    var ContextPrintButton = require('views/ContextPrintButton');

    App.Views.CardHeader = View.extend({
        events: {
        },

        template: template,

        data: function () {
            //console.log('data', this.model.toJSON());
            return this.model.toJSON();
        },

        initialize: function (options) {
            console.log('header options', options)
            this.model.on("change", this.render, this);
        },

        renderContextPrintButton: function () {
            this.contextPrintButton = new ContextPrintButton({
                context: 'f066',//this.model.get('context')
                data: {
                    event_id: this.model.get('id'),
                    context_type: 'event',
                    client_id: this.model.get('patient').get('id'),
                    additional_context: {
                        currentOrgStructure: '',
                        currentOrganisation: 3479,
                        currentPerson: Core.Cookies.get('userId')
                    }
                }
            });

            this.contextPrintButton.setElement(this.$el.find('.context-print-button'));
            this.contextPrintButton.render();
        },

        /**
         * @param options
         * {
         * 	visible: true,
         * 	dropDownItems: [{
         * 		label: "",
         * 		params: {
         * 			templateName: "",
         * 			data: {}
         *		}
         * 	}],
         * 	label: "",
         * 	params: {
         * 		templateName: "",
         * 		data: {}
         * 	}
         * }
         */
        showPrintBtn: function (options) {
            if (options) {
                var $printBtnHolder = $("<div/>");
                var $printBtn = $('<button class="PrintBtn"/>');

                $printBtn
                    .button({
                        label: options.label
                    })
                    .click(function (event) {
                        event.preventDefault();
                        options.handler.apply(options.scope);
                    });

                $printBtnHolder.append($printBtn);

                if (options.dropDownItems && options.dropDownItems.length) {
                    var $dropDown = $(
                        '<div class="DDList" style="display: block; left: -200px;">' +
                        '<div class="Content ButtonContent" style="top: 0; max-height: 30em; width: 20em;">' +
                        '<ul></ul>' +
                        '</div>'
                    );
                    var $list = $dropDown.find("ul");

                    _(options.dropDownItems).each(function (ddi) {
                        $list.append($("<li><a href='#' class='SubPrint'>" + ddi.label + "</a></li>").click(function () {
                            event.preventDefault();
                            ddi.handler.apply(options.scope);
                        }));
                    });

                    var $dropDownTrigger = $("<button/>").button({
                        text: false,
                        label: "Выбор формы",
                        icons: {
                            primary: "ui-icon-triangle-1-s"
                        }
                    })
                        .click(function () {
                            $dropDown.position({
                                my: "right top",
                                at: "left bottom",
                                of: $(this).parent().parent()
                            }).toggleClass("Active");

                            return false;
                        });

                    $printBtnHolder.append($dropDownTrigger).buttonset();
                    $printBtnHolder.after($dropDown);
                }

                this.$(".CardPrint").empty().append($printBtnHolder).show();
            } else {

            }

            this.$el.find(".context-print-button").show();
        },

        hidePrintBtn: function () {
            this.$(".CardPrint").empty();
            this.$el.find(".context-print-button").hide();
        },

        /*onPrintBtnClick: function (event) {
			console.log(event);
		},

		onSubPrintClick: function (event) {
			console.log(event);
		},*/

        render: function () {
            this.$el.html($.tmpl(this.template, this.data()));

            var patient = this.model.get('patient');
            var patientId = patient.get('id');

            this.cardNav = new CardNav({
                permissions: userPermissions,
                patient: patient,
                structure: [{
                    link: '/patients/' + patientId + '/',
                    name: 'Карточка пациента',
                    permissions: ['see_patient_card']
                }, {
                    link: '/patients/' + patientId + '/appeals/',
                    name: 'Госпитализации',
                    permissions: ['see_patient_appeals'],
                    active: true
                }, {
                    link: '/patients/' + patientId + '/summary',
                    name: 'Сводная информация',
                    permissions: ['see_patient_summary']
                }]
            });
            this.$el.find('.CardNav').append(this.cardNav.render().el)

            this.renderContextPrintButton();

            return this;
        }
    });

    return App.Views.CardHeader;
});
