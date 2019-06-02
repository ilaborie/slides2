// input: List<Element>
// accumulate : (Accumulator, Element) -> Accumulator
// Accumulator#merge : (Accumulator) -> Accumulator
Accumulator result =
    input.stream()
        .reduce(
            new Accumulator(),
            (acc, elt) -> accumulate(acc, elt),
            (acc1, acc2) -> acc1.merge(acc2)
        );