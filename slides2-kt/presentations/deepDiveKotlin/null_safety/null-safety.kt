fun main() {
    val somethingNotNull: String = "aString"
    // somethingNotNull: String = null => compilation error

    var length = somethingNotNull.length

    var something: String? = null
    length = something?.length ?: 0
    length = createSomething()?.length ?: 0

    // length = createSomething()!!.length // throw kotlin.KotlinNullPointerException

    // SmartCast
    something = "aString"
    length = something.length // auto cast to String (no need of `?.`)
}

fun createSomething(): String? = null