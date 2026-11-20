package kz.kaznaru.capstone;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

/** Dependency rules are tests: breaking the architecture breaks the build. */
@AnalyzeClasses(packages = "kz.kaznaru.capstone")
class ArchitectureTest {

    @ArchTest
    static final ArchRule domain_depends_on_nothing_else =
        noClasses().that().resideInAPackage("..domain..")
            .should().dependOnClassesThat().resideInAnyPackage("..service..", "..infra..", "..ui..");

    @ArchTest
    static final ArchRule service_does_not_know_infra_or_ui =
        noClasses().that().resideInAPackage("..service..")
            .should().dependOnClassesThat().resideInAnyPackage("..infra..", "..ui..");

    @ArchTest
    static final ArchRule ui_does_not_know_infra =
        noClasses().that().resideInAPackage("..ui..")
            .should().dependOnClassesThat().resideInAPackage("..infra..");
}
