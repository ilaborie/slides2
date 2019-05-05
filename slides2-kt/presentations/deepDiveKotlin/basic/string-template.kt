fun greeting(who: Someone) {
    println("Hello $who!")
    println("Hello ${who.firstName} ${who.lastName}!")
    println("""
        multi
        line "$who"
        string""".trimIndent())
}