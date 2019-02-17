package io.github.ilaborie.slides2.kt.cli

import kotlin.system.measureNanoTime


class Notifier(private val stopWatch: StopWatch) {

    private fun display(level: Int, message: String) {
        println(" ".repeat(level) + message)
    }

    fun debug(label: String? = null, message: () -> String) {
        display(0, Styles.debug(label ?: "Debug") + message())
    }

    fun info(label: String? = null, message: () -> String) {
        display(0, Styles.info(label ?: "Info ") + message())
    }

    fun warning(label: String? = null, message: () -> String) {
        display(0, Styles.warn(label ?: "warn ") + message())
    }

    fun error(label: String? = null, cause: Exception? = null, message: () -> String) {
        display(0, Styles.error(label ?: "error") + message())
        cause?.stackTrace?.forEach { display(1, Styles.stacktrace(it.toString())) }
    }

    fun <T> time(label: String, supplier: () -> T): T {
        display(1, Styles.time(">>> ") + label)
        val (result, time) = stopWatch.time(supplier)
        display(1, Styles.time("<<< ") + label + " took " + Styles.time(time))
        return result;
    }

    companion object {

        interface StopWatch {
            fun <T> time(supplier: () -> T): Pair<T, String>
        }

        private val noTime = object : StopWatch {
            override fun <T> time(supplier: () -> T): Pair<T, String> {
                var result: T? = null
                val nanos = measureNanoTime {
                    result = supplier()
                }
                return result!! to "$nanos ns"
            }
        }

        fun withoutTime(): Notifier =
            Notifier(noTime)

    }
}