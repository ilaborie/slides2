public Collection<Element> filter(Collection<Element> input) {
    Collection<Element> results = new ArrayList<>();
    for (Element element : input) {
        if(isSomething(element)) {
            results.add(element);
        }
    }
    return results;
}