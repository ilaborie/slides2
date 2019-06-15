// Java 5 for Each
public Collection<Result> transform(Collection<Element> input) {
    Collection<Result> results = new ArrayList<Result>();
    for (Element element : input) {
        Result res = transform(element);
        results.add(res);
    }
    return results;
}