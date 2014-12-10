define(function (require) {
    var Model = require('./Model');
    require('moment');
    require('twix');

    var states = {
        runs: {
            title: 'Выполняется',
            color: '#f57177'
        },
        assigned: {
            title: 'Назначено',
            color: '#07ace3'
        },
        notExecuted: {
            title: 'Не исполнено',
            color: '#b2a7c9'
        },
        executed: {
            title: 'Исполнено',
            color: '#94cf51'
        },
        canceled: {
            title: 'Отменено',
            color: '#999999'
        },
        stop: {
            title: 'Стоп',
            color: '#FFEA4F'
        }
    };

    var Interval = Model.extend({
        defaults: {
            beginDateTime: moment((new Date()).getTime()).add(1,'hour').startOf('hour').valueOf(),
            endDateTime: null,
            status: 0
        },

        initialize: function () {
            this.setState();
            this.on('change:status change:beginDateTime change:endDateTime', this.setState, this);
            this.set('cid', this.cid);
        },
        setState: function () {
            var state = this.getState();
            if (state) {
                this.set('stateName', states[state].title);
                this.set('backgroundColor', states[state].color);
            }
        },

        delete: function () {
            this.collection.remove(this);
        },

        getState: function () {
            var status = this.get('status');
            var state = '';
            if (status === 0 && this.timeIn()) {
                state = 'runs';
            }
            if (status === 0 && this.timeBefore()) {
                state = 'assigned';
            }
            if (status === 0 && this.timeAfter()) {
                state = 'notExecuted';
            }
            if (status === 1) {
                state = 'executed';
            }
            if (status === 2) {
                state = 'canceled';
            }
            if (status === 3) {
                state = 'stop';
            }

            return state;
        },

        getCurrentTime: function () {
            return moment().valueOf();
        },

        roundToMinutes: function (timestamp) {
            return timestamp ? moment(timestamp).startOf('minute').valueOf() : timestamp;
        },

        timeBefore: function () { //предикат: время начала интервала больше текущего
            var begin = this.roundToMinutes(this.get('beginDateTime'));
            var current = this.roundToMinutes(this.getCurrentTime());

            return begin > current;
        },

        isNotExecuted: function () {
            return this.getState() === 'notExecuted';
        },

        getIntervalCoordinates: function (rangeStart, rangeEnd) {
            var defaultIntervalLength = 45 * 60 * 1000;
            var intervalStart = this.get('beginDateTime');
            var intervalEnd = this.get('endDateTime');
            var coordinates = {};

            if ((intervalStart < rangeStart) && intervalEnd && (intervalEnd > rangeStart)) {
                intervalStart = rangeStart;
            }

            if ((intervalStart >= rangeStart) && (intervalStart <= rangeEnd)) {
                coordinates.x1 = (intervalStart - rangeStart) / (rangeEnd - rangeStart) * 100;
            }

            if (!intervalEnd) { //для интервалов без окончания задаём длинну
                intervalEnd = intervalStart + defaultIntervalLength;
            }

            if (intervalEnd > rangeEnd) { //обрезаем если окончание интервала выходит за пределы диапозона
                intervalEnd = rangeEnd;
            }

            if ((intervalEnd > rangeStart) && (intervalEnd <= rangeEnd) && _.isNumber(coordinates.x1)) {
                coordinates.x2 = (intervalEnd - rangeStart) / (rangeEnd - rangeStart) * 100;
                coordinates.w = coordinates.x2 - coordinates.x1;
            }
            //console.log('coordinates', coordinates);
            return coordinates;
        },

        overlapRange: function (start, end) { //пересекается ли интервал с интервалом...
            var range = moment(start).twix(end);
            var overlap = false;

            if (this.get('endDateTime')) {
                var intervalRange = moment(this.get('beginDateTime')).twix(this.get('endDateTime'));
                overlap = intervalRange.overlaps(range);
            } else {
                overlap = range.contains(this.get('beginDateTime'));
            }

            return overlap;
        },

        timeIn: function () { //предикат: время начала интервала меньше текущего, время окончания интервала больше текущего
            var bool;
            var begin = this.roundToMinutes(this.get('beginDateTime'));
            var current = this.roundToMinutes(this.getCurrentTime());
            var end = this.roundToMinutes(this.get('endDateTime'));

            if (end) {
                bool = (begin <= current <= end);
            } else { //если у интервала есть только начало, то считаем что интервал 'in', когда текущая минута равна минуте начала интервала
                bool = (begin === current);
            }

            return bool;
        },

        timeAfter: function () { //предикат: время окончания интервала меньше текущего
            var bool;
            var begin = this.roundToMinutes(this.get('beginDateTime'));
            var current = this.roundToMinutes(this.getCurrentTime());
            var end = this.roundToMinutes(this.get('endDateTime'));

            if (end) {
                bool = (end < current);
            } else {
                bool = (begin < current);
            }

            return bool;
        },

        intervalToString: function () {
            var string = '';
            var begin = this.get('beginDateTime') ? moment(this.get('beginDateTime')) : null;
            var end = this.get('endDateTime') ? moment(this.get('endDateTime')) : null;

            string = begin.format('DD.MM.YYYY HH:mm');

            if (end) {
                // console.log(begin.format('DD.MM.YYYY HH:mm'),end.format('DD.MM.YYYY HH:mm'),begin.startOf('day').diff(end.startOf('day'), 'day'))
                var end2 = moment(this.get('endDateTime'));
                var endInSameDay = (begin.startOf('day').diff(end2.startOf('day'), 'day') === 0);

                if (endInSameDay) {
                    string = string + ' - ' + end.format('HH:mm');
                } else {
                    string = string + ' - ' + end.format('DD.MM.YYYY HH:mm');
                }
            }

            return string;
        },

        toJSON: function () {
            var attributes = _.clone(this.attributes);

            _.each(attributes, function (value, key) {
                if (value && _.isFunction(value.toJSON)) {
                    attributes[key] = value.toJSON();
                }
            });

            attributes.intervalString = this.intervalToString();
            attributes.canBeEdited = this.canBeEdited();
            attributes.canBeCanceled = this.canBeCanceled();
            attributes.canBeExecuted = this.canBeExecuted();
            attributes.canBeCanceledExecution = this.canBeCanceledExecution();
            attributes.canBeAddedStop = this.canBeAddedStop();

            return attributes;
        },

        url: function () {
            return '/api/v1/prescriptions/intervals';
        },

        sync: function (method, model, options) {
            if (method === 'update') {
                options.dataType = "jsonp";
                options.url = model.url();
                options.contentType = 'application/json';
                options.data = JSON.stringify({
                    data: [model.toJSON()]
                });
                Backbone.sync.call(this, method, model, options);
            } else {
                Model.prototype.call(this, method, model, options);
            }
        },

        execute: function (opts) {
            var options = {
                data: JSON.stringify({
                    data: [this.get('id')]
                }),
                url: '/api/v1/prescriptions/executeIntervals',
                type: 'PUT',
                dataType: "jsonp",
                contentType: 'application/json'
            };

            return $.ajax(_.extend(options, opts));
        },

        cancelExecution: function (opts) {
            var options = {
                data: JSON.stringify({
                    data: [this.get('id')]
                }),
                url: '/api/v1/prescriptions/cancelIntervalsExecution',
                type: 'PUT',
                dataType: "jsonp",
                contentType: 'application/json'
            };

            return $.ajax(_.extend(options, opts));
        },

        cancel: function (opts) {
            var options = {
                data: JSON.stringify({
                    data: [this.get('id')]
                }),
                url: '/api/v1/prescriptions/cancelIntervals',
                type: 'PUT',
                dataType: "jsonp",
                contentType: 'application/json'
            };

            return $.ajax(_.extend(options, opts));
        },

        validateModel: function () {
            var errors = [];
            if (!this.get('beginDateTime')) {
                errors.push('Не указано начало интервала. ');
            }

            if (this.get('endDateTime') && this.get('beginDateTime')) {
                var diff = moment(this.get('endDateTime')).startOf('minute').diff(moment(this.get('beginDateTime')).startOf('minute'));
                if (diff < 0) {
                    errors.push('Время окончания интервала меньше времени начала. ');
                }
                if (diff === 0) {
                    errors.push('Время окончания интервала равно времени начала. ');
                }
            }

            // if (errors.length) {
                return errors;
            // }
        },

        canBeCanceled: function () {
            var state = this.getState();
            var status = this.get('status');

            return (state === 'assigned' && status !== 3);
        },

        canBeExecuted: function () {
            var state = this.getState();
            var status = this.get('status');

            return ((state === 'runs') || (state === 'notExecuted')) && (status !== 3);
        },

        canBeCanceledExecution: function () {
            var state = this.getState();
            var status = this.get('status');

            return state === 'executed' && status !== 3;
        },

        canBeEdited: function () {
            var state = this.getState();
            var status = this.get('status');

            return state !== 'canceled';// && status !== 3;
        },

        canBeAddedStop: function () {
            return (this.get('endDateTime') && (this.get('status') !== 3));
        }

    });

    return Interval;
});
