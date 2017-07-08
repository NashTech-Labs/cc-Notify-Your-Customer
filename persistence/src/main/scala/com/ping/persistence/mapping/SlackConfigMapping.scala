package com.ping.persistence.mapping

import com.ping.models.RDSlackConfig
import com.ping.persistence.provider.DBProvider
import slick.lifted.ProvenShape


trait SlackConfigMapping {
  this: DBProvider =>

  import driver.api._

  protected def slackConfigInfoAutoInc = slackConfigInfo returning slackConfigInfo.map(_.id)

  def slackConfigInfo: TableQuery[SlackConfigMapping] = TableQuery[SlackConfigMapping]

  class SlackConfigMapping(tag: Tag) extends Table[RDSlackConfig](tag, "slack_config") {

    def * : ProvenShape[RDSlackConfig] = (id, clientId, token, defaultChannel) <> (RDSlackConfig.tupled, RDSlackConfig.unapply)

    def id: Rep[Long] = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def clientId: Rep[Long] = column[Long]("client_id")

    def token: Rep[String] = column[String]("token")

    def defaultChannel: Rep[String] = column[String]("default_channel")

  }

}
