// Good old for style
public List<Result> transform(List<Element> input) {
    List<Result> results = new ArrayList<>();
    for (int i = 0; i < input.size(); i++) {
        Element element = input.get(i);
        Result res = transform(element);
        results.add(res);
    }
    return results;
}