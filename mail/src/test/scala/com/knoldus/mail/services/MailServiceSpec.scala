package com.knoldus.mail.services

import com.ping.domain.PingEmail
import com.ping.models.EmailInfo
import infrastructure.MailPingHttpClient
import org.mockito.Mockito._
import org.scalatest.WordSpecLike
import org.scalatest.mockito.MockitoSugar

import scala.concurrent.Await
import scala.concurrent.duration.Duration


class MailServiceSpec extends WordSpecLike with MockitoSugar {
  val mockedEmailApi: EmailApi = mock[EmailApi]
  val emailInfo =PingEmail(1, List("himanshu.rajput@knoldus.in"), List("kunal.sethi@knoldus.in"), List("himanshu.14mca1061@abes.ac.in"), "Test Email", "testing")



//  "Mail Service" can {
//    "send Mail" should {
//      "with Success" in {
//        when(mockedEmailApi.send(emailInfo,"","")).thenReturn(None)
//        val result =Await.result(MailServiceImpl.(emailInfo),Duration.Inf)
//        assert(result.isEmpty)
//      }
//      "with failure" in {
//        when(mockedEmailApi.send(emailInfo, "", "")).thenReturn(None)
//        val result = Await.result(sendEmail(emailInfo),Duration.Inf)
//        assert(result.isEmpty)
//      }
//    }
//  }
}
