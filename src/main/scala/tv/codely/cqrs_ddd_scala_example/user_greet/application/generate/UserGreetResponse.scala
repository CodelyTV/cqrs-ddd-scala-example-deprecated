package tv.codely.cqrs_ddd_scala_example.user_greet.application.generate

import java.util.UUID

import tv.codely.cqrs_ddd_scala_example.bus.domain.Response

case class UserGreetResponse(override val requestId: UUID, greet: String) extends Response(requestId)
