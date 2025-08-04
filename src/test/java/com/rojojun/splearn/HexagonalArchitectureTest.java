package com.rojojun.splearn;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.library.Architectures;

@AnalyzeClasses(packages = "com.rojojun.splearn", importOptions = ImportOption.OnlyIncludeTests.class)
public class HexagonalArchitectureTest {

    @ArchTest
    void hexagonalArchitecture(JavaClasses classes) {
        Architectures.layeredArchitecture()
                .consideringAllDependencies()
                .layer("domain").definedBy("com.rojojun.splearn.domain..")
                .layer("application").definedBy("com.rojojun.splearn.application..")
                .layer("adapter").definedBy("com.rojojun.splearn.adapter..")
                .whereLayer("domain").mayOnlyBeAccessedByLayers("adapter")
                .whereLayer("adapter").mayNotBeAccessedByAnyLayer()
                .check(classes);
    }
}
