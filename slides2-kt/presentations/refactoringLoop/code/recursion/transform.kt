fun transformzR(input: List<Element>): List<Result> =
    if (input.isEmpty()) listOf() // end of recursion
    else {
        // Deconstruct
        val head = input.first()
        val tail = input.drop(1)

        // Handle head
        val transformed = transform(head)

        // Recursion
        listOf(transformed) + transformzR(tail)
    }