public List<Element> filter(List<Element> input) {
    List<Element> results = new ArrayList<>();
    for (Element element : input) {
        if(isSomething(element)) {
            results.add(element);
        }
    }
    return results;
}