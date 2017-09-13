package tv.codely.async_query_example.user_greet.application.generate

import java.util.UUID

import org.joda.time.DateTime
import tv.codely.async_query_example.bus.domain.Query

case class GenerateUserGreetQuery(override val queryId: UUID, override val askedAt: DateTime, userId: UUID)
  extends Query(queryId, askedAt)
