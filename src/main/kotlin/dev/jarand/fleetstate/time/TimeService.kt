package dev.jarand.fleetstate.time

import org.springframework.stereotype.Service
import java.time.Instant

@Service
class TimeService(private val instantSupplier: () -> Instant) {

    fun vehicleCreated(): Instant {
        return instantSupplier.invoke()
    }
}