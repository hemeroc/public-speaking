package com.github.hemeroc.publicspeaking.archtest

import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses
import com.tngtech.archunit.library.Architectures.layeredArchitecture


@AnalyzeClasses(packages = ["com.github.hemeroc.publicspeaking.archtest"])
internal class ArchitectureTest {
    @ArchTest
    fun `service components should only be used in application and service packages`(importedClasses: JavaClasses) {
        val inlineRule = classes()
            .that().resideInAPackage("..service..")
            .should().onlyBeAccessed().byAnyPackage("..application..", "..service..")
        inlineRule.check(importedClasses)
    }

    @ArchTest
    fun `concrete io components should only be accessed in the application package`(importedClasses: JavaClasses) {
        classes()
            .that().resideInAPackage("..io.impl..")
            .should().onlyBeAccessed().byAnyPackage("..application..")
            .check(importedClasses)
    }

    @ArchTest
    fun `io should not dependent on other io`(importedClasses: JavaClasses) {
        noClasses().that().resideInAPackage("..io.impl..")
            .should().dependOnClassesThat().resideInAPackage("..io.impl..")
            .check(importedClasses)
    }

    @ArchTest
    fun `ensure that architecture layers are not mixed`(importedClasses: JavaClasses) {
        LAYERS
            .whereLayer(MAIN).mayNotBeAccessedByAnyLayer()
            .whereLayer(SERVICE).mayOnlyBeAccessedByLayers(MAIN, SERVICE)
            .whereLayer(IO).mayOnlyBeAccessedByLayers(MAIN)
            .check(importedClasses)
    }
}

const val SERVICE = "Service"
const val IO = "IO"
const val MODEL = "Model"
const val MAIN = "Main"

private val LAYERS = layeredArchitecture()
    .consideringAllDependencies()
    .layer(SERVICE).definedBy("..service..")
    .layer(IO).definedBy("..io..")
    .layer(MODEL).definedBy("..model..")
    .layer(MAIN).definedBy("..application..")
