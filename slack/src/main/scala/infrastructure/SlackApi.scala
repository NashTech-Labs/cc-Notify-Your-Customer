package infrastructure

import akka.actor.ActorSystem
import com.ping.domain.PingSlack
import com.ping.logger.PingLogger
import slack.api.SlackApiClient

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._
import scala.util.control.NonFatal

trait SlackApi extends PingLogger{

  val pingApiClient: SlackPingHttpClient

  /**
    *
    * this method sends a msgBody on the channel named channelName as user
    */
  def send(slackDetail: PingSlack): Future[String] = {
    pingApiClient.getClientConfig(slackDetail.clientId.toString).flatMap {
      case Some(config) => {
        info(s"Got slack config for client: ${slackDetail.clientId} with token: ${config.token}")
        val slackClient: SlackApiClient = new SlackApiClient(config.token)
        getChannelId(slackDetail.channelId.getOrElse("general"), slackClient).flatMap {
          case Some(channel) =>
            info(s"Got channel id ${channel}, posing message..")
            slackClient.postChatMessage(channel, slackDetail.message) map{ response =>
              info(s"Got response: $response")
              response
            }
          case None => Future.successful("Channel id not found")
        } recover{
          case NonFatal(ex) =>
            error("Error found while connecting to slack: ", ex)
            ex.getMessage
        }
      }
      case None => Future.successful("Config not found")
    }
  }

  /**
    *
    * this method returns the channel id of the channel named channelName
    */
  def getChannelId(channelId: String, slackClient: SlackApiClient): Future[Option[String]] = {
    val channelsFuture: Future[Seq[Option[String]]] = slackClient.listChannels().map(channels =>
      channels.map(channel =>
        if (channel.name == channelId) {
          Some(channel.id)
        }
        else {
          None
        }
      ))
    channelsFuture.map(channels => channels.find(channel => channel.isDefined).flatten)
  }

}

object SlackApiImpl {
  def apply(system: ActorSystem) = new SlackApi {
    val pingApiClient: SlackPingHttpClient = PingClientApiFactory(system)
  }
}
