define(function(require) {


    var DoctorFreeModel = Model.extend({
        initialize: function() {
            this.beginDate = this.collection.requestData.filter.beginDate;
            var schedule = new ScheduleCollection(this.get('schedule'));
            this.set('schedule', schedule);
        }
    });

    var ScheduleModel = Model.extend({

    });


    var ScheduleCollection = Collection.extend({
        model: ScheduleModel

    });

    var DoctorsFreeCollection = Collection.extend({
        model: DoctorFreeModel,
        url: function() {
            return DATA_PATH + "dir/persons/free/"
        },
        convertTime: function(time) {
            var now = moment().startOf('day').valueOf();
            return moment(now + time).format('HH:mm:ss')
        },
        parse: function(raw) {
            checkForWarnings(raw.requestData, "requestData was not found in the JSON");
            this.requestData = raw.requestData || {};
            this.requestData.filter = this.requestData.filter || {};

            if (raw.requestData && this.requestData.coreVersion) {
                CORE_VERSION = raw.requestData.coreVersion;
                VersionInfo.show();
            }


            var beginDate = moment(this.requestData.filter.beginDate).startOf('day');
            var startOfDay = moment().startOf('day');
            //console.log('beginDate',beginDate);

            _.each(raw.data, function(person) {
                var nowTime = 0;

                if (beginDate.valueOf() === startOfDay.valueOf()) {
                    nowTime = moment().diff(startOfDay);
                }

                _.each(person.schedule, function(item) {
                    item.disabled = (nowTime > item.time)
                    item.parsedTime = this.convertTime(item.time);
                }, this);

            }, this);



            return raw.data
        },
    });

    return DoctorsFreeCollection;
});
