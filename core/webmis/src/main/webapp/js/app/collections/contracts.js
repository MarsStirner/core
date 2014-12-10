define(function (require) {
    var Contract = Model.extend({
        defaults: {
            number: "",
            begDate: "",
            finance: {}
        }
    });

    return Collection.extend({
        model: Contract,
        url: function () {
            return DATA_PATH + "dir/contracts?eventTypeId=" + this.eventTypeId;
        },
        parse: function(data){
            return data; 
        }
    });

});
