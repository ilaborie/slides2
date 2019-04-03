List<String> errors = 
    Files.lines(Paths.get(fileName))
        .filter(line -> line.startsWith("ERROR"))
        .limit(40)
        .collect(toList());