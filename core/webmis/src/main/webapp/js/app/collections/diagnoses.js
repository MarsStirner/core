define( ["models/diagnosis"], function (){
	App.Collections.Diagnoses = Collection.extend({
		model: App.Models.Diagnosis,
		getAssignments: function ()
		{
			return this.filter( function ( model )
			{
				return ( model.get( "diagnosisKind" ) == "diagReceivedMkb" )
			} )
		},
		getAftereffects: function ()
		{
			return this.filter( function ( model )
			{
				return ( model.get( "diagnosisKind" ) == "aftereffectMkb" )
			} )
		},
		getAttendants: function ()
		{
			return this.filter( function ( model )
			{
				return ( model.get( "diagnosisKind" ) == "attendantMkb" )
			} )
		}
	})
} );