// Declare apply function with function as parameter
fun compute(x: Int, y: Int, operation: (Int, Int) -> Int): Int =
    operation(x, y)

// Declare function
fun sumf(x: Int, y: Int): Int =
    x + y

// call compute with function reference
val sum5 = compute(2, 3, ::sumf)

// store function reference
val sumLam = ::sumf

// call compute with the function reference
val sum6 = compute(1, 5, sumLam)