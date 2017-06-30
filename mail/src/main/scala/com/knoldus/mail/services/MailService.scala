package com.knoldus.mail.services

import com.ping.models.EmailInfo


trait MailService extends EmailApi {

  def sendEmail(emailInfo: EmailInfo): Option[Int] = {
    //TODO fetch client configuration
    val userId = ""
    val password = ""
    send(emailInfo, userId, password)
  }

}

object MailServiceImpl extends MailService