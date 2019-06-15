// Java 8 forEach & lambda expression
public Collection<Result> transform(Collection<Element> input) {
    Collection<Result> results = new ArrayList<>();
    input.forEach(element -> {
        Result res = transform(element);
        results.add(res); // 😨
    });
    return results;
}