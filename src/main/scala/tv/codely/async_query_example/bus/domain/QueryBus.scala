package tv.codely.async_query_example.bus.domain

trait QueryBus[P[_], QueryType <: Query, ResponseType <: Response] {
  def ask(query: QueryType): P[ResponseType]
}
