package com.ping.persistence.mapping

import com.ping.models.{RDMailConfig, RDTwilioConfig}
import com.ping.persistence.provider.DBProvider
import slick.lifted.ProvenShape

/**
  * Created by girish on 30/6/17.
  */
trait TwilioConfigMapping {this: DBProvider =>

  import driver.api._

  def twilioConfigInfo: TableQuery[TwilioConfigMapping] = TableQuery[TwilioConfigMapping]

  protected def twillioConfigInfoAutoInc = twilioConfigInfo returning twilioConfigInfo.map(_.id)


  class TwilioConfigMapping(tag: Tag) extends Table[RDTwilioConfig](tag, "twillio_config") {

    def * : ProvenShape[RDTwilioConfig] = (id, clientId, phoneNo, token, sID) <> (RDTwilioConfig.tupled, RDTwilioConfig.unapply)

    def id: Rep[Long] = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def clientId: Rep[Long] = column[Long]("client_id")

    def phoneNo: Rep[String] = column[String]("phone_no")

    def token: Rep[String] = column[String]("token")

    def sID: Rep[String] = column[String]("s_id")

  }

}
