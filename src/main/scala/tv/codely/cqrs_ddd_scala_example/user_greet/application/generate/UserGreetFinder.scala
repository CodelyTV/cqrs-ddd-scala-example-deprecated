package tv.codely.cqrs_ddd_scala_example.user_greet.application.generate

import cats.Functor
import cats.implicits._
import tv.codely.cqrs_ddd_scala_example.user_greet.domain.{User, UserGreet, UserId, UserRepository}

final class UserGreetFinder[P[_]: Functor](userRepository: UserRepository[P]) {
  def generate(userId: UserId): P[UserGreet] =
    userRepository.search(userId).map(greet)

  private def greet(userOption: Option[User]) =
    userOption.map(user => UserGreet(user.name)).getOrElse(UserGreet.default)
}
