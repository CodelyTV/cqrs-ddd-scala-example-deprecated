package tv.codely.cqrs_ddd_scala_example.bus.domain

import java.util.UUID

import org.joda.time.DateTime

abstract class Request(val requestId: UUID, val createdAt: DateTime)
