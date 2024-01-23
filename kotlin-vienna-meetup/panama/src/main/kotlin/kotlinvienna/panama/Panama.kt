package kotlinvienna.panama

import kotlinvienna.panama.OSArchitectureHelper.Companion.CURRENT_ARCHITECTURE_NAME
import java.lang.foreign.Arena
import java.lang.foreign.FunctionDescriptor
import java.lang.foreign.Linker
import java.lang.foreign.MemorySegment
import java.lang.foreign.SymbolLookup
import java.lang.foreign.ValueLayout.ADDRESS
import java.lang.foreign.ValueLayout.JAVA_INT
import java.lang.invoke.MethodHandle

fun main() {
    OSArchitectureHelper.ensureSupportedOSArchitecture()
    val panama = Panama()
    println(panama.greet("Kotlin Vienna"))
    println(panama.greet(panama.rot("Kotlin Vienna", 13)))
}

class Panama {
    companion object {
        val LINKER = try {
            Linker.nativeLinker() ?: throw RuntimeException("Failed to load native linker")
        } catch (ex: UnsupportedOperationException) {
            throw RuntimeException("Failed to load native linker", ex)
        }
        val STRING = ADDRESS ?: throw RuntimeException("Failed to create platform string type")
        val RETURN_STRING = STRING
    }

    private val greetMethodHandle: MethodHandle
    private val rotMethodHandle: MethodHandle

    init {
        System.loadLibrary("greet-${CURRENT_ARCHITECTURE_NAME}")
        val greetLibrary: SymbolLookup = SymbolLookup.loaderLookup() // loads from system libraries for simplicity
        greetMethodHandle = greetMethodHandle(greetLibrary)
        System.loadLibrary("rot-${CURRENT_ARCHITECTURE_NAME}")
        val rotLibrary: SymbolLookup = SymbolLookup.loaderLookup() // loads from system libraries for simplicity
        rotMethodHandle = rotMethodHandle(rotLibrary)
    }

    private fun greetMethodHandle(symbolLookup: SymbolLookup): MethodHandle {
        val greetMethodSegment = symbolLookup.find("greet").get()
        return LINKER.downcallHandle(
            greetMethodSegment,
            FunctionDescriptor.of(
                RETURN_STRING,
                *arrayOf(STRING),
            ),
        )
    }

    fun greet(name: String): String =
        Arena.ofConfined().use { offHeap ->
            val zeroLengthMemorySegment =
                greetMethodHandle.invokeWithArguments(offHeap.allocateUtf8String(name)) as MemorySegment
            zeroLengthMemorySegment
                .reinterpret(Long.MAX_VALUE)
                .getUtf8String(0)
        }

    private fun rotMethodHandle(symbolLookup: SymbolLookup): MethodHandle {
        val rotMethodSegment = symbolLookup.find("rot").get()
        return LINKER.downcallHandle(
            rotMethodSegment,
            FunctionDescriptor.of(
                RETURN_STRING,
                *arrayOf(STRING, JAVA_INT),
            ),
        )
    }

    fun rot(name: String, rot: Int): String =
        Arena.ofConfined().use { offHeap ->
            val zeroLengthMemorySegment =
                rotMethodHandle(offHeap.allocateUtf8String(name), rot) as MemorySegment
            zeroLengthMemorySegment
                .reinterpret(Long.MAX_VALUE)
                .getUtf8String(0)
        }
}
