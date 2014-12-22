define(function(){
	var SocialStatus = Model.extend ({
		defaults: {
			status: ""
		}
	});

	var Rank = Model.extend ({
		defaults: {
			rank: ""
		}
	});

	var ForceBranch = Model.extend ({
		defaults: {
			branch: ""
		}
	});

	var RelationType = Model.extend ({

	});

	var Work = Model.extend({
		defaults: {
			"preschoolNumber": "",
			"schoolNumber": "",
			"classNumber": "",
			"militaryUnit": "",
			"workingPlace": "",
			"position": "",
			"rank": {},
			"forceBranch": {},
			"relationType": {}
		},

		relations: [
			{
				type: Backbone.HasOne,
				key: "rank",
				relatedModel: Rank
			},
			{
				type: Backbone.HasOne,
				key: "forceBranch",
				relatedModel: ForceBranch
			},
			{
				type: Backbone.HasOne,
				key: "relationType",
				relatedModel: RelationType
			}
		]
	});

	var Works = Collection.extend({
		model: Work
	});


	App.Models.Occupation = Model.extend ({
		defaults: {
			comment: "",
			//preschoolNumber: "",
			//schoolNumber: "",
			//classNumber: "",
			//militaryUnit: "",
			//workingPlace: "",
			//position: "",

			//rank: {},
			socialStatus: {},
			works: []
			//forceBranch: {},
			//relationType: {}
		},

		relations: [
			{
				type: Backbone.HasOne,
				key: "socialStatus",
				relatedModel: SocialStatus
			},
			{
				type: Backbone.HasMany,
				key: "works",
				relatedModel: Work,
				collectionType: Works
			}
			/*{
				type: Backbone.HasOne,
				key: "rank",
				relatedModel: Rank
			},
			{
				type: Backbone.HasOne,
				key: "forceBranch",
				relatedModel: ForceBranch
			},
			{
				type: Backbone.HasOne,
				key: "relationType",
				relatedModel: RelationType
			}*/
		]
	});
});