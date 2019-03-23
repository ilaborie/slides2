// input: List<Element> 
// Element#getChildren : () -> Collection<Child>

input.stream()
    .flatMap(element -> 
        element.getChildren().stream())
    // : Stream<Child>
    // ...
    ;