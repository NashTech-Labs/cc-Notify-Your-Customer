package com.ping.persistence.mapping

import com.ping.models.RDClient
import com.ping.persistence.provider.DBProvider
import slick.lifted.ProvenShape


trait ClientMapping {
  this: DBProvider =>

  import driver.api._

  def clientInfo: TableQuery[ClientMapping] = TableQuery[ClientMapping]

  protected def clientAutoInc = clientInfo returning clientInfo.map(_.id)

  class ClientMapping(tag: Tag) extends Table[RDClient](tag, "client") {
    def * : ProvenShape[RDClient] = (id, name, email, address, password, accessToken) <>
      (RDClient.tupled, RDClient.unapply)

    def id: Rep[Long] = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def name: Rep[String] = column[String]("name")

    def email: Rep[String] = column[String]("email")

    def address: Rep[String] = column[String]("address")

    def password: Rep[String] = column[String]("password")

    def accessToken: Rep[String] = column[String]("access_token")

  }

}
