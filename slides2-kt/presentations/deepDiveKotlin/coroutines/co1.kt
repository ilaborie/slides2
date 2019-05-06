fun main(param: Int): Int {
    val step1Result = step1(param)
    return step2(step1Result)
}