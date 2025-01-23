package com.github.hemeroc.publicspeaking.archtest

import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes


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
        IO_ACCESS_RULE.check(importedClasses)
    }

    @ArchTest
    fun `io should not dependent on other io`(importedClasses: JavaClasses) {
        classes().that().resideInAPackage("..io..")
            .should().onlyBeAccessed().byClassesThat().resideOutsideOfPackage("..io..")
    }
}

private val IO_ACCESS_RULE = classes()
    .that().resideInAPackage("..io.impl..")
    .should().onlyBeAccessed().byAnyPackage("..application..")
