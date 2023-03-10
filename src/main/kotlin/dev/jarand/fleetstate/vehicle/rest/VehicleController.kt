package dev.jarand.fleetstate.vehicle.rest

import dev.jarand.fleetstate.vehicle.VehicleService
import dev.jarand.fleetstate.vehicle.domain.VehicleAssembler
import dev.jarand.fleetstate.vehicle.rest.resource.CreateVehicleResource
import dev.jarand.fleetstate.vehicle.rest.resource.VehicleResource
import dev.jarand.fleetstate.vehicle.rest.resource.VehicleResourceAssembler
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("vehicle")
class VehicleController(
        private val vehicleAssembler: VehicleAssembler,
        private val vehicleService: VehicleService,
        private val vehicleResourceAssembler: VehicleResourceAssembler
) {

    @PostMapping
    fun createVehicle(@Valid @RequestBody resource: CreateVehicleResource): ResponseEntity<VehicleResource> {
        val vehicle = vehicleAssembler.assembleNew(resource)
        vehicleService.saveVehicle(vehicle)
        return ResponseEntity.ok(vehicleResourceAssembler.assemble(vehicle))
    }
}
