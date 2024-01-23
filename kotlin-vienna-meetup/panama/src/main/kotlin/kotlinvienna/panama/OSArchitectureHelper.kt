package kotlinvienna.panama

import kotlinvienna.panama.OSArchitectureHelper.OSArchitecture.UNSUPPORTED

const val SYSTEM_PROPERTY_OS_ARCH = "os.arch"

class OSArchitectureHelper internal constructor(private val osArchitectureString: String) {
    enum class OSArchitecture(internal vararg val supportedArchitectureStrings: String) {
        AARCH64("aarch64"),
        X86_64("x86_64", "amd64"),
        UNSUPPORTED,
    }

    companion object {
        private val INSTANCE = OSArchitectureHelper(System.getProperty(SYSTEM_PROPERTY_OS_ARCH))
        fun ensureSupportedOSArchitecture() = INSTANCE.ensureSupportedOSArchitecture()
        val CURRENT_ARCHITECTURE_NAME = INSTANCE.currentArchitectureName
    }

    internal val currentArchitecture: OSArchitecture = calculateOSArchitecture(osArchitectureString)
    internal val currentArchitectureName: String = currentArchitecture.name.lowercase()
    internal fun ensureSupportedOSArchitecture() {
        if (currentArchitecture == UNSUPPORTED) {
            val supportedArchitectures =
                OSArchitecture.entries.map { it.supportedArchitectureStrings }.flatMap { it.asIterable() }
            throw RuntimeException("Supported Architectures are $supportedArchitectures but was '$osArchitectureString'")
        }
    }

    private fun calculateOSArchitecture(osArchitectureString: String) =
        osArchitectureString.lowercase().let { currentOSArchitectureString ->
            OSArchitecture.entries.find { architecture ->
                architecture.supportedArchitectureStrings.map { it.lowercase() }.contains(currentOSArchitectureString)
            } ?: UNSUPPORTED
        }
}
