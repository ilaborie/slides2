// Java 10 with `var`
public List<Result> transform(List<Element> input) {
    var results = new ArrayList<Result>();
    input.forEach(element -> {
        Result res = transform(element);
        results.add(res); // 😨
    });
    return results;
}