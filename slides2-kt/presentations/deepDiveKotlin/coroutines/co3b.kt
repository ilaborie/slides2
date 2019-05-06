fun main(param: Int): Int {
    return step1(param) { step1Result ->
        step2(step1Result) { step2Result ->
            step3(step2Result)
        }
    }