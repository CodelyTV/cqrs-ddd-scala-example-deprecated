package tv.codely.cqrs_ddd_scala_example.bus.domain

abstract class QueryHandler[P[_], QueryType <: Query] {
  def handle(query: QueryType): P[QueryType#QueryResponse]
}
