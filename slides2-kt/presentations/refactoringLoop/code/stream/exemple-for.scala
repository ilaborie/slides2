val monkeyFood = (for {
  fruit <- fruits
  it = emojify(fruit)
  if "🍌" == it || "🥥" == it
} yield it)