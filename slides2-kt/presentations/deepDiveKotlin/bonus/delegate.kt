import kotlin.properties.Delegates
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

fun main(args: Array<String>) {
    // Delegate
    val value: String by MyDelegateClass()
    println(value)

    // Lazy
    val ultimateQuestionOfLife: Int by lazy {
        DeepThought.answer()
    }
    println("The Ultimate Question of Life, " +
                    "the Universe and Everything ?")
    println("Answer: $ultimateQuestionOfLife" )

    // Observable
    var observable: String by Delegates.observable("Initial value") {
            _, old, new ->
        println("$old -> $new")
    }

    observable = "new value"

    // lateinit
    // println(str) kotlin.UninitializedPropertyAccessException
    str = "Hello RivieraDev"
    println(str)
}

lateinit var str: String

class MyDelegateClass : ReadOnlyProperty<Nothing?, String> {
    override operator fun getValue(thisRef: Nothing?,
                                   property: KProperty<*>) = "Chocolatine"
}

object DeepThought {
    fun answer(): Int {
        print("Computing ...")
        return 42
    }
}
