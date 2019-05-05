// In my-app.jar
fun main() {
    println(compute())
}

// In lib-v1.0.0
fun compute(result: Int = 1) = result

// In lib-v1.0.1
fun compute(result: Int = 42) = result