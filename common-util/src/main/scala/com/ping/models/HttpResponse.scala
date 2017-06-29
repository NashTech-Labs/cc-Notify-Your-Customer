package com.ping.models


case class HttpResponse(
                         code: Int,
                         message: Option[String],
                         data: AnyRef
                       )

