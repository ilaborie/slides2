package io.github.ilaborie.slides2.kt.cli

import kotlin.system.measureNanoTime


class Notifier(private val stopWatch: StopWatch) {

    var level = 0

    private fun display(message: String, level: Int = this.level) {
        println(" ".repeat(level) + message)
    }

    fun debug(label: String? = null, message: () -> String) {
        display(Styles.debug(label ?: "Debug") + " " + message())
    }

    fun info(label: String? = null, message: () -> String) {
        display(Styles.info(label ?: "Info ") + " " + message())
    }

    fun warning(label: String? = null, message: () -> String) {
        display(Styles.warn(label ?: "warn ") + " " + message())
    }

    fun error(label: String? = null, cause: Exception? = null, message: () -> String) {
        display(Styles.error(label ?: "error") + " " + message())
        cause?.stackTrace?.forEach { display(Styles.stacktrace(it.toString()), level + 1) }
    }

    fun <T> time(label: String, supplier: () -> T): T {
        display(Styles.time(">>> ") + label, level)
        level++
        val (result, time) = stopWatch.time(supplier)
        level--
        display(Styles.time("<<< ") + label + " took " + Styles.time(time), level)
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