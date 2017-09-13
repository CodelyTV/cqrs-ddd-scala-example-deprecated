package tv.codely.async_query_example.bus.domain

import java.util.UUID

import org.joda.time.DateTime

abstract class Request(val requestId: UUID, val createdAt: DateTime)
