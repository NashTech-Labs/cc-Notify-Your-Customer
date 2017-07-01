package com.ping.domain

case class HttpResponse(
                         code: Int,
                         message: Option[String],
                         data: AnyRef
                       )

