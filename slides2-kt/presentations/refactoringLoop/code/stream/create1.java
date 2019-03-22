// Create from a Collection
Stream<Element> s0 = col.stream();
Stream<Element> s1 = col.parallelStream();

// Create from array
Stream<Element> s2 = Arrays.stream(array);
Stream<Element> s3 = Stream.of(array);

// Manually
Stream<Element> s4 = Stream.of(elt1, elt2 /* , ...*/);
Stream<Element> s5 = Stream.empty();
