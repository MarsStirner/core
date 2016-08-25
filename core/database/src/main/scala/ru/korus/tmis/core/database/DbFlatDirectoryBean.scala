package ru.korus.tmis.core.database

import java.util
import javax.ejb.Stateless
import javax.persistence.{EntityManager, PersistenceContext}

import ru.korus.tmis.auxiliary.FDSortingStruct
import ru.korus.tmis.core.auth.AuthData
import ru.korus.tmis.core.data.{FlatDirectoryRequestData, FlatDirectoryRequestDataListFilter, QueryDataStructure}
import ru.korus.tmis.core.entity.model.fd.{FDField, FDFieldValue, FDRecord, FlatDirectory}
import ru.korus.tmis.scala.util.I18nable

import scala.collection.JavaConversions._

@Stateless
class DbFlatDirectoryBean extends DbFlatDirectoryBeanLocal
with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  def getFlatDirectories(page: Int, limit: Int, sortingField: String, sortingMethod: String, filter: Object, userData: AuthData) = {

    val queryStr: QueryDataStructure = filter match {
      case x: FlatDirectoryRequestDataListFilter => x.toQueryStructure
      case _ => new QueryDataStructure()
    }

    val typed = em.createQuery(flatDirectoriesWithFilterQuery.format("fd", queryStr.query),
      classOf[FlatDirectory])
    if (queryStr.data.size() > 0) {
      queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))
    }
    val result = typed.getResultList


    result
  }

  def getFlatDirectoriesWithFilterRecords(page: Int, limit: Int, sorting: java.util.LinkedHashMap[java.lang.Integer, java.lang.Integer], filter: Object, request: FlatDirectoryRequestData, userData: AuthData) = {

    val queryStr: QueryDataStructure = filter match {
      case x: FlatDirectoryRequestDataListFilter => x.toQueryStructureForRecordsRequest
      case _ =>new QueryDataStructure()
    }

    val query = flatDirectoryRecordsWithFilterQuery.format("fd, fdr, fv", queryStr.query)
    val typed = em.createQuery(query, classOf[Array[AnyRef]])
    if (queryStr.data.size() > 0) {
      queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))
    }
    val res = typed.getResultList

    val result = res.foldLeft(new java.util.LinkedHashMap[FlatDirectory, java.util.LinkedHashMap[FDRecord, java.util.LinkedList[FDFieldValue]]])(
      (map, a) => {
        if (!map.containsKey(a(0).asInstanceOf[FlatDirectory])) {
          val internalMap = new java.util.LinkedHashMap[FDRecord, java.util.LinkedList[FDFieldValue]]
          val internalList = new java.util.LinkedList[FDFieldValue]
          internalList.add(a(2).asInstanceOf[FDFieldValue])
          internalMap.put(a(1).asInstanceOf[FDRecord], internalList)

          map.put(a(0).asInstanceOf[FlatDirectory], internalMap)
        }
        else {
          val sMap = map.get(a(0).asInstanceOf[FlatDirectory])
          if (!sMap.containsKey(a(1).asInstanceOf[FDRecord])) {
            val internalList = new java.util.LinkedList[FDFieldValue]
            internalList.add(a(2).asInstanceOf[FDFieldValue])
            sMap.put(a(1).asInstanceOf[FDRecord], internalList)
          }
          else {
            val sList = sMap.get(a(1).asInstanceOf[FDRecord])
            if (!sList.contains(a(2).asInstanceOf[FDFieldValue])) {
              sList.add(a(2).asInstanceOf[FDFieldValue])
            }
          }
        }

        map
      })

    //сортировка и разметка на страницы
    var mainCount: Long = 0
    val begin: Int = (page - 1) * limit
    val end: Int = (page - 1) * limit + limit
    val sortingResult = new java.util.LinkedHashMap[FlatDirectory, java.util.LinkedHashMap[FDRecord, java.util.LinkedList[FDFieldValue]]]

    result.foreach(f => {
      mainCount += f._2.size()
      if (sorting != null && sorting.size() > 0) {
        val singleResult = FDSortingStruct.getFDSortingStructArray(f._2, sorting)
        sortingResult.put(f._1, FDSortingStruct.toHashMap(singleResult, begin, end))
      }
      else {
        val map = new java.util.LinkedHashMap[FDRecord, java.util.LinkedList[FDFieldValue]]
        val count = if (f._2.size() < end) {
          f._2.size()
        } else {
          end
        }
        val it: java.util.Iterator[FDRecord] = f._2.keySet().iterator()
        var pos = 0

        while (it.hasNext && pos < count) {
          val key = it.next()
          if (pos >= begin) {
            map.put(key, f._2.get(key))
          }
          pos = pos + 1
        }
        sortingResult.put(f._1, map)
      }
    })

    request.setRecordsCount(mainCount)
    sortingResult
  }

  val flatDirectoriesWithFilterQuery =
    """
  SELECT %s
  FROM FlatDirectory fd
  %s
  ORDER BY fd.id
    """

  val flatDirectoryRecordsWithFilterQuery =
    """
  SELECT %s
  FROM FDRecord fdr
  JOIN fdr.flatDirectory fd
  JOIN fdr.fieldValues fv
  %s
  ORDER BY fd.id ASC, fdr.order ASC
    """

  override def getByCode(code: String): FlatDirectory = {
    val res = em.createNamedQuery("FlatDirectory.byCode", classOf[FlatDirectory]).setParameter("code", code).getResultList
    if (res.nonEmpty) {
      res.head
    } else {
      null
    }
  }

  def groupListOfArray_FDRecord_FDFieldValue(records: util.List[_], fieldCount: Int): util.Map[FDRecord, util.List[FDFieldValue]] = {
    val result = new util.LinkedHashMap[FDRecord, util.List[FDFieldValue]](records.size / fieldCount + 1)
    var lastRecord: FDRecord = null
    var lastValuesList: util.List[FDFieldValue] = null
    for (record <- records) {
      val itemsArray = record.asInstanceOf[Array[Object]]
      val fdRecord = itemsArray(0).asInstanceOf[FDRecord]
      if (lastRecord != fdRecord) {
        if (lastRecord != null) {
          result.put(lastRecord, lastValuesList)
        }
        lastRecord = fdRecord
        lastValuesList = new util.ArrayList[FDFieldValue](fieldCount)
      }
      lastValuesList.add(itemsArray(1).asInstanceOf[FDFieldValue])
    }
    if (lastRecord != null) {
      result.put(lastRecord, lastValuesList)
    }
    result
  }

  /**
   * Получение записей FD (запрошенный кусок (limit+offset)) со значениями заданных полей
   * @param flatDirectory   спарвочник, для которого нужно вытащить записи
   * @param fields заданные поля (в результате будт только значения с этими полями)
   * @return Порция запрошенных записей справочника
   */
  override def getRecordsWithFilter(
                                     flatDirectory: FlatDirectory,
                                     fields: util.List[FDField]
                                     ): util.Map[FDRecord, util.List[FDFieldValue]] = {

    val queryFDRecordWithFields =
      """
        SELECT rec, fv
        FROM FDRecord rec
        INNER JOIN rec.fieldValues fv
        INNER JOIN fv.fdField fd
        WHERE rec.deleted = 0
        AND rec.flatDirectory.id = :flatDirectory
        AND fd.id IN :fieldList
        ORDER BY rec.order ASC, rec.id ASC, fd.order ASC
      """
    val records = em.createQuery(queryFDRecordWithFields)
      .setParameter("fieldList", new util.ArrayList(fields.map(_.getId).toList))
      .setParameter("flatDirectory", flatDirectory.getId)
      .getResultList
    groupListOfArray_FDRecord_FDFieldValue(records, fields.size())
  }
  //TODO
  override def getRecordsWithFilter(
                                     flatDirectory: FlatDirectory,
                                     fields: util.List[FDField],
                                     filter: util.Map[FDField, util.List[String]]
                                     ): util.Map[FDRecord, util.List[FDFieldValue]] = {
    val queryFDRecordWithFields =
      """
        SELECT rec, fv
        FROM FDRecord rec
        INNER JOIN rec.fieldValues fv
        INNER JOIN fv.fdField fd
        WHERE rec.deleted = 0
        AND rec.flatDirectory.id = :flatDirectory
        AND fd.id IN :fieldList
        AND EXISTS (%s)
        ORDER BY rec.order ASC, rec.id ASC, fd.order ASC
      """
    val subquery = new StringBuilder("\nSELECT _f.id FROM FDFieldValue _f WHERE _f.record.id = rec.id AND (\n")
    for((filterField, filterValues) <- filter){
      //TODO Possible SQL Injection point
      subquery.append("( _f.fdField.id = ").append(filterField.getId)
      subquery.append(" AND _f.value IN ").append(filterValues.mkString("(\'","\',\'" ,"\')")).append(" )\nOR\n")
    }
    subquery.setLength(subquery.length - 4)
    subquery.append("\n)")
    val records = em.createQuery(queryFDRecordWithFields.format(subquery.toString()))
      .setParameter("fieldList", new util.ArrayList(fields.map(_.getId).toList))
      .setParameter("flatDirectory", flatDirectory.getId)
      .getResultList
    groupListOfArray_FDRecord_FDFieldValue(records, fields.size())
  }

  override def getRecordsWithFilter(flatDirectory: FlatDirectory, fields: util.List[FDField], filter: String): util.Map[FDRecord, util.List[FDFieldValue]] = {
    val queryFDRecordWithFields =
      """
        SELECT rec, fv
        FROM FDRecord rec
        INNER JOIN rec.fieldValues fv
        INNER JOIN fv.fdField fd
        WHERE rec.deleted = 0
        AND rec.flatDirectory.id = :flatDirectory
        AND fd.id IN :fieldList
        AND EXISTS (SELECT _f.id FROM FDFieldValue _f WHERE _f.value = :value AND _f.record.id = rec.id)
        ORDER BY rec.order ASC, rec.id ASC, fd.order ASC
      """
    val records = em.createQuery(queryFDRecordWithFields)
      .setParameter("fieldList", new util.ArrayList(fields.map(_.getId).toList))
      .setParameter("flatDirectory", flatDirectory.getId)
      .setParameter("value", filter)
      .getResultList
    groupListOfArray_FDRecord_FDFieldValue(records, fields.size())
  }
}
