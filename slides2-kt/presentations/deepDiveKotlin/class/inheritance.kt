open class SmallBody {
    open fun sizeRange(): IntRange = 0..10
}

// Inherit SmallBody
data class Comet(val name: String): SmallBody()

// Inherit SmallBody
data class Asteroid(val name: String): SmallBody() {
    override fun sizeRange(): IntRange = 0..4
}

fun main(args: Array<String>) {
    val bodies = listOf(Comet("Halley"), Asteroid("Adeona"))

    bodies.forEach { body ->
        println("$body: ${body.sizeRange()}")
    }
}