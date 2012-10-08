package ru.korus.tmis.core.data

//Структура для хранения запросов фильтрации
class QueryDataStructure {

  var data: java.util.List[QueryDataParameter] = new java.util.ArrayList[QueryDataParameter]

  var query: String = ""

  def add(name: String,
          value: AnyRef) {
    this.data.add(new QueryDataParameter(name, value))
  }
}

class QueryDataParameter {

  var name: String = _

  var value: AnyRef = _

  def this(name: String,
           value: AnyRef) {
    this()
    this.name = name
    this.value = value
  }
}
