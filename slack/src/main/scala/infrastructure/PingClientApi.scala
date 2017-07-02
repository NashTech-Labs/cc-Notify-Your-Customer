package infrastructure

import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.unmarshalling.Unmarshal
import com.ping.config.Configuration
import com.ping.http.{HttpWebClient, PingHttpResponseData}
import com.ping.models.RDMailConfig

import scala.concurrent.Future


trait PingClientApi extends HttpWebClient {

  val clientApiHost = Configuration.config.getString("client.api.host")
  //localhost:9001
  val clientApiUrl = Configuration.config.getString("client.api.url")
  ///v1/config/slack/
  val accessToken = Configuration.config.getString("client.api.access.token")
  //dsfdsf-dsfsdfd-dsfdsfsdew
  val url = clientApiHost + clientApiUrl

  def getClientConfig(clientId: String): Future[Option[RDMailConfig]] = {
    getRequest(url + clientId, Map("accessToken" -> accessToken)).flatMap { response =>
      unmarshal(response).map {
        case Some(responseCart) => flightLookUpResponseHandler(responseCart)
        case None => warn(s"Response from client api: $response")
          None
      }
    }
  }

  private def flightLookUpResponseHandler(responseCart: PingHttpResponseData): Option[RDMailConfig] = {
    responseCart.data match {
      case Some(data) => data.extractOpt[RDMailConfig]
      case None => responseCart.message match {
        case Some(msg) => warn(s"Error during flight look up. Response found: $msg")
          None
        case None => warn(s"Unexpected response cart state: $responseCart")
          None
      }

    }
  }

  private def unmarshal(response: HttpResponse): Future[Option[PingHttpResponseData]] = {
    Unmarshal(response.entity).to[String].map { flightStatsResponseJSON =>
      debug("Json response from client api: " + flightStatsResponseJSON)
      parse(flightStatsResponseJSON).extractOpt[PingHttpResponseData]
    }
  }
}

object PingClientApi extends PingClientApi
