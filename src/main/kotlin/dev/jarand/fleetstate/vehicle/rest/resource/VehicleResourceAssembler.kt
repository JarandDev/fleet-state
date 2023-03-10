package dev.jarand.fleetstate.vehicle.rest.resource

import dev.jarand.fleetstate.vehicle.domain.Vehicle
import org.springframework.stereotype.Component

@Component
class VehicleResourceAssembler {

    fun assemble(vehicle: Vehicle): VehicleResource {
        return VehicleResource(vehicle.id.toString(), vehicle.name, vehicle.type.name, vehicle.created.toString())
    }
}
