define(function (require) {
    var Collection = require('./Collection');
    var Interval = require('../models/Interval');

    var Intervals = Collection.extend({
        model: Interval,
        initialize: function () {
            // console.log('init intervals collection', this);
        },
        addInterval: function () {
            var interval = new Interval();

            if (this.length) {
                console.log('add interval', this.last());
                var last = this.last();
                var endDateTime = last.get('endDateTime');
                if (endDateTime) {
                    interval.set('beginDateTime', moment(endDateTime).add(1, 'minute').valueOf());
                }else{
                    if(last.get('beginDateTime')){
                        interval.set('beginDateTime', moment(last.get('beginDateTime')).add(1, 'hour').valueOf());
                    } 
                }
            }

            this.add(interval);
        },
        validateCollection: function () {
            var collectionErrors = [];

            var modelsJSON = this.toJSON();

            var ranges = _.filter(modelsJSON, function(mj){
                return !!mj.beginDateTime && !!mj.endDateTime; 
            }); 
            if((ranges.length > 0) && (ranges.length != modelsJSON.length)){
                collectionErrors.push('В назначении не могут быть интервалы разных типов. ');
            }

            _.each(modelsJSON, function (mj) {
                console.log('interval',mj);

                var modelsJSON2 = _.without(modelsJSON, mj);
                _.each(modelsJSON2, function (mj2) {

                    if (mj.beginDateTime && !mj.endDateTime) {
                        if (mj2.beginDateTime && mj2.endDateTime) {
                            var range = moment(mj2.beginDateTime)
                                .twix(mj2.endDateTime);
                            if (range.contains(mj.beginDateTime)) {
                                collectionErrors.push('Начало интервала входит в другой интервал. ');
                            }
                        }

                        if (mj2.beginDateTime && !mj2.endDateTime) {
                            var diff = moment(mj.beginDateTime)
                                .startOf('minutes')
                                .diff(moment(mj2.beginDateTime)
                                    .startOf('minutes'));
                            if (diff === 0) {
                                collectionErrors.push('Совпадают начала интервалов. ');
                            }
                            // console.log('begin diff', diff)
                        }
                    }

                    if (mj.beginDateTime && mj.endDateTime) {
                        if (mj2.beginDateTime && mj2.endDateTime) {
                            var range1 = moment(mj.beginDateTime).startOf('minutes').twix(moment(mj.endDateTime).startOf('minutes'));
                            var range2 = moment(mj2.beginDateTime).startOf('minutes').twix(moment(mj2.endDateTime).startOf('minutes'));
                            if (range1.overlaps(range2)) {
                                // console.log('пересекаются') 
                                collectionErrors.push('Пересечение интервалов. ')
                            }


                        }
                    }

                });


            });

            collectionErrors = _.uniq(collectionErrors);

            this.each(function (model) {
                var errors = model.validateModel();
                if (errors && errors.length) {
                    collectionErrors = collectionErrors.concat(errors);
                }
            });

            if (collectionErrors.length) {
                return collectionErrors;
            }
        },

        execute: function (opts) {
            var ids = this.map(function (model) {
                return model.get('id');
            });

            var options = {
                data: JSON.stringify({
                    data: ids
                }),
                url: '/api/v1/prescriptions/executeIntervals',
                type: 'PUT',
                dataType: "jsonp",
                contentType: 'application/json'
            };

            return $.ajax(_.extend(options, opts));
        },

        update: function (opts) {
            var options = {
                data: JSON.stringify({
                    data: this.toJSON()
                }),
                url: '/api/v1/prescriptions/intervals',
                type: 'PUT',
                dataType: "jsonp",
                contentType: 'application/json'
            };

            return $.ajax(_.extend(options, opts));
        }
    });

    return Intervals;
});
