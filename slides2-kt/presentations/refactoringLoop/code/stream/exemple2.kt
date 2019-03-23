val fruits = listOf("apple", "banana", "orange", "coconut")

val food = fruits
    .also(::println) // "apple", "banana", "orange", "coconut"
    .map { emojify(it) } // 🍎, 🍌, 🍊, 🥥
    .firstOrNull { "🍌" == it || "🥥" == it } // 🍌
    ?: throw AngryMonkeyException()

feedMonkey(food)