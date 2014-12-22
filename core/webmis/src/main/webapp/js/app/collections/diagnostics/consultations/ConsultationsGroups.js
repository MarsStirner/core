define(function(require) {

    var ConsultationsGroups = Collection.extend({
        url: function() {
            return DATA_PATH + "dir/actionTypes?filter[mnem]=CONS&filter[view]=tree";
        }
    });

    return ConsultationsGroups;

});
