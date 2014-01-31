package ru.korus.tmis.core.database

import javax.ejb.Stateless
import ru.korus.tmis.core.logging.LoggingInterceptor
import javax.interceptor.Interceptors
import grizzled.slf4j.Logging
import javax.persistence.{EntityManager, PersistenceContext}
import ru.korus.tmis.core.auth.AuthData
import scala.collection.JavaConversions._
import ru.korus.tmis.core.entity.model.fd.{FDFieldValue, FDRecord, FlatDirectory}
import ru.korus.tmis.auxiliary.{AuxiliaryFunctions, FDSortingStruct}
import ru.korus.tmis.core.data.{FlatDirectoryRequestData, FlatDirectoryRequestDataListFilter, QueryDataStructure}
import ru.korus.tmis.scala.util.I18nable

@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class DbFlatDirectoryBean extends DbFlatDirectoryBeanLocal
with Logging
with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  def getFlatDirectories(page: Int, limit: Int, sortingField: String, sortingMethod: String, filter: Object, userData: AuthData) = {

    val queryStr: QueryDataStructure = if (filter.isInstanceOf[FlatDirectoryRequestDataListFilter]) {
      filter.asInstanceOf[FlatDirectoryRequestDataListFilter].toQueryStructure()
    }
    else {
      new QueryDataStructure()
    }

    val typed = em.createQuery(flatDirectoriesWithFilterQuery.format("fd", queryStr.query),
      classOf[FlatDirectory])
    if (queryStr.data.size() > 0) {
      queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))
    }
    val result = typed.getResultList

    result.foreach(fd => em.detach(fd))
    result
  }

  def getFlatDirectoriesWithFilterRecords(page: Int, limit: Int, sorting: java.util.LinkedHashMap[java.lang.Integer, java.lang.Integer], filter: Object, request: FlatDirectoryRequestData, userData: AuthData) = {

    val queryStr: QueryDataStructure = if (filter.isInstanceOf[FlatDirectoryRequestDataListFilter]) {
      filter.asInstanceOf[FlatDirectoryRequestDataListFilter].toQueryStructureForRecordsRequest()
    }
    else {
      new QueryDataStructure()
    }

    val query = flatDirectoryRecordsWithFilterQuery.format("fd, fdr, fv", queryStr.query)
    val typed = em.createQuery(query, classOf[Array[AnyRef]])
    if (queryStr.data.size() > 0) {
      queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))
    }
    val res = typed.getResultList

    var result = res.foldLeft(new java.util.LinkedHashMap[FlatDirectory, java.util.LinkedHashMap[FDRecord, java.util.LinkedList[FDFieldValue]]])(
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
        if (a(0).isInstanceOf[FlatDirectory]) em.detach(a(0).asInstanceOf[FlatDirectory])
        if (a(1).isInstanceOf[FDRecord]) em.detach(a(1).asInstanceOf[FDRecord])
        if (a(2).isInstanceOf[FDFieldValue]) em.detach(a(2).asInstanceOf[FDFieldValue])
        map
      })

    //сортировка и разметка на страницы
    var mainCount: Long = 0
    val begin: Int = (page - 1) * limit
    val end: Int = (page - 1) * limit + limit
    var sortingResult = new java.util.LinkedHashMap[FlatDirectory, java.util.LinkedHashMap[FDRecord, java.util.LinkedList[FDFieldValue]]]

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
        var it: java.util.Iterator[FDRecord] = f._2.keySet().iterator()
        var pos = 0

        while (it.hasNext() && pos < count) {
          val key = it.next();
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

  val flatDirectoriesWithFilterQuery = """
  SELECT %s
  FROM FlatDirectory fd
  %s
  ORDER BY fd.id
                                       """

  val flatDirectoryRecordsWithFilterQuery = """

  SELECT %s
  FROM FDRecord fdr
  JOIN fdr.flatDirectory fd
  JOIN fdr.fieldValues fv
  %s
  ORDER BY fd.id ASC, fdr.order ASC
                                            """
}
