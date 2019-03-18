public Element find(List<Element> elements) {
    if (elements.isEmpty()) {
        return null;
    }
    Element head = elements.get(0);
    List<Element> tail = elements.subList(1, elements.size());
    if (isSomething(head)) {
        return head;
    }
    return find(tail);
}