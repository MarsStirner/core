define(function(require){

    /**
     * Облегчённая коллекция персонала ЛПУ (без bb.relational)
     * @type {*}
     */
    var Persons = Collection.extend({
        model: Backbone.Model.extend({}),

        url: function () {
            return DATA_PATH + "dir/persons";
        }
    });

    return Persons;


});
