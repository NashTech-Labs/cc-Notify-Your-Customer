package infrastructure

import com.ping.config.Configuration
import com.ping.domain.PingSlack
import slack.api.SlackApiClient

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._
import com.typesafe.config.ConfigFactory
trait SlackApi {


  val token: String  = Configuration.config.getString("client.api.access.token")


  val apiClient: SlackApiClient

  /**
    *
    * this method sends a msgBody on the channel named channelName as user
    */
  def send(slackDetail: PingSlack): Future[Boolean] = {
    val channelId: Future[Option[String]] = getChannelId(slackDetail.channelId.getOrElse("general"))

    channelId.map {
      _ match {
        case Some(channelId) =>
          apiClient.postChatMessage(channelId, slackDetail.message)
          true
        case None =>
          false

      }
    }
  }

  /**
    *
    * this method returns the channel id of the channel named channelName
    */
  def getChannelId(channelId: String): Future[Option[String]] = {
    val channelsFuture: Future[Seq[Option[String]]] = apiClient.listChannels().map(channels =>
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

object SlackApiImpl extends SlackApi {
  val apiClient: SlackApiClient = SlackApiClient(token)
}
