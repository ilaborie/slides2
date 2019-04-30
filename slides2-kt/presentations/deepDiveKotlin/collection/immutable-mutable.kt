fun main(args: Array<String>) {

    val earthMoon = listOf(Moon("moon"))
    val add = earthMoon + Moon("moon 2")

    println("earthMoon: $earthMoon") // earthMoon: [Moon(name=moon)]
    println("add: $add")             // add: [Moon(name=moon), Moon(name=moon 2)]
    println("reference equality: ${earthMoon === add}") //reference equality: false

    println("\n")
    val earthMoon2 = mutableListOf(Moon("moon"))
    val add2 = earthMoon2.add(Moon("moon 2"))

    println("earthMoon2: $earthMoon2") // earthMoon2: [Moon(name=moon), Moon(name=moon 2)]
    println("add2: $add2")             // add2: true
}