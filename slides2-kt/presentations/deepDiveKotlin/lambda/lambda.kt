// Declare lambda
val suml: (Int, Int) -> Int = { x: Int, y: Int -> x + y }

// call apply with suml lambda
val sum3 = apply(1, 2, suml)

// call apply with lamda
val sum4 = apply(1, 3) { x, y -> x + y }
