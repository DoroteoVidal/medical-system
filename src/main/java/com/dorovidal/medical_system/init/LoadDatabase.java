package com.dorovidal.medical_system.init;

import com.dorovidal.medical_system.model.Role;
import com.dorovidal.medical_system.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
@ConditionalOnProperty(
        value="app.dbload.loaddbonrun",
        havingValue="true",
        matchIfMissing=false)
public class LoadDatabase {

    Role admin = new Role(1L, "ADMIN");
    Role user = new Role(2L, "USER");

    @Bean
    CommandLineRunner initRoles(@Qualifier("roleRepository") RoleRepository roleRepository) {
        return args -> {
            log.info("Preloading {}", roleRepository.save(admin));
            log.info("Preloading {}", roleRepository.save(user));
        };
    }

}
