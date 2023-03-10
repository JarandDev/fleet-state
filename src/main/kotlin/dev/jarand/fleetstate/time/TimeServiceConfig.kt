package dev.jarand.fleetstate.time

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Instant

@Configuration
class TimeServiceConfig {

    @Bean
    fun instantSupplier(): () -> Instant {
        return { Instant.now() }
    }
}
