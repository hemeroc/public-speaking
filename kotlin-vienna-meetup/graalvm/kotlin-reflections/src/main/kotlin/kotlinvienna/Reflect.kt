package kotlinvienna

fun main() {
    val kClass = Class.forName("kotlinvienna.SimpleGreeter").kotlin
    val greeter = kClass.constructors.first().call("Reflections") as Greeter
    greeter.greet()
}

interface Greeter {
    fun greet()
}

class SimpleGreeter(private val name: String) : Greeter {
    override fun greet() {
        println("Hello $name!")
    }
}
