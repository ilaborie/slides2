val fruits = List("apple", "banana", "orange", "coconut")

val food = fruits
    .map(emojify) // 🍎, 🍌, 🍊, 🥥
    .find(it => "🍌" == it || "🥥" == it)
    .get

feedMonkey(food)