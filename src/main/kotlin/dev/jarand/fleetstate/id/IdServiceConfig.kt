package dev.jarand.fleetstate.id

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
class IdServiceConfig {

    @Bean
    fun uuidSupplier(): () -> UUID {
        return { UUID.randomUUID() }
    }
}
