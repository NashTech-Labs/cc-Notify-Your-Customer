package infrastructure

import _root_.slack.api.SlackApiClient
import _root_.slack.models.Channel
import com.ping.domain.PingSlack
import org.mockito.Mockito._
import org.scalatest.WordSpecLike
import org.scalatest.mock.MockitoSugar

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}


class SlackApiTest extends WordSpecLike with MockitoSugar {

  val mockedApiClient: SlackApiClient = mock[SlackApiClient]

  object SlackApiTestObj extends SlackApi {
    val apiClient: SlackApiClient = mockedApiClient
  }

  "Slack Api " should {
    val one: Long = 1.toLong
    "not send Slack Message when no channel found" in {
      when(SlackApiTestObj.apiClient.listChannels()).thenReturn(Future.successful(Seq()))
      val res: Boolean = Await.result(SlackApiTestObj.send(PingSlack(Some("general"), "hellooo !!!")), Duration(5, "seconds"))
      assert(res === false)
    }
    "send Slack Message" in {
      when(SlackApiTestObj.apiClient.listChannels()).thenReturn(Future.successful(Seq(Channel("1", "general", one, "me", None, None, None, None, None, None, None, None, None, None, None, None, None, None))))
      when(SlackApiTestObj.apiClient.postChatMessage("general", "sending message on slack !! :)")).thenReturn(Future("true"))
      val res: Boolean = Await.result(SlackApiTestObj.send(PingSlack(Some("general"), "sending message on slack !! :)")), Duration(5, "seconds"))
      assert(res === true)
    }

    "not send Slack Message due to incorrect channel name" in {
      when(SlackApiTestObj.apiClient.listChannels()).thenReturn(Future.successful(Seq(Channel("1", "general", one, "abc", None, None, None, None, None, None, None, None, None, None, None, None, None, None))))
      when(SlackApiTestObj.apiClient.postChatMessage("channel", "wrong channel name")).thenReturn(Future("false"))
      val res: Boolean = Await.result(SlackApiTestObj.send(PingSlack(Some("channel"), "sending message on slack !! :)")), Duration(20, "seconds"))
      assert(res === false)
    }
  }
}
