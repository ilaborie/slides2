class SmallBody {
    fun sizeRange(): IntRange = 0..10
}

// Inherit SmallBody
data class Comet(val name: String)

// Inherit SmallBody
data class Asteroid(val name: String) {
    fun sizeRange(): IntRange = 0..4
}

fun main(args: Array<String>) {
    val bodies = listOf(Comet("Halley"), Asteroid("Adeona"))

//    bodies.forEach { body ->
//        println("$body: ${body.sizeRange()}")
//    }
}
