package dev.jarand.fleetstate.vehicle.domain

import dev.jarand.fleetstate.id.IdService
import dev.jarand.fleetstate.time.TimeService
import dev.jarand.fleetstate.vehicle.rest.resource.CreateVehicleResource
import org.springframework.stereotype.Component

@Component
class VehicleAssembler(private val idService: IdService, private val timeService: TimeService) {

    fun assembleNew(resource: CreateVehicleResource): Vehicle {
        return Vehicle(idService.vehicleId(), resource.name, timeService.vehicleCreated())
    }
}
