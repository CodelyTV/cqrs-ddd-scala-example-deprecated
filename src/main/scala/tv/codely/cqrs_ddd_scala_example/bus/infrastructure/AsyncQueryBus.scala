package tv.codely.cqrs_ddd_scala_example.bus.infrastructure

import scala.concurrent.Future
import scala.reflect.ClassTag

import tv.codely.cqrs_ddd_scala_example.bus.domain.{Query, QueryBus, QueryHandler, Response}
final class AsyncQueryBus[QueryType <: Query, ResponseType <: Response](
  private val handlers: Map[ClassTag[QueryType], QueryHandler[Future, QueryType, ResponseType]]
) extends QueryBus[Future, QueryType, ResponseType] {

  override def ask(query: QueryType): Future[ResponseType] = {
    handlers(ClassTag[QueryType](query.getClass)).handle(query)
  }
}
