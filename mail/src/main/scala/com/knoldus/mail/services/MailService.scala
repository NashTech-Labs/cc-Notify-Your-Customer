package com.knoldus.mail.services

import com.ping.models.EmailInfo


trait MailService extends EmailApi {

  def sendEmail(emailInfo: EmailInfo): Option[Int] = {
    //TODO fetch client configuration
    val userId = "emailId to be loaded from configuration"
    val password = "password to be loaded from configuration"
    send(emailInfo, userId, password)
  }

}

object MailServiceImpl extends MailService