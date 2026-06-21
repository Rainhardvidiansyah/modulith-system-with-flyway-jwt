package com.rainhard.modulith.system.modules;

import com.rainhard.modulith.system.RainhardModulithApplication;
import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

public class ArchitectureTest {

    @Test
    void shouldVerifyModuleStructure(){
        // Tanpa @SpringBootTest, ini berjalan sangat cepat!
        ApplicationModules modules = ApplicationModules.of(RainhardModulithApplication.class);
        modules.verify();
    }

    @Test
    void createDocumentation(){
        ApplicationModules modules = ApplicationModules.of(RainhardModulithApplication.class);
        new Documenter(modules).writeDocumentation();
    }

    @Test
    void ApplicationModuleTestViolation(){
//        ApplicationModules modules =
        ApplicationModules.of(RainhardModulithApplication.class)
                .detectViolations()
                .filter(violation -> violation.hasMessageContaining("user"))
                .throwIfPresent();


//        modules.verify();
    }
}
