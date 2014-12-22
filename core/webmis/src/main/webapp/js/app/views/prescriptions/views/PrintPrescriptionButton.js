define(function (require) {
    require('views/print');
    var TreeButton = require('views/ui/TreeButton');


    var PrintButton = TreeButton.extend({
        initialize: function () {
            this.listenTo(this.collection, 'change reset', this.afterRender);
        },

        print: function (key) {
            var c = key.split(':');
            var type = c[0];
            var id = false;
            if (c[1]) {
                id = c[1];
            }

            if (!id && type === 'ln') {
                this.printAllPrescriptions();
            }

            if (id && type === 'ln') {
                this.printPatientPrescriptions(id);
            }

            if (id && type === 'm') {
                this.printPatientMonitoring(id);
            }
        },

        printPatientMonitoring: function (id) {
            var prescriptions = this.getClientPrescriptions(id);
            if (prescriptions && prescriptions.length) {
                console.log('prescription', prescriptions[0]);
                var data = {};
                var prescription = prescriptions[0];
                data.patientName = prescription.getPatientFio();
                data.patientBirthDate = prescription.getPatientBirthDate();

                new App.Views.Print({
                    data: data,
                    template: "patientMonitoring"
                });

                console.log('печать мониторинга пациента', id);

            }
        },

        convertPrescriptionForPrint: function (prescription, range) {
            var data = {};
            console.log('prescription', prescription);
            data.note = prescription.get('note');
            data.voa = prescription.get('voa') ? prescription.get('voa') + ' мл/ч' : '';
            data.drugs = prescription.get('drugs').map(function (drug) {
                return {
                    name: drug.get('name'),
                    dose: drug.get('dose') + ' ' + drug.get('unitName')
                };
            });

            var printRange = moment(range.min).twix(range.max - 1);
            var iter = printRange.iterate("hours"); //=> duration object with {days: 3}
            var hourRanges = [];
            var intervals = [];
            _(_.range(24)).each(function (i) {
                var start = iter.next().valueOf();
                var end = moment(start).add('minutes', 59).valueOf();
                hourRanges.push(moment(start).twix(end));
                intervals.push({
                    val: ''
                });

            });

            prescription.get('assigmentIntervals').each(function (ai) {
                ai.get('executionIntervals').each(function (ei) {
                    var start = ei.get('beginDateTime');
                    var end = ei.get('endDateTime');
                    var isRange = end ? true : false;

                    _(hourRanges).each(function (hourRange, index) {
                        var inRange = false;
                        if (isRange) {
                            inRange = hourRange.overlaps(moment(start).twix(end));
                        } else {
                            inRange = hourRange.contains(moment(start));
                        }
                        if (inRange) {
                            if (isRange) {
                                intervals[index].val = '➔';
                            } else {
                                intervals[index].val = '✔';
                            }
                        }
                    });
                });
            });

            data.intervals = intervals;

            return data;
        },

        getPatientPrescriptionPrintData: function (id, range) {
            var data = {
                groups: []
            };
            var prescriptions = this.getClientPrescriptions(id);
            var prescription = prescriptions[0];
            data.patientName = prescription.getPatientFio();
            data.patientBirthDate = prescription.getPatientBirthDate();
            data.patientAge = Core.Date.getAgeString(data.patientBirthDate);

            var groups = _(prescriptions).groupBy(function (model) {
                return model.get('moa');
            });

            _(groups).each(function (groupPrescriptions, groupName) {
                var convertedPrescriptions = [];
                _(groupPrescriptions).each(function (prescription) {
                    convertedPrescriptions.push(this.convertPrescriptionForPrint(prescription, range));
                }, this);

                if(groupName === 'null'){
                    groupName = 'Не определён';
                }

                data.groups.push({
                    name: groupName,
                    prescriptions: convertedPrescriptions
                });

            }, this);

            return data;
        },

        printPatientPrescriptions: function (id) {
            var range = {
                min: this.collection._filter.dateRangeMin * 1000,
                max: this.collection._filter.dateRangeMax * 1000
            };

            var data = this.getPatientPrescriptionPrintData(id, range);
            // console.log('data', data);
            new App.Views.Print({
                data: data,
                template: "patientPrescriptions"
            });

        },

        printAllPrescriptions: function () {
            new App.Views.Print({
                data: {},
                template: "allPrescriptions"
            });

            console.log('печать всех назначений');
        },

        getClientPrescriptions: function (id) {
            return this.collection.filter(function (prescription) {
                return prescription.get('client').id == id;
            });
        },

        getMenuItems: function () {
            var items = {
                // "ln": {
                //     name: "Лист назначений"
                // }
            };


            this.groupedByClient = this.collection.groupBy(function (prescription) {
                return prescription.get('client').id;
            });

            _.each(this.groupedByClient, function (clientPrescriptions, clientId) {
                var client = clientPrescriptions[0].get('client');
                var clientName = client.firstName + ' ' + client.lastName;
                var clientMenu = {};

                clientMenu['ln:' + clientId] = {
                    name: 'Лист назначений'
                };

                clientMenu['m:' + clientId] = {
                    name: 'Мониторинг'
                };

                items['client:' + clientId] = {
                    name: 'Лист назначений(' + clientName + ')',
                    items: clientMenu
                };

            });

            // console.log('items', items);

            return items;
        },

        getMenuCallback: function (key, options) {
            this.print(key);
        },

    });

    return PrintButton;
});
