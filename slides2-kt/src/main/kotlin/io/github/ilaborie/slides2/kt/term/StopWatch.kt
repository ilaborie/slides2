package io.github.ilaborie.slides2.kt.term

import io.github.ilaborie.slides2.kt.term.TimeUnit.Companion.Days
import io.github.ilaborie.slides2.kt.term.TimeUnit.Companion.Hours
import io.github.ilaborie.slides2.kt.term.TimeUnit.Companion.Micros
import io.github.ilaborie.slides2.kt.term.TimeUnit.Companion.Millis
import io.github.ilaborie.slides2.kt.term.TimeUnit.Companion.Minutes
import io.github.ilaborie.slides2.kt.term.TimeUnit.Companion.Nanos
import io.github.ilaborie.slides2.kt.term.TimeUnit.Companion.Seconds
import kotlin.system.measureNanoTime


object StopWatch {
    fun <T> time(supplier: () -> T): Pair<T, String> {
        var result: T? = null
        val nanos = measureNanoTime {
            result = supplier()
        }
        return result!! to humanize(nanos)
    }

    private fun humanize(nanos: Long): String {
        val unit = chooseUnit(nanos.toDouble())
        val value = unit.fromNanos(nanos.toDouble())
        return String.format("%.4g %s", value, unit.unit)
    }

    private fun chooseUnit(nanos: Double): TimeUnit =
        sequenceOf(Days, Hours, Minutes, Seconds, Millis, Micros)
            .filter { it.fromNanos(nanos) > 1L }
            .firstOrNull() ?: Nanos
}