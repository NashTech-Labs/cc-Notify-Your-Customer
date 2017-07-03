package service

import com.ping.domain.PingSlack
import infrastructure.SlackApi
import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{MustMatchers, WordSpecLike}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}


class SlackServiceTest extends WordSpecLike with MustMatchers with MockitoSugar {

  val mockedSlackApi = mock[SlackApi]

  object MockSlackServiceTestObject extends SlackService {
    val slackApi: SlackApi = mockedSlackApi
  }

  "Slack Service " should {

    "send notification on slack" in {
      when(mockedSlackApi.send(PingSlack (Some("general"), "hey there !!"))).thenReturn(Future.successful(true))
      assert(Await.result(MockSlackServiceTestObject.sendSlackMsg(PingSlack(Some("general"), "hey there !!")), Duration(20, "seconds")))
    }

  }
}