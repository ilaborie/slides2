// input: List<Element> 
// isSomething : (Element) -> boolean

input.stream()
    .filter(element -> isSomething(element))
    // : Stream<Element>
    // ...
    ;