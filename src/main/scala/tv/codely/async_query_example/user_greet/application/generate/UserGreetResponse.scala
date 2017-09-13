package tv.codely.async_query_example.user_greet.application.generate

import java.util.UUID

import tv.codely.async_query_example.bus.domain.Response

case class UserGreetResponse(override val requestId: UUID, greet: String) extends Response(requestId)
