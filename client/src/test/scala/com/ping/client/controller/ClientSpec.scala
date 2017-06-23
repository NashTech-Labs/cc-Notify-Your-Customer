package com.ping.client.controller

import java.sql.Timestamp

import com.ping.client.app.ClientApi
import com.ping.client.service.ClientService
import com.ping.json.JsonHelper
import com.ping.models._
import com.twitter.finagle.http.Status
import com.twitter.finatra.http.{EmbeddedHttpServer, HttpTest}
import com.twitter.inject.Mockito
import com.twitter.inject.server.FeatureTest

class ClientSpec extends FeatureTest with Mockito with HttpTest with JsonHelper {

  val mockClientService = smartMock[ClientService]

  override protected def server = new EmbeddedHttpServer(ClientApi).bind[ClientService](mockClientService)

  test("insert client successfully") {
  val clientReq = ClientRequest("Harshit", "harshit@knoldus.com", "946044006", "NSEZ, Noida", "India", "201305")
    val dbClient = DBClient(1, "Harshit", "harshit@knoldus.com", "94604440066")
    val dbAddress = DBClientAddress(1, 1, "NSEZ Noida", "India", "201305")
    val token = DBAccessToken(1, 1, "some_hashed_token", new Timestamp(System.currentTimeMillis))
    val clientDetails = ClientDetails(dbClient, dbAddress, token)

//    when(mockClientService.insert(clientReq)) thenReturn Future.successful(clientDetails)
    val respBody = HttpResponse(200, data = Some(clientDetails), message = None)
    server.httpPost(
      path = "/add",
      postBody = write(clientReq),
      andExpect = Status(200),
      withJsonBody = write(respBody)
    )
  }

}
