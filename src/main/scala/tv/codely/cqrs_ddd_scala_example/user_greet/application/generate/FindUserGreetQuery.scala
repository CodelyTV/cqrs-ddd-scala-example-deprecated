package tv.codely.cqrs_ddd_scala_example.user_greet.application.generate

import java.util.UUID

import org.joda.time.DateTime
import tv.codely.cqrs_ddd_scala_example.bus.domain.Query

case class FindUserGreetQuery(override val queryId: UUID, override val askedAt: DateTime, userId: UUID)
  extends Query(queryId, askedAt)
