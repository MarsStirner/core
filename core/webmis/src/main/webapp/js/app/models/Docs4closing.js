define(function(require) {

    var Docs = Model.extend({
        initialize: function(){
            console.log('initialize d4c');
        },

        url: function() {
            return '/api/v1/appeals/' + this.appealId + '/docs4closing'
        }

    });


    return Docs;

});
