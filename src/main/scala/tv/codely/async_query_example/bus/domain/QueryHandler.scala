package tv.codely.async_query_example.bus.domain

abstract class QueryHandler[P[_], QueryType <: Query, ResponseType <: Response] {
  def handle(query: QueryType): P[ResponseType]
}
