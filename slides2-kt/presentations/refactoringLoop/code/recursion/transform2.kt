fun transformzR(input: List<Element>): List<Result> =
    if (input.isEmpty()) listOf()
    else listOf(transform(input.first())) +
            transformzR(input.drop(1))