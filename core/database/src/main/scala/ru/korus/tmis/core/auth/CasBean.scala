package ru.korus.tmis.core.auth

import javax.ejb.{DependsOn, Singleton}

import org.codehaus.jackson.annotate.JsonIgnoreProperties
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.http.{HttpEntity, HttpHeaders, MediaType}
import org.springframework.web.client.{RestClientException, RestTemplate}
import ru.korus.tmis.scala.util.ConfigManager

import scala.beans.BeanProperty

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        29.04.2015, 18:20 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Singleton
@DependsOn(Array("DbSettingsBean"))
class CasBean extends CasBeanLocal {
  val logger: Logger = LoggerFactory.getLogger(classOf[CasBean])

  override def createToken(login: String, password: String): CasResp = {
    val url: String = "acquire/"
    try {
      val casReq = new CasReq()
      casReq.setLogin(login)
      casReq.setPassword(password)
      postForCasResp(url, casReq)
    } catch {
      case ex: RestClientException =>
        logger.error("REST exception:", ex)
        null
    }
  }

  def postForCasResp(url: String, casReq: CasReq): CasResp = {
    val baseUrl: String = ConfigManager.Cas.ServiceUrl
    val fullUrl: String = baseUrl + "/cas/api/" + url
    val restTemplate: RestTemplate = new RestTemplate
    val headers: HttpHeaders = new HttpHeaders
    headers.setContentType(MediaType.APPLICATION_JSON)
    val entity: HttpEntity[CasReq] = new HttpEntity[CasReq](casReq, headers)
    restTemplate.postForObject(fullUrl, entity, classOf[CasResp])
  }

  override def checkToken(token: String): CasResp = {
    var res: CasResp = new CasResp
    val url: String = "check/"
    try {
      val casReq = new CasReq()
      casReq.setProlong(true)
      casReq.setToken(token)
      res = postForCasResp(url, casReq)
      if (!res.getSuccess) {
        logger.info("CAS exception: " + res.getException + " message: " + res.getMessage)
      }
      res.setSuccess(res.getSuccess && res.token == token)
      res
    } catch {
      case ex: RestClientException =>
        logger.error("REST exception:", ex)
        res.setSuccess(false)
        res
    }
  }

  override def checkTokenWithoutProlong(token: String): CasResp = {
    var res: CasResp = new CasResp
    val url: String = "check/"
    try {
      val casReq = new CasReq()
      casReq.setProlong(false)   //only this differs
      casReq.setToken(token)
      res = postForCasResp(url, casReq)
      if (!res.getSuccess) {
        logger.info("CAS exception: " + res.getException + " message: " + res.getMessage)
      }
      res.setSuccess(res.getSuccess && res.token == token)
      res
    } catch {
      case ex: RestClientException =>
        logger.error("REST exception:", ex)
        res.setSuccess(false)
        res
    }
  }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class CasResp {
  // {"user_id": 205, "token": "787a3965f9fc84ab2323cab28151cc6d", "deadline": 1430323408.388633, "success": true, "ttl": 1099.9999339580536}
  //{"exception": "EExpiredToken", "message": "Token 53fd4dfc5f68d989ce0f1bce23a9ba94 is expired or not taken", "success": false}

  @BeanProperty
  var user_id: Int = _

  @BeanProperty
  var token: String = _

  @BeanProperty
  var deadline: Double = _

  @BeanProperty
  var success: Boolean = _

  @BeanProperty
  var ttl: Double = _

  @BeanProperty
  var exception: String = _

  @BeanProperty
  var message: String = _

}

@JsonIgnoreProperties(ignoreUnknown = true)
class CasReq {
  // {"login": "ДБалашов", "password": "1"}
  // {"token": "301eb9302b3a0eceefdfa71e66eb0c9e", "prolong": true}

  @BeanProperty
  var token: String = _

  @BeanProperty
  var login: String = _

  @BeanProperty
  var password: String = _

  @BeanProperty
  var prolong: Boolean = _

}

