package infrastructure

import akka.actor.ActorSystem
import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.unmarshalling.Unmarshal
import com.ping.config.Configuration
import com.ping.http.{PingHttpResponseData, WebClient}
import com.ping.models.RDMailConfig
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


trait PingClientApi extends WebClient {

  val clientApiHost = Configuration.config.getString("client.api.host")
  val clientApiUrl = Configuration.config.getString("client.api.url")
  val accessToken = Configuration.config.getString("client.api.access.token")
  val url = clientApiHost + clientApiUrl

  def getClientConfig(clientId: String): Future[Option[RDMailConfig]] = {
    getRequest(url + clientId, Map("accessToken" -> accessToken)).flatMap { response =>
      unmarshal(response).map {
        case Some(responseCart) => clientConfigResponseHandler(responseCart)
        case None => warn(s"Response from client api: $response")
          None
      }
    }
  }

  private def clientConfigResponseHandler(responseCart: PingHttpResponseData): Option[RDMailConfig] = {
    responseCart.data match {
      case Some(data) => data.extractOpt[RDMailConfig]
      case None => responseCart.message match {
        case Some(msg) => warn(s"Error during config look up. Response found: $msg")
          None
        case None => warn(s"Unexpected response state: $responseCart")
          None
      }
    }
  }

  private def unmarshal(response: HttpResponse): Future[Option[PingHttpResponseData]] = {
    Unmarshal(response.entity).to[String].map { clientResponse =>
      debug("Json response from client api: " + clientResponse)
      parse(clientResponse).extractOpt[PingHttpResponseData]
    }
  }
}

object PingClientApiFactory {
  def apply(actorSystem: ActorSystem) = new PingClientApi {
    val system: ActorSystem = actorSystem
  }
}
