String[] data = "lorem ... amet".split(" ");
Stream<String> stream = Arrays.stream(data)
    .map(s -> {
        System.out.println(s);
            return s.toUpperCase();
    }).filter(s -> {
        System.out.println(s);
        return s.startsWith("A");
    }).peek(s -> System.out.println("s"));

System.out.println("Created stream");