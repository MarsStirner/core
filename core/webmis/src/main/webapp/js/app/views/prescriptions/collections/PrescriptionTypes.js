
define(function(require){
    'use strict';
    
    var Collection = require('./Collection');
    return  Collection.extend({
        url: function(){
            return '/api/v1/prescriptions/types'; 
        } 
    });
});
