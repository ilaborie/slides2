fun main(args: Array<String>) {
    val aPair = "Earth" to "Moon" // ~  Pair("Earth", "Moon")
    val (planet, moon) = aPair

    val aTriple = Triple("Voyager 1", 1977, listOf("Jupiter", "Saturn"))
    val (probeNane, launchYear, flyOver) = aTriple
}
