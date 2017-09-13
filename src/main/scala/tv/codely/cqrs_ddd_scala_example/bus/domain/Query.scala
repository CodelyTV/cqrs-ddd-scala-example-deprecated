package tv.codely.cqrs_ddd_scala_example.bus.domain

import java.util.UUID

import org.joda.time.DateTime

abstract class Query(val queryId: UUID, val askedAt: DateTime) extends Request(queryId, askedAt)
