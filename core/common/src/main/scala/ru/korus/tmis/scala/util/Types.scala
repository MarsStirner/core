package ru.korus.tmis.scala.util

// aliases for java types
object Types {
  type JBoolean = java.lang.Boolean
  type JInteger = java.lang.Integer
  type JDouble = java.lang.Double
  type JFloat = java.lang.Float

  type JList[T] = java.util.List[T]
  type JSet[T] = java.util.Set[T]
  type JLinkedList[T] = java.util.LinkedList[T]
  type JLinked[T] = JLinkedList[T]
  type JString = String


}
