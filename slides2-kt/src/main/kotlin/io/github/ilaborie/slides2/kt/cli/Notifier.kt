package io.github.ilaborie.slides2.kt.cli


object Notifier {

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
        val (result, time) = StopWatch.time(supplier)
        level--
        display(Styles.time("<<< ⏱ ") + label + " took " + Styles.time(time), level)
        return result;
    }

}