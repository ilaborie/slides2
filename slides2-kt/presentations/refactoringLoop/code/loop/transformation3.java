// Java 8 forEach & lambda expression
public List<Result> transform(List<Element> input) {
    List<Result> results = new ArrayList<>();
    input.forEach(element -> {
        Result res = transform(element);
        results.add(res); // 😨
    });
    return results;
}