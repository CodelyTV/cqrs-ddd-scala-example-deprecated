package tv.codely.cqrs_ddd_scala_example.user_greet.application.generate

import cats.Functor
import cats.implicits._

import tv.codely.cqrs_ddd_scala_example.user_greet.domain.{User, UserId, UserRepository}

final class UserGreetGenerator[P[_]: Functor](userRepository: UserRepository[P]) {
  def generate(userId: UserId): P[String] = userRepository.search(userId).map(greet)

  private def greet(userOption: Option[User]) = userOption.map("Hello " + _.name).getOrElse("User not found")
}
