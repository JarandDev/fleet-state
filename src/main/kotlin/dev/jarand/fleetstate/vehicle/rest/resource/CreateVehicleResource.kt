package dev.jarand.fleetstate.vehicle.rest.resource

import jakarta.validation.constraints.NotBlank

data class CreateVehicleResource(@NotBlank val name: String, @NotBlank val type: String)
