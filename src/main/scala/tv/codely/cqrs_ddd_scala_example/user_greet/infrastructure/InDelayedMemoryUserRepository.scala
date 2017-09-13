package tv.codely.cqrs_ddd_scala_example.user_greet.infrastructure

import java.util.UUID

import scala.concurrent.Future
import scala.concurrent.duration.FiniteDuration

import tv.codely.cqrs_ddd_scala_example.user_greet.domain.{User, UserId, UserRepository}

final class InDelayedMemoryUserRepository(delay: FiniteDuration) extends UserRepository[Future] {
  private val users: Map[UserId, User] = Map(
    UserId(UUID.fromString("1646fd5c-de2b-435f-b20f-ad1f50924dfe")) -> User(
      UserId(UUID.fromString("1646fd5c-de2b-435f-b20f-ad1f50924dfe")),
      "Rafa"
    ),
    UserId(UUID.fromString("bf660b5b-8211-4634-8933-1fafbfa434a9")) -> User(
      UserId(UUID.fromString("bf660b5b-8211-4634-8933-1fafbfa434a9")),
      "Javi"
    )
  )

  override def search(userId: UserId): Future[Option[User]] = {
    Thread.sleep(delay.toMillis)

    Future.successful(users.get(userId))
  }
}
