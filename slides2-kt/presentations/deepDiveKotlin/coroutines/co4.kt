suspend fun main(param: Int): Int {
    val step1Result = step1(param)
    val step2Result = step2(step1Result)
    return step3Result = step3(step2Result)
}