package tv.codely.cqrs_ddd_scala_example.bus.infrastructure

import scala.reflect.ClassTag

import cats.Id
import tv.codely.cqrs_ddd_scala_example.bus.domain.{Query, QueryBus, QueryHandler, Response}

final class SyncQueryBus[QueryType <: Query, ResponseType <: Response](
  private val handlers: Map[ClassTag[QueryType], QueryHandler[Id, QueryType, ResponseType]]
) extends QueryBus[Id, QueryType, ResponseType] {

  override def ask(query: QueryType): Id[ResponseType] = {
    handlers(ClassTag[QueryType](query.getClass)).handle(query)
  }
}
