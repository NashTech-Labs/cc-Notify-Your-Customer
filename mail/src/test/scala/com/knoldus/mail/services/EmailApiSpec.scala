package com.knoldus.mail.services

import com.ping.models.EmailInfo
import org.mockito.Mockito.when
import org.scalatest.WordSpec
import org.scalatest.mockito.MockitoSugar


class EmailApiSpec extends WordSpec with MockitoSugar{
  val emailApi=mock[EmailApi]
  val emailInfo=EmailInfo(1,List("himanshu.rajput@knoldus.in"),List("kunal.sethi@knoldus.in"),List("himanshu.14mca1061@abes.ac.in"),"Test Email","testing")
  "Mail Api" can {
    "send mail" should {

      "with success" in {
        when(emailApi.send(emailInfo,"","")).thenReturn(Some(1))
        val result: Option[Int] = emailApi.send(emailInfo,"","")
        assert(result.contains(1))
      }
      "with failure" in{
        when(emailApi.send(emailInfo,"","")).thenReturn(None)
        val result=emailApi.send(emailInfo,"","")
        assert(result.isEmpty)
      }
    }
  }
}