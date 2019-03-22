// input: List<Element> 
// transform : (Element) -> Result
input.stream()
    .map(element -> transform(element)) // :Stream<Result> 
    // ...
    ;