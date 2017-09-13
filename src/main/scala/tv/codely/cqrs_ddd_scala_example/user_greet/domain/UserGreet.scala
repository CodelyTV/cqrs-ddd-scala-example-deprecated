package tv.codely.cqrs_ddd_scala_example.user_greet.domain

final case class UserGreet(name: String) {
  def greet: String = "Hello " + name
}

object UserGreet {
  def default: UserGreet = UserGreet("Fulanito")
}
