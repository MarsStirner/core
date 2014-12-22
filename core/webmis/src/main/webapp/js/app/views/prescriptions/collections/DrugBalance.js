define(function (require) {
    var Collection = require('./Collection');
    var Model = require('../models/Model');


    var DrugBalanceModel = Model.extend({
        initialize: function () {
            console.log('dbm', this.toJSON(), this.isExpired(), this.isExpirationDateComesToEnd(), this.getLifeTimePercentage());
        },

        getLifeTimePercentage: function () {
            var bestBefore = moment(this.get('bestBefore'), 'YYYY-MM-DD');
            var drugLifeTime = this.get('drugLifeTime');
            var currentDate = moment();
            var diff = bestBefore.diff(currentDate, 'month');
            var lifeTimePercentage = (diff / drugLifeTime) * 100;

            return lifeTimePercentage;

        },

        isExpired: function () {
            var lifeTimePercentage = this.getLifeTimePercentage();
            var bool = false;

            if (lifeTimePercentage <= 0) {
                bool = true;
            }

            return bool;
        },

        isExpirationDateComesToEnd: function () {
            var lifeTimePercentage = this.getLifeTimePercentage();
            var bool = false;

            if (lifeTimePercentage < 30) {
                bool = true;
            }

            return bool;
        },

        toJSON: function(){
            var attributes = _.clone(this.attributes);
            attributes.lifeTimePercentage = this.getLifeTimePercentage();
            attributes.isExpired = this.isExpired();
            attributes.isExpirationDateComesToEnd = this.isExpirationDateComesToEnd();

            return attributes;
        }
    });

    var DrugBalance = Collection.extend({
        model: DrugBalanceModel,
        initialize: function (models, options) {

        },

        url: function () {
            return DATA_PATH + "rls/" + this.nomenId;
        },

        parse: function(response) {
            return response;
        }
    });

    return DrugBalance;

});
