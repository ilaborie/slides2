// Java 10 with `var`
public Collection<Result> transform(Collection<Element> input) {
    var results = new ArrayList<Result>();
    input.forEach(element -> {
        Result res = transform(element);
        results.add(res); // 😨
    });
    return results;
}