object Logger {
    var enable: Boolean = false

    inline fun log(msg: () -> String) {
        if (enable) {
            println(msg())
        }
    }
}

fun main() {
    Logger.log { "Hello" }
}