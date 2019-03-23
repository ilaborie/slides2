public Element find(List<Element> elements) {
    if (elements.isEmpty()) {
        return null;
    }
    Element head = elements.get(0);
    if (isSomething(head)) {
        return head;
    }
    List<Element> tail = elements.subList(1, elements.size());
    return find(tail);
}