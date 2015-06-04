package ru.korus.tmis.core.auth

import java.lang
import javax.ejb.{Singleton, Stateless}

import org.codehaus.jackson.annotate.JsonIgnoreProperties
import org.slf4j.{LoggerFactory, Logger}
import org.springframework.http.{MediaType, HttpHeaders, HttpEntity}
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
class CasBean extends CasBeanLocal {
  val logger: Logger = LoggerFactory.getLogger(classOf[CasBean])
  override def createToken(login: String, password: String, defaultToken: String): String = {
    if (ConfigManager.Cas.isActive) {
      val url: String = "acquire/"
      try {
        val casReq = new CasReq();
        casReq.setLogin(login)
        casReq.setPassword(password)
        val res: CasResp = postForCasResp(url, casReq)
        if (res.token == null || res.token.isEmpty) {
          logger.info("CAS exception: " +  res.getException + " message: " + res.getMessage )
          defaultToken
        } else {
          res.token
        }
      }
      catch {
        case ex: RestClientException => {
          ex.printStackTrace
          return defaultToken
        }
      }
    } else {
      defaultToken
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
    if (ConfigManager.Cas.isActive) {
      val url: String = "prolong/"
      try {
        val casReq = new CasReq();
        casReq.setToken(token)
        res = postForCasResp(url, casReq)
        if(!res.getSuccess) {
          logger.info("CAS exception: " + res.getException + " message: " + res.getMessage)
        }
        res.setSuccess(res.getSuccess && res.token == token)
        res
      }
      catch {
        case ex: RestClientException => {
          ex.printStackTrace
          res.setSuccess(false)
          res
        }
      }
    } else {
      res.setSuccess(true)
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
  var success: Boolean = _

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
