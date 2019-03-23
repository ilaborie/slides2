// Some IntStream
IntStream.range(0, 9); // 0,1,2,3,4,5,6,7,8
IntStream.rangeClosed(0, 9);  // 0,1,2,3,4,5,6,7,8,9
IntStream.iterate(0, i -> i + 1); // 0,1,2,3,...
IntStream ints = new Random().ints();

// With Path
Path path = Paths.get("plop.md");
Stream<String> lines = Files.lines(path);
Stream<Path> walk = Files.walk(path);

// With a Spliterator
Iterable<Element> elts = // ...
StreamSupport.stream(elts.spliterator(), false);