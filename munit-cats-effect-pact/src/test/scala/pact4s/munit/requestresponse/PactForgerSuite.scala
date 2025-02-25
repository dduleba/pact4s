package pact4s.munit.requestresponse

import au.com.dius.pact.consumer.{ConsumerPactBuilder, PactTestExecutionContext}
import au.com.dius.pact.core.model.RequestResponsePact
import cats.effect.IO
import cats.implicits.catsSyntaxApplicativeId
import io.circe.Json
import io.circe.syntax.EncoderOps
import org.http4s.circe.jsonEncoder
import org.http4s.ember.client.EmberClientBuilder
import org.http4s.headers.`Content-Type`
import org.http4s._
import org.typelevel.ci.CIString
import pact4s.circe.implicits._
import pact4s.munit.RequestResponsePactForger

class PactForgerSuite extends RequestResponsePactForger {

  override val pactTestExecutionContext: PactTestExecutionContext = new PactTestExecutionContext(
    "./munit-cats-effect-pact/target/pacts"
  )

  val pact: RequestResponsePact =
    ConsumerPactBuilder
      .consumer("Pact4sConsumer")
      .hasPactWith("Pact4sProvider")
      .uponReceiving("a request to say Hello")
      .path("/hello")
      .method("POST")
      .body(Json.obj("name" -> "harry".asJson), "application/json")
      .headers("other-header" -> "howdy")
      .willRespondWith()
      .status(200)
      .body(Json.obj("hello" -> "harry".asJson))
      .uponReceiving("a request to say Goodbye")
      .path("/goodbye")
      .method("GET")
      .willRespondWith()
      .status(204)
      .`given`("bob exists", Map("foo" -> "bar"))
      .uponReceiving("a request to find a friend")
      .path("/anyone-there")
      .method("GET")
      .willRespondWith()
      .status(200)
      .body(Json.obj("found" -> "bob".asJson))
      .toPact()

  val client = ResourceSuiteLocalFixture(
    "httpClient",
    EmberClientBuilder.default[IO].build
  )

  override def additionalMunitFixtures: Seq[Fixture[_]] = Seq(client)

  pactTest("munit pact test") { server =>
    val request = Request[IO](
      method = Method.POST,
      uri = Uri.unsafeFromString(server.getUrl + "/hello"),
      headers = Headers(Header.Raw(CIString("other-header"), "howdy"))
    )
      .withEntity(Json.obj("name" -> "harry".asJson))
    client().run(request).use {
      _.as[String].assertEquals("{\"hello\":\"harry\"}")
    }
  }

  pactTest("another munit pact test") { server =>
    val request = Request[IO](
      uri = Uri.unsafeFromString(server.getUrl + "/goodbye"),
      headers = Headers(`Content-Type`(MediaType.application.json))
    )
    client().run(request).use {
      _.status.code.pure[IO].assertEquals(204)
    }
  }

  pactTest("test with provider state") { server =>
    val request = Request[IO](uri = Uri.unsafeFromString(server.getUrl + "/anyone-there"))
    client().run(request).use {
      _.as[String].assertEquals("{\"found\":\"bob\"}")
    }
  }
}
