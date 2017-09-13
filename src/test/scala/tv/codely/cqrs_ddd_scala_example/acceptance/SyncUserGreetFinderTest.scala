package tv.codely.cqrs_ddd_scala_example.acceptance

import java.util.UUID

import scala.reflect.classTag

import org.joda.time.DateTime
import org.scalatest._
import org.scalatest.Matchers._
import tv.codely.cqrs_ddd_scala_example.bus.domain.QueryBus
import tv.codely.cqrs_ddd_scala_example.user_greet.application.generate.{FindUserGreetQuery, FindUserGreetQueryHandler, UserGreetFinder}
import tv.codely.cqrs_ddd_scala_example.user_greet.infrastructure.InMemoryUserRepository

final class SyncUserGreetFinderTest extends WordSpec with GivenWhenThen {

  "UserGreetGenerator with an SyncQueryBus" should {
    "block the execution flow until getting a response from the repository" in {

      Given("a UserGreetGenerator with a user repository")

      val userRepository                = new InMemoryUserRepository()
      val userGreetGeneratorWithDelay   = new UserGreetFinder(userRepository)
      val generateUserGreetQueryHandler = new FindUserGreetQueryHandler(userGreetGeneratorWithDelay)

      And("an SyncQueryBus which block the execution flow until getting a response")

      val queryBus = new QueryBus(
        Map(
          classTag[FindUserGreetQuery] -> generateUserGreetQueryHandler
        )
      )

      When("we ask the GenerateUserGreetQuery to the SyncQueryBus")

      val query = FindUserGreetQuery(
        UUID.randomUUID(),
        DateTime.now(),
        UUID.fromString(
          "1646fd5c-de2b-435f-b20f-ad1f50924dfe"
        )
      )
      val greeting = queryBus.ask(query)

      Then("it should say hello to someone")

      pprint.log("This is a text printed once we've asked the query to the SyncQueryBus")

      greeting.greet shouldBe "Hello Rafa"
    }
  }
}
