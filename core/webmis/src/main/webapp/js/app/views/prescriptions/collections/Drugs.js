define(function (require) {
    var Collection = require('./Collection');
    var Drug = require('../models/Drug');

    var Drugs = Collection.extend({
        model: Drug,
        validateCollection: function(){
            var collectionErrors = [];

            this.each(function(model){
               var errors = model.validateModel(); 
               if(errors && errors.length){
                    collectionErrors = collectionErrors.concat(errors); 
               }
            });

            if(collectionErrors.length){
                return collectionErrors; 
            }
        },
        initialize: function(){
            // console.log('init drugs collection', this);
        }
    });

    return Drugs;
});
