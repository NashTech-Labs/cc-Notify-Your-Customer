package com.ping.client.db.mapping

import com.ping.client.db.provider.DBProvider
import com.ping.domain.DBClientAddress
import slick.lifted.ProvenShape


private[db] trait ClientAddressMapping {
  this: DBProvider =>

  import driver.api._

  val clientAddressInfo: TableQuery[ClientAddressMapping] = TableQuery[ClientAddressMapping]

  protected def clientAddressAutoInc = clientAddressInfo returning clientAddressInfo.map(_.id)

  class ClientAddressMapping(tag: Tag) extends Table[DBClientAddress](tag, "client_address") {

    def * : ProvenShape[DBClientAddress] = (id, clientId, address, country, pinCode) <> (DBClientAddress.tupled, DBClientAddress.unapply)

    def id: Rep[Long] = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def address: Rep[String] = column[String]("address")

    def country: Rep[String] = column[String]("country")

    def pinCode: Rep[String] = column[String]("pin_code")

    def clientId: Rep[Long] = column[Long]("client_id")

  }

}
