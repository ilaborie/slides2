List<String> errors = new ArrayList<>();
int errorCount = 0;
File file = new File(fileName);
String line = file.readLine();
while(errorCount < 40  && line != null) {
    if(line.startWith("ERROR")) {
        errors.add(line);
        errorCount++;
    }
    line = file.readLine();
}