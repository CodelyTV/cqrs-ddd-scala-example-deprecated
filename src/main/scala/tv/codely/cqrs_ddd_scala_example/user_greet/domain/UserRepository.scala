package tv.codely.cqrs_ddd_scala_example.user_greet.domain

trait UserRepository[P[_]] {
  def search(userId: UserId): P[Option[User]]
}
