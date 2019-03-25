fun transformR(input: List<Element>): List<Result> =
    if (input.isEmpty()) listOf()
    else listOf(transform(input.first())) +
            transformR(input.drop(1))