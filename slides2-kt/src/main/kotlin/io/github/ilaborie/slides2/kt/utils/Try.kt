package io.github.ilaborie.slides2.kt.utils


sealed class Try<T> {

    fun unwrap(): T =
        when (this) {
            is Success -> value
            is Failure -> throw cause
        }

    fun <U> map(mapper: (T) -> U): Try<U> =
        when (this) {
            is Success -> Success(mapper(value))
            is Failure -> Failure(this.cause)
        }

    fun <U> flatMap(mapper: (T) -> Try<U>): Try<U> =
        when (this) {
            is Success -> mapper(value)
            is Failure -> Failure(this.cause)
        }

    fun recover(function: (Throwable) -> T): T =
        when (this) {
            is Success -> value
            is Failure -> function(this.cause)
        }

    companion object {
        class Success<T>(val value: T) : Try<T>()
        class Failure<T>(val cause: Exception) : Try<T>()

        operator fun <T> invoke(block: () -> T): Try<T> =
            try {
                Success(block())
            } catch (e: Exception) {
                Failure(e)
            }
    }
}


