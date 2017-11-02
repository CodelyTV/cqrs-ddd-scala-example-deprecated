package tv.codely.cqrs_ddd_scala_example.integration

import java.util.UUID

import cats.Applicative
import cats.syntax.applicative._
import cats.instances.either._
import org.joda.time.DateTime
import org.scalatest.{Matchers, WordSpec}
import tv.codely.cqrs_ddd_scala_example.bus.domain._
import tv.codely.cqrs_ddd_scala_example.types.ThrowableTypeClasses.EitherThrowable

final class QueryBusTest extends WordSpec with Matchers {

  val queryBus = new QueryBus[EitherThrowable]
  queryBus.subscribe(new PingQueryHandler)
  queryBus.subscribe(new EchoQueryHandler)

  "A QueryBus" should {
    "return the correct ping response" in {
      val pingQuery = PingQuery(UUID.randomUUID(), DateTime.now())

      val result = queryBus.ask(pingQuery)
      result.isRight shouldBe true
      result.map(response => response shouldBe PingResponse(pingQuery.queryId))
    }

    "return the correct echo response" in {
      val echoQuery = EchoQuery("Coming soon in CodelyTV...", UUID.randomUUID(), DateTime.now())

      val result = queryBus.ask(echoQuery)
      result.isRight shouldBe true
      result.map(response => response shouldBe EchoResponse(echoQuery.message, echoQuery.queryId))
    }

    "fail when no handler exists for the asked query" in {
      val responseWithoutHandlers = new Response(UUID.randomUUID()) {}
      val queryWithoutHandlers = new Query(UUID.randomUUID(), DateTime.now()) {
        override type QueryResponse = responseWithoutHandlers.type
      }

      val result = queryBus.ask(queryWithoutHandlers)
      result.isLeft shouldBe true
      result.left.map(_ shouldBe QueryHandlerNotFoundForQuery)
    }
  }
}

private case class PingQuery(override val queryId: UUID, override val createdAt: DateTime)
    extends Query(queryId, createdAt) {
  override type QueryResponse = PingResponse
}
private case class PingResponse(override val requestId: UUID) extends Response(requestId)
private class PingQueryHandler[P[_]: Applicative] extends QueryHandler[P, PingQuery] {
  override def handle(query: PingQuery): P[PingResponse] = Applicative[P].pure(PingResponse(query.queryId))
}

private case class EchoQuery(message: String, override val queryId: UUID, override val createdAt: DateTime)
    extends Query(queryId, createdAt) {
  override type QueryResponse = EchoResponse
}
private case class EchoResponse(message: String, override val requestId: UUID) extends Response(requestId)
private class EchoQueryHandler[P[_]: Applicative] extends QueryHandler[P, EchoQuery] {
  override def handle(query: EchoQuery): P[EchoResponse] =
    EchoResponse(query.message, query.queryId).pure
}
