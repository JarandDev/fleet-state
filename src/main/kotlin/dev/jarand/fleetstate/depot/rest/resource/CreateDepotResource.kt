package dev.jarand.fleetstate.depot.rest.resource

import jakarta.validation.constraints.NotBlank

data class CreateDepotResource(@NotBlank val name: String)
