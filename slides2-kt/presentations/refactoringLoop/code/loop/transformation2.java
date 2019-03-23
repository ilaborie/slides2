// Java 5 for Each
public List<Result> transform(List<Element> input) {
    List<Result> results = new ArrayList<Result>();
    for (Element element : input) {
        Result res = transform(element);
        results.add(res);
    }
    return results;
}