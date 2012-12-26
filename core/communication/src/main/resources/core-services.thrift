namespace java ru.korus.tmis.communication.thriftgen
//Namespace=package name for java

//Type definitions for structures

struct OrgStructure{
1:required i32 id,
2:optional i32 parent_id=0,
3:required string code,
4:requred string name="",
5:optional string adress="",
6:optional string sexFilter="",
7:optional string ageFilter=""
}

//Exceptions
exception NotFoundException {
 1: string error_msg
}
exception SQLException {
  1: i32 error_code,
  2: string error_msg
}


//Service to be generated from here
service Communications{

//Methods to be generated in this service
list<OrgStructure> getOrgStructures(1:i32 id,  2:bool recursive)
throws (1:NotFoundException exc, 2:SQLException excsql)


}