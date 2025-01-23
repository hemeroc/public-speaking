package com.github.hemeroc.publicspeaking.archtest

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.architecture.KoArchitectureCreator.assertArchitecture
import com.lemonappdev.konsist.api.architecture.Layer
import com.lemonappdev.konsist.api.verify.assertTrue
import org.junit.jupiter.api.Test

internal class ArchitectureTest {

    @Test
    fun `service components should only be used in application and service packages`() {
    }

    @Test
    fun `concrete io components should only be accessed in the application package`() {
    }

    @Test
    fun `concrete service components should only be accessed in the application package`() {
    }

    @Test
    fun `io should not dependent on other io`() {
    }

    @Test
    fun `ensure that architecture layers are not mixed`() {
        Konsist
            .scopeFromProject()
            .assertArchitecture {
                val service = Layer("service", "..service..")
                val serviceImpl = Layer("service.impl", "..service.impl..")
                val io = Layer("io", "..io..")
                val ioImpl = Layer("io.impl", "..io.impl..")
                val model = Layer("model", "..model..")

                service.dependsOn(model)
                io.dependsOn(model)
                serviceImpl.dependsOn(model, service)
                ioImpl.dependsOn(model, io)
                model.dependsOnNothing()
            }
    }
}
