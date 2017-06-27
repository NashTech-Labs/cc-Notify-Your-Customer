package com.knoldus.mail.services

import com.ping.models.EmailInfo
import org.scalatest.WordSpec


class EmailApiSpec extends WordSpec {

  "Mail Api" can {
    "send mail" should {

      "with success" in {

        val result: Option[Int] = EmailApiImpl.send(EmailInfo(1,List("himanshu.rajput@knoldus.in"),List("kunal.sethi@knoldus.in"),List("himanshu.14mca1061@abes.ac.in"),"Test Email","testing"))
        assert(result.contains(1))
      }
      "with failure" in{
        val result=EmailApiImpl.send(EmailInfo(1,Nil,Nil,Nil,"",""))
        assert(result.isEmpty)
      }
    }
  }
}