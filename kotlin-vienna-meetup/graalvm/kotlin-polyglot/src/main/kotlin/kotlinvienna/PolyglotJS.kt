package kotlinvienna

import org.graalvm.polyglot.Context

fun main() {
    println("Hello Java!")
    Context.create().use { context ->
        val value = context
            .eval("js", object {}::class.java.getResource("/hello.js")?.readText())
        value.execute("javascript")
    }
}