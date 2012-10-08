package ru.korus.tmis.drugstore.sync

import ru.korus.tmis.core.database.{DbOrganizationBeanLocal, DbOrgStructureBeanLocal}
import ru.korus.tmis.core.entity.model.{Organisation, OrgStructure}
import ru.korus.tmis.core.logging.db.LoggingInterceptor
import ru.korus.tmis.drugstore.data.{YGetDepartmentListRequest, YGetOrganizationListRequest}
import ru.korus.tmis.drugstore.util.Xmlable.asScalaXml

import java.lang.String
import java.util.{HashMap, Map}
import javax.annotation.PostConstruct
import javax.ejb._
import javax.interceptor.Interceptors

import grizzled.slf4j.Logging
import scala.collection.JavaConversions._
import ru.korus.tmis.drugstore.util.SoapAcknowlegement
import ru.korus.tmis.util.StringUtils


@Startup
@Interceptors(Array(classOf[LoggingInterceptor]))
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
@Singleton
class RefResolverBean
  extends RefResolverBeanLocal
  with SoapAcknowlegement
  with Logging {

  @EJB
  var dbOrganization: DbOrganizationBeanLocal = _

  @EJB
  var dbOrgStructure: DbOrgStructureBeanLocal = _

  //

  var organizationRefMap: Map[Organisation, String] =
    new HashMap[Organisation, String]()

  var orgStructureRefMap: Map[OrgStructure, String] =
    new HashMap[OrgStructure, String]()

  @Lock(LockType.WRITE)
  @PostConstruct
  def sync(): Unit = {
    try {
      // Clear state
      organizationRefMap.clear()
      orgStructureRefMap.clear()

      val orgListResponse = sendSoapMessage(new YGetOrganizationListRequest().toXmlDom,
        CMD.GetOrgList_RequestRootElement,
        CMD.GetOrgList_SoapAction,
        CMD.GetOrgList_SoapOperation,
        CMD.DefaultXsiType)

      if (orgListResponse == null) {
        error("Cannot get organization list from 1C drugstore server")
        return
      }

      val orgRefs = (asScalaXml(orgListResponse.getSOAPBody) \\ "Organization")
      val orgs = dbOrganization.getAllOrganizations

      def refNameCompare(a: String, b: String) = {
        (StringUtils.normalize(a) contains StringUtils.normalize(b)) ||
          (StringUtils.normalize(b) contains StringUtils.normalize(a))
      }

      val newOrganizationRefMap = (new HashMap[Organisation, String]() /: orgs)((m, o) => {
        orgRefs.find(ref => {
          val shortNameMatched = o.getShortName match {
            case null | "" => false
            case name => refNameCompare((ref \ "Description").text, name) ||
              refNameCompare((ref \ "NameShort").text, name)
          }
          val titleMatched = o.getTitle match {
            case null | "" => false
            case name => refNameCompare((ref \ "Description").text, name) ||
              refNameCompare((ref \ "NameShort").text, name)
          }
          shortNameMatched || titleMatched
        }) match {
          case None => {}
          case Some(ref) => m.put(o, ref \ "Ref" text)
        }
        m
      })
      organizationRefMap.putAll(newOrganizationRefMap)

      orgRefs
        .map(_ \ "Ref" text)
        .foreach(orgRef => {
        val depListResponse = sendSoapMessage(new YGetDepartmentListRequest(orgRef).toXmlDom,
          CMD.GetDepList_RequestRootElement,
          CMD.GetDepList_SoapAction,
          CMD.GetDepList_SoapOperation,
          CMD.DefaultXsiType)

        if (depListResponse == null) {
          error("Cannot get department list from 1C drugstore server")
          return
        }

        val depRefs = (asScalaXml(depListResponse.getSOAPBody) \\ "Department")
        val deps = dbOrgStructure.getAllOrgStructures.filter(dep => {
          organizationRefMap.get(dep.getOrganization) == orgRef
        })

        val newOrgStructureRefMap = (new HashMap[OrgStructure, String]() /: deps)((m, d) => {
          depRefs.find(ref => {
            (d.getName contains (ref \ "Description").text) ||
              (d.getCode contains (ref \ "Description").text)
          }) match {
            case None => {}
            case Some(ref) => m.put(d, ref \ "Ref" text)
          }
          m
        })
        orgStructureRefMap.putAll(newOrgStructureRefMap)
      })

      info("Organization ref sync complete: " + organizationRefMap)
      info("Department ref sync complete: " + orgStructureRefMap)

    } catch {
      case ex: Exception => {
        error("Exception when syncing with 1C drugstore server", ex)
      }
    }
  }

  def getDepartmentRef(orgStructure: OrgStructure) =
    orgStructureRefMap.getOrElse(orgStructure, null)

  def getOrganizationRef(organization: Organisation) =
    organizationRefMap.getOrElse(organization, null)
}
