package ru.korus.tmis.core.event


import java.io.Serializable
import java.util.{List, Map, Set}
import ru.korus.tmis.core.entity.model.{AssignmentHour, APValue, ActionProperty, Action}

trait Notification extends Serializable {

}

class CreateActionNotification(var a: Action,
                               var values: Map[ActionProperty, List[APValue]])
  extends Notification {

}

class ModifyActionNotification(var oldAction: Action,
                               var oldValues: Map[ActionProperty, List[APValue]],
                               var newAction: Action,
                               var newValues: Map[ActionProperty, List[APValue]])
  extends Notification {

}

class CancelActionNotification(var a: Action,
                               var values: Map[ActionProperty, List[APValue]])
  extends Notification {

}

class PrescriptionChangedNotification(var a: Action,
                                      var values: Map[ActionProperty, List[APValue]],
                                      var created: Set[AssignmentHour],
                                      var done: Set[AssignmentHour],
                                      var notDone: Set[AssignmentHour],
                                      var deleted: Set[AssignmentHour])
  extends Notification {

}
