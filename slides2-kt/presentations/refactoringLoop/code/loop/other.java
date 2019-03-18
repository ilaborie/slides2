toto: for (Element element : input) {
    tata: for (Child child : element.getChildren()) {
        if(cond1) {
            continue toto;
        } else if (cond2) {
            break tata;
        }
        // ...
    }
}