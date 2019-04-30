// Declare apply function with function as parameter
fun apply(x: Int, y: Int, operation: (Int, Int) -> Int): Int =
    operation(x, y)

// Declare function
fun sumf(x: Int, y: Int) : Int =
    x + y

// call apply with function reference
val sum5 = apply(2,3, ::sumf)

// store function reference
val sumLam = ::sumf

// call apply with the function reference
val sum6 = apply(1,5, sumLam)
