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

//    @ArchTest
//    fun `concrete service components should only be accessed in the application package`(importedClasses: JavaClasses) {
//        classes()
//            .that().resideInAPackage("..service.impl..")
//            .should().onlyBeAccessed().byAnyPackage("..application..")
//            .check(importedClasses)
//    }

    @ArchTest
    fun `io should not dependent on other io`(importedClasses: JavaClasses) {
        noClasses().that().resideInAPackage("..io.impl..")
            .should().dependOnClassesThat().resideInAPackage("..io.impl..")
            .check(importedClasses)
    }

    @ArchTest
    fun `ensure that architecture layers are not mixed`(importedClasses: JavaClasses) {
        LAYERS
            .whereLayer(APPLICATION).mayNotBeAccessedByAnyLayer()
            .whereLayer(SERVICE).mayOnlyBeAccessedByLayers(APPLICATION, SERVICE)
            .whereLayer(IO).mayOnlyBeAccessedByLayers(APPLICATION, SERVICE)
            .whereLayer(IO_IMPL).mayOnlyBeAccessedByLayers(APPLICATION)
            .whereLayer(SERVICE_IMPL).mayOnlyBeAccessedByLayers(APPLICATION)
            .check(importedClasses)
    }
}

const val SERVICE = "Service"
const val SERVICE_IMPL = "ServiceImpl"
const val IO = "IO"
const val IO_IMPL = "IOImpl"
const val MODEL = "Model"
const val APPLICATION = "Application"

private val LAYERS = layeredArchitecture()
    .consideringAllDependencies()
    .layer(SERVICE).definedBy("..service..")
    .layer(SERVICE_IMPL).definedBy("..service.impl..")
    .layer(IO).definedBy("..io..")
    .layer(IO_IMPL).definedBy("..io.impl..")
    .layer(MODEL).definedBy("..model..")
    .layer(APPLICATION).definedBy("..application..")
