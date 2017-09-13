package tv.codely.cqrs_ddd_scala_example.bus.domain

trait QueryBus[P[_], QueryType <: Query, ResponseType <: Response] {
  def ask(query: QueryType): P[ResponseType]
}
