// Declare lambda
val suml: (Int, Int) -> Int = { x: Int, y: Int ->
    x + y
}

// call compute with suml lambda
val sum3 = compute(1, 2, suml)

// call compute with lamda
val sum4 = compute(1, 3) { x, y ->
    x + y
}