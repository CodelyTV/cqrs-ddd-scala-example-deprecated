package tv.codely.cqrs_ddd_scala_example.bus.domain

import scala.reflect.ClassTag

final class QueryBus[P[_], QueryType <: Query, ResponseType <: Response](
  private val handlers: Map[
    ClassTag[QueryType],
    QueryHandler[P, QueryType,ResponseType]
  ]
) {
  def ask(query: QueryType): P[ResponseType] =
    handlers(ClassTag[QueryType](query.getClass)).handle(query)
}
