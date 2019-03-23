val fruits = listOf("apple", "banana", "orange", "coconut")

val food = fruits.asSequence()
    .also(::println) // just "apple", "banana"
    .map { emojify(it) }  // 🍎, 🍌, 🍊, 🥥
    .firstOrNull { "🍌" == it || "🥥" == it } // 🍌
    ?: throw AngryMonkeyException()

feedMonkey(food) // 🍌