package tv.codely.async_query_example.acceptance

import java.util.UUID

import scala.reflect.classTag
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

import org.joda.time.DateTime
import org.scalatest.Matchers._
import org.scalatest._
import tv.codely.async_query_example.bus.infrastructure.AsyncQueryBus
import tv.codely.async_query_example.user_greet.application.generate.{GenerateUserGreetQuery, GenerateUserGreetQueryHandler, UserGreetGenerator}
import tv.codely.async_query_example.user_greet.infrastructure.InDelayedMemoryUserRepository

final class AsyncUserGreetGeneratorTest extends WordSpec with GivenWhenThen {

  "UserGreetGenerator with an AsyncQueryBus" should {
    "not block the execution flow until getting a response from a slow repository" in {

      Given("a UserGreetGenerator with a user repository with a notable delay")

      val notableDelay = 5.second
      val userRepository = new InDelayedMemoryUserRepository(notableDelay)
      val userGreetGeneratorWithDelay = new UserGreetGenerator(userRepository)
      val generateUserGreetQueryHandler = new GenerateUserGreetQueryHandler(userGreetGeneratorWithDelay)

      And("an AsyncQueryBus which doesn't block the execution flow until getting a response")

      val queryBus = new AsyncQueryBus(Map(
        classTag[GenerateUserGreetQuery] -> generateUserGreetQueryHandler
      ))

      When("we ask the GenerateUserGreetQuery to the AsyncQueryBus")

      val query = GenerateUserGreetQuery(UUID.randomUUID(), DateTime.now(), UUID.fromString("1646fd5c-de2b-435f-b20f-ad1f50924dfe"))
      val futureGreeting = queryBus.ask(query)

      Then("it should say hello to someone")

      pprint.log("This is a text printed once we've asked the query to the AsyncQueryBus")

      futureGreeting.map { greeting =>
        pprint.log("This is a text printed once the repository returns the value")

        greeting.greet shouldBe "Hello Rafa"
      }
    }
  }
}
