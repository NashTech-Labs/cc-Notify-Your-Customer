package com.ping.persistence.mapping

import com.ping.models.User
import com.ping.persistence.provider.DBProvider
import slick.lifted.ProvenShape

trait UserMapping {
  this: DBProvider =>

  import driver.api._

  def userInfo: TableQuery[UserMapping] = TableQuery[UserMapping]

  protected def userAutoInc = userInfo returning userInfo.map(_.id)

  class UserMapping(tag: Tag) extends Table[User](tag, "User1") {
    def * : ProvenShape[User] = (id, name, address) <> (User.tupled, User.unapply)

    def id: Rep[Long] = column[Long]("id", O.PrimaryKey)

    def name: Rep[String] = column[String]("name")

    def address: Rep[String] = column[String]("address")

  }

}
