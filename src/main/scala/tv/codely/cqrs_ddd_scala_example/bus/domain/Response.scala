package tv.codely.cqrs_ddd_scala_example.bus.domain

import java.util.UUID

abstract class Response(val requestId: UUID)
