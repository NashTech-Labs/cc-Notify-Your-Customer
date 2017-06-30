package com.ping.persistence.mapping

import com.ping.models.RDMailConfig
import com.ping.persistence.provider.DBProvider
import slick.lifted.ProvenShape


trait MailConfigMapping {
  this: DBProvider =>

  import driver.api._

  def mailConfigInfo: TableQuery[MailConfigMapping] = TableQuery[MailConfigMapping]

  protected def mailConfigInfoAutoInc = mailConfigInfo returning mailConfigInfo.map(_.id)

  class MailConfigMapping(tag: Tag) extends Table[RDMailConfig](tag, "mail_config") {

    def * : ProvenShape[RDMailConfig] = (id, clientId, email, password) <> (RDMailConfig.tupled, RDMailConfig.unapply)

    def id: Rep[Long] = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def clientId: Rep[Long] = column[Long]("client_id")

    def email: Rep[String] = column[String]("email")

    def password: Rep[String] = column[String]("address")

  }

}
