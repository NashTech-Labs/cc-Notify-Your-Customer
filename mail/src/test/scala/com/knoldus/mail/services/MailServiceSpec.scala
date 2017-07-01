package com.knoldus.mail.services

import com.ping.models.EmailInfo
import org.scalatest.WordSpec
import org.scalatest.mockito.MockitoSugar

/**
  * Created by knoldus on 30/6/17.
  */
class MailServiceSpec extends WordSpec{

  val emailInfo = EmailInfo(1, List("himanshu.rajput@knoldus.in"), List("kunal.sethi@knoldus.in"), List("himanshu.14mca1061@abes.ac.in"), "Test Email", "testing")

  "Mail Service" can {
    "send Mail" should {
      "with Success" in {
        val result = MailServiceImpl.sendEmail(emailInfo)
        assert(result.contains(1))
      }
      "with failure" in {
        val result = MailServiceImpl.sendEmail(emailInfo)
        assert(result.isEmpty)
      }
    }
  }
}
