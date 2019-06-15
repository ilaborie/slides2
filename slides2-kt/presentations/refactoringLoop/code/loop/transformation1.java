// Good old for style
public Collection transform(Collection input) {
    Collection results = new ArrayList();
    for (int i = 0; i < input.size(); i++) {
        Element element = (Element) input.get(i);
        Result res = transform(element);
        results.add(res);
    }
    return results;
}