define(function(){
    var permissions = [];
    var userRole = Core.Data.currentRole(); 

    switch(userRole){
        case 'nurse-department':
        case 'nurse-receptionist':
            permissions = ['see_patient_card', 'see_patient_appeals'];
        break;
        default:
            permissions = ['see_patient_card', 'see_patient_appeals', 'see_patient_summary'];
        break;
    }



    return permissions;
});
