package tv.codely.cqrs_ddd_scala_example.bus.domain

import scala.reflect.ClassTag

import cats.implicits._
import tv.codely.cqrs_ddd_scala_example.types.ThrowableTypeClasses.MonadErrorThrowable

final class QueryBus[P[_]: MonadErrorThrowable] {
  private var handlers: Map[Class[_], Query => P[_ <: Response]] = Map.empty

  def ask[Q <: Query: ClassTag](query: Q): P[Q#QueryResponse] = {
    val classTag = implicitly[ClassTag[Q]]
    handlers
      .get(classTag.runtimeClass)
      .fold[P[Q#QueryResponse]](MonadErrorThrowable[P].raiseError(QueryHandlerNotFoundForQuery))(handler =>
        handler(query).map(_.asInstanceOf[Q#QueryResponse]))
  }

  def subscribe[Q <: Query: ClassTag](handler: QueryHandler[P, Q]): Unit = {
    val classTag = implicitly[ClassTag[Q]]
    handlers = handlers +
      (classTag.runtimeClass -> ((query: Query) => handler.handle(query.asInstanceOf[Q])))
  }
}

object QueryHandlerNotFoundForQuery extends Exception
