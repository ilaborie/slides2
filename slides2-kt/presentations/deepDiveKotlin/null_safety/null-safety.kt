fun main(args: Array<String>) {

    val somethingNotNull: String = "aString"
    // somethingNotNull: String = null => compilation error

    var length = somethingNotNull.length

    var something: String? = null
    length = something?.length ?: 0

    length = something()?.length ?: 0

    // length = something()!!.length // throw kotlin.KotlinNullPointerException

    // SmartCast
    something = "aString"
    length = something.length
}

fun something(): String? = null
