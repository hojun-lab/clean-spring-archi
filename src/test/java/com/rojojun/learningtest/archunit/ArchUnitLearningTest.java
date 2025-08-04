package com.rojojun.learningtest.archunit;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = "com.rojojun.learningtest.archunit")
public class ArchUnitLearningTest {
    /**
     * Application 클래스를 의존하는 클래스는 application, adapter에만 존재해야 한다.
     */
    @ArchTest
    void application(JavaClasses classes) {
        classes().that().resideInAPackage("..application..")
                .should().onlyHaveDependentClassesThat().resideInAnyPackage("..application..", "..adapter..")
                .check(classes);
    }

    /**
     * Application 클래스는 adpater의 클래스를 의존하면 안된다
     */
    @ArchTest
    void adapterDependencyViolation(JavaClasses classes) {
        noClasses().that().resideInAPackage("..application..")
                .should().dependOnClassesThat().resideInAnyPackage("..adapter..")
                .check(classes);
    }

    /**
     * Domain의 클래스는 domain, java
     */
    @ArchTest
    void domainPermission(JavaClasses classes) {
        classes().that().resideInAnyPackage("..domain..")
                .should().onlyDependOnClassesThat().resideInAnyPackage("..domain..", "..java...")
                .check(classes);
    }
}
