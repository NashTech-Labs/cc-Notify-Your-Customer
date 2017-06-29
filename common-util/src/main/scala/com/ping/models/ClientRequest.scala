package com.ping.models


case class ClientRequest (
                         name: String,
                         email: String,
                         phone: String,
                         address: String,
                         country: String,
                         pinCode: String
                         ) {
  require(email.matches("""^\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$"""), "Email id is not valid")
}
