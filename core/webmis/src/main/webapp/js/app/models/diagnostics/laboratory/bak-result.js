define(["models/model-base"], function (ModelBase) {
    return ModelBase.extend({
//    return Backbone.Model.extend({
        url: function () {
            return DATA_PATH + "diagnostics/laboratory/bak/" + this.diagnosticId;
            //return "/bak-results.json";
        },
        getTable: function () {
            var organisms = this.get('organismResults');
            var antibiotics = [];

            if(_.isEmpty(organisms)){
                return false;
            }

            _.each(organisms, function (organism) {
                var sensValues = organism.sensValues;

                if(!_.isEmpty(sensValues)){

                    _.each(sensValues, function (sensValue) {
                        var antibiotic = {
                            name: sensValue.antibiotic.name,
                            code: sensValue.antibiotic.code,
                            organisms: []
                        };
                        antibiotics.push(antibiotic);
                    });
                
                }

            });

            antibiotics = _.uniq(antibiotics, false, function(item){
                return item.name; 
            });

            _.each(antibiotics, function(antibiotic){
                _.each(organisms, function (organism) {

                    var sensValues = organism.sensValues;
                    var o = {astivity: '', id: organism.organism.id, name: organism.organism.name};

                    _.each(sensValues, function (sensValue) {
                        if(sensValue.antibiotic.name === antibiotic.name){
                            o.activity = sensValue.activity;
                        }
                    });
                    antibiotic.organisms.push(o);
                });
            });

            // console.log('antibiotics', antibiotics);

            return {rows: antibiotics};
        }
    });
});
