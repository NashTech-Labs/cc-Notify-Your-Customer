package com.knoldus.mail.services

import com.ping.models.EmailInfo
import org.scalatest.WordSpec
import org.scalatest.mockito.MockitoSugar


class EmailApiSpec extends WordSpec{

  val emailInfo=EmailInfo(1,List("himanshu.rajput@knoldus.in"),List("kunal.sethi@knoldus.in"),List("himanshu.14mca1061@abes.ac.in"),"Test Email","testing")
  "Mail Api" can {
    "send mail" should {

      "with success" in {
        val result: Option[Int] = EmailApiImpl.send(emailInfo,"himanshu.rajput32@gmail.com","string.class")
        assert(result.contains(1))
      }
      "with failure" in{
        val result=EmailApiImpl.send(emailInfo,"","")
        assert(result.isEmpty)
      }
    }
  }
}