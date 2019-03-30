// T: Type du Stream
// A: Type de l'accumulateur
// R: Type du résultat
interface Collector<T,A,R> {
    Supplier<A> supplier() 
    BiConsumer<A,T> accumulator();
    BinaryOperator<A> combiner();
    Function<A,R> finisher();
    // ...
}