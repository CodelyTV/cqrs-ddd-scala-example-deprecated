package tv.codely.async_query_example.user_greet.domain

trait UserRepository[P[_]] {
  def search(userId: UserId): P[Option[User]]
}
