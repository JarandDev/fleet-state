package dev.jarand.fleetstate.id

import org.springframework.stereotype.Service
import java.util.*

@Service
class IdService(private val uuidSupplier: () -> UUID) {

    fun vehicleId(): UUID {
        return uuidSupplier.invoke()
    }
}
