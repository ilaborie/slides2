
import java.util.ArrayList;
import java.util.List;


public class Plop {
    static class Child {
    }

    static abstract class Element {

        abstract Iterable<Child> getChildren();
    }

    static class Result {
    }

    static class Accumulator {
    }


    public List<Result> transform(List<Element> input) {
        List<Result> results = new ArrayList<>();
        for (Element element : input) {
            Result res = transform(element);
            results.add(res);
        }
        return results;
    }

    public List<Element> filter(List<Element> input) {
        List<Element> results = new ArrayList<>();
        for (Element element : input) {
            if (isSomething(element)) {
                results.add(element);
            }
        }
        return results;
    }

    Accumulator initialValue;


    public Accumulator compute(List<Element> input) {
        Accumulator accumulator = initialValue;
        for (Element element : input) {
            // accumulate :: (Accumulator, Element) -> Accumulator
            accumulator = accumulate(accumulator, element);
        }
        return accumulator;
    }

    public Accumulator nest(List<Element> input) {
        Accumulator accumulator = initialValue;

        for (Element element : input) {
            for (Child child : input.getChildren()) {
                // transform, filter, accumulate, ...
            }
        }

        return accumulator;
    }


    public Accumulator other(List<Element> input) {
        Accumulator accumulator = initialValue;
        boolean cond1;
        boolean cond2;

toto: for (Element element : input) {
    tata: for (Child child : input.getChildren()) {
        if(cond1) {
            continue toto;
        } else if (cond2) {
            break tata;
        }
        // ...
    }
}

        return accumulator;
    }


    private Accumulator accumulate(Accumulator accumulator, Element element) {
        throw new AssertionError();
    }

    private boolean isSomething(Element element) {
        throw new AssertionError();
    }

    private Result transform(Element element) {
        throw new AssertionError();
    }

}
