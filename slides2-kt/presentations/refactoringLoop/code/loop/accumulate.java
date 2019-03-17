public Accumulator compute(List<Element> input) {
    Accumulator accumulator = initialValue;
    for (Element element : input) {
        // accumulate :: (Accumulator, Element) -> Accumulator
        accumulator = accumulate(accumulator, element);
    }
    return accumulator;
}