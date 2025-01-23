package com.github.hemeroc.publicspeaking.archtest

interface Greeter {
    fun greet(): String
}

class Greeter1(private val who: String = "World") : Greeter {
    override fun greet(): String {
        return "Hello, $who!"
    }
}

class Greeter2(private val greeter: Greeter) : Greeter by greeter {
    override fun greet() = greeter.greet()
}

fun main() {
    val greeter1 = Greeter1()
    val greeter2 = Greeter2(greeter1)
    println(greeter1.greet())
    println(greeter2.greet())
}