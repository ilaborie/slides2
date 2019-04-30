fun main(args: Array<String>) {
    val moons = (1..9).map { Moon("Moon #$it") }.toList()

    println(moons.javaClass) // class java.util.ArrayList

    moons.javaClass.methods
        .find { it.name == "add" && it.parameterCount == 1 }
        ?.invoke(moons, Moon("XXX"))

    println(moons.joinToString("\n"))
    // Moon(name=Moon #1)
    // Moon(name=Moon #2)
    // Moon(name=Moon #3)
    // Moon(name=Moon #4)
    // Moon(name=Moon #5)
    // Moon(name=Moon #6)
    // Moon(name=Moon #7)
    // Moon(name=Moon #8)
    // Moon(name=Moon #9)
    // Moon(name=XXX)
}
