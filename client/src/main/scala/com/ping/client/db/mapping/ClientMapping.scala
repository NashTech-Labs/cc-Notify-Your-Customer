package com.ping.client.db.mapping

import com.ping.client.db.provider.DBProvider
import slick.lifted.ProvenShape
import com.ping.models.DBClient

private[db] trait ClientMapping {
  this: DBProvider =>

  import driver.api._

  val clientInfo: TableQuery[ClientMapping] = TableQuery[ClientMapping]

  protected def clientAutoInc = clientInfo returning clientInfo.map(_.id)

  class ClientMapping(tag: Tag) extends Table[DBClient](tag, "client") {
    def * : ProvenShape[DBClient] = (id, name, userName, password, email, phone) <> (DBClient.tupled, DBClient.unapply)

    def id: Rep[Long] = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def name: Rep[String] = column[String]("name")

    def userName: Rep[String] = column[String]("user_name")

    def password: Rep[String] = column[String]("password")

    def email: Rep[String] = column[String]("email")

    def phone: Rep[String] = column[String]("phone")

  }

}
