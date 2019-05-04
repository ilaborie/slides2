object DeepThought {
    fun answer(): Int {
        print("Computing ...")
        return 42
    }
}

fun main(args: Array<String>) {

    val ultimateQuestionOfLife: Int by lazy {
        DeepThought.answer()
    }
    println("The Ultimate Question of Life, " +
                    "the Universe and Everything ?")
    println("Answer: $ultimateQuestionOfLife" )
}