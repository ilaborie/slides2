public List<Result> transformR(List<Element> input) {
    if (input.isEmpty()) { // end of recursion
        return Collections.emptyList();
    }
    // Deconstruct
    Element head = input.get(0);
    List<Element> tail = input.subList(1, input.size() - 1);

    // Handle head
    Result transformed = transform(head);

    // Recursion
    List<Result> results = new ArrayList<>();
    results.add(transformed);
    results.addAll(transformR(tail));
    return results;
}