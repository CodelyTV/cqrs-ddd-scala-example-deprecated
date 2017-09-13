package tv.codely.cqrs_ddd_scala_example.user_greet.application.generate

import cats.Functor
import cats.implicits._

import tv.codely.cqrs_ddd_scala_example.bus.domain.QueryHandler
import tv.codely.cqrs_ddd_scala_example.user_greet.domain.UserId

final class FindUserGreetQueryHandler[P[_]: Functor](
  private val generator: UserGreetFinder[P]
) extends QueryHandler[P, FindUserGreetQuery, UserGreetResponse] {
  override def handle(query: FindUserGreetQuery): P[UserGreetResponse] = {
    val userId = UserId(query.userId)

    generator.generate(userId).map(greet =>
      UserGreetResponse(query.requestId, greet.greet)
    )
  }
}
