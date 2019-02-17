package io.github.ilaborie.slides2.kt.utils


sealed class Try<out T> {

    fun unwrap(): T =
        when (this) {
            is Success -> value
            is Failure -> throw cause
        }

    fun <U> map(mapper: (T) -> U): Try<U> =
        when (this) {
            is Success -> Success(mapper(value))
            is Failure -> this
        }

    fun <U> flatMap(mapper: (T) -> Try<U>): Try<U> =
        when (this) {
            is Success -> mapper(value)
            is Failure -> this
        }

    companion object {
        class Success<T>(val value: T) : Try<T>()
        class Failure(val cause: Exception) : Try<Nothing>()

        operator fun <T> invoke(block: () -> T): Try<T> =
            try {
                Success(block())
            } catch (e: Exception) {
                Failure(e)
            }
    }
}


