fun find(elements: List<Element>): Element? =
    when {
        elements.isEmpty()    ->
            null
        isSomething(elements) ->
            elements.first()
        else                  ->
            filterAlt(elements.drop(1))
    }