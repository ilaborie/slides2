object Plop {
    lateinit var str: String

    fun info() {
        println(Plop::str.isInitialized)
    }
}

fun main() {
    Plop.info() // false
    Plop.str = "Hello"
    Plop.info() // true

    println(str) // Hello
}