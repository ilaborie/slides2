fun find(input: List<Element>): Element? =
    when {
        input.isEmpty()    -> null
        isSomething(input) -> input.first()
        else               -> find(input.drop(1))
    }