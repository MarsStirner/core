define(function(require) {

    var Consultation = Model.extend({
        idAttribute: 'id'

    });

    return Collection.extend({
        model: Consultation,

        url: function() {
            return DATA_PATH + "appeals/" + this.appealId + "/diagnostics/consultations/";
        }
    });
});
