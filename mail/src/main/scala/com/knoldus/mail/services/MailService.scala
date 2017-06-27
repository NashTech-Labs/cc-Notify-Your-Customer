package com.knoldus.mail.services

import com.ping.models.EmailInfo


class MailService extends EmailApi{
  def sendEmail(emailInfo:EmailInfo): Option[Int] = send(emailInfo)
}