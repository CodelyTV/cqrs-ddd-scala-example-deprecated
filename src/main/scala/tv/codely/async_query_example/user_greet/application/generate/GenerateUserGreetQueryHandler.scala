package tv.codely.async_query_example.user_greet.application.generate

import cats.Functor
import cats.implicits._

import tv.codely.async_query_example.bus.domain.QueryHandler
import tv.codely.async_query_example.user_greet.domain.UserId

final class GenerateUserGreetQueryHandler[P[_]: Functor](private val generator: UserGreetGenerator[P])
  extends QueryHandler[P, GenerateUserGreetQuery, UserGreetResponse] {

  override def handle(query: GenerateUserGreetQuery): P[UserGreetResponse] = {
    val userId = UserId(query.userId)

    generator.generate(userId).map(greet => UserGreetResponse(query.requestId, greet))
  }
}
