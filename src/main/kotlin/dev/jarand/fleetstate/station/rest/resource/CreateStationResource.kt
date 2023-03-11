package dev.jarand.fleetstate.station.rest.resource

import jakarta.validation.constraints.NotBlank

data class CreateStationResource(@NotBlank val name: String)
