package io.github.ilaborie.slides2.kt.jvm

import io.github.ilaborie.slides2.kt.cli.Notifier.Companion.StopWatch
import io.github.ilaborie.slides2.kt.utils.Try
import java.lang.System.nanoTime
import java.time.Duration
import java.time.Duration.ofNanos
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeUnit.DAYS
import java.util.concurrent.TimeUnit.HOURS
import java.util.concurrent.TimeUnit.MICROSECONDS
import java.util.concurrent.TimeUnit.MILLISECONDS
import java.util.concurrent.TimeUnit.MINUTES
import java.util.concurrent.TimeUnit.NANOSECONDS
import java.util.concurrent.TimeUnit.SECONDS

object JvmStopWatch : StopWatch {
    // FIXME remove JVM dependencies
    override fun <T> time(supplier: () -> T): Pair<T, String> =
        Try { nanoTime() to supplier() }
            .map { (start, result) ->
                result to humanize(ofNanos(nanoTime() - start))
            }
            .unwrap()

    private fun humanize(duration: Duration): String =
        duration.toNanos().let { nanos ->
            val unit = chooseUnit(nanos)
            val value = nanos.toDouble() / NANOSECONDS.convert(1L, unit).toDouble()
            String.format("%.4g %s", value, abbreviate(unit))
        }

    private fun chooseUnit(nanos: Long): TimeUnit =
        sequenceOf(DAYS, HOURS, MINUTES, SECONDS, MILLISECONDS, MICROSECONDS)
            .filter { it.convert(nanos, NANOSECONDS) > 0L }
            .firstOrNull() ?: NANOSECONDS

    private fun abbreviate(unit: TimeUnit): String =
        when (unit) {
            NANOSECONDS  -> "ns"
            MICROSECONDS -> "μs"
            MILLISECONDS -> "ms"
            SECONDS      -> "s"
            MINUTES      -> "min"
            HOURS        -> "h"
            DAYS         -> "d"
            else         -> throw AssertionError()
        }

}