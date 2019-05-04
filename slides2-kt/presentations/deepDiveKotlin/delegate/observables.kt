import kotlin.properties.Delegates

fun main(args: Array<String>) {

    var observable: String by Delegates.observable("Initial value") {
        _, old, new ->
            println("$old -> $new")
    }

    observable = "new value"
}