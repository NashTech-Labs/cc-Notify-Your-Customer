package com.knoldus.mail.services

import com.ping.models.EmailInfo
import org.mockito.Mockito._
import org.scalatest.WordSpecLike
import org.scalatest.mockito.MockitoSugar


class MailServiceSpec extends WordSpecLike with MockitoSugar {
  val mockedEmailApi: EmailApi = mock[EmailApi]
  val emailInfo = EmailInfo(1, List("himanshu.rajput@knoldus.in"), List("kunal.sethi@knoldus.in"), List("himanshu.14mca1061@abes.ac.in"), "Test Email", "testing")

  object MockMailTestObject extends MailService {
    val emailApi: EmailApi = mockedEmailApi
  }

  "Mail Service" can {
    "send Mail" should {
      "with Success" in {
        when(mockedEmailApi.send(emailInfo,"","")).thenReturn(None)
        val result = MockMailTestObject.sendEmail(emailInfo)
        assert(result.isEmpty)
      }
      "with failure" in {
        when(mockedEmailApi.send(emailInfo, "", "")).thenReturn(None)
        val result = MockMailTestObject.sendEmail(emailInfo)
        assert(result.isEmpty)
      }
    }
  }
}
