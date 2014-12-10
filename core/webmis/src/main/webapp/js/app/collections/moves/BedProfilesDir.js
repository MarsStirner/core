define(function(require){

    var BedProfileDir = Collection.extend({
        url: function(){
            return DATA_PATH+ 'hospitalbed/avaliable_profiles';
        },
        parse: function(raw){
            return raw.profiles; 
        }
    });

    return BedProfileDir;
});