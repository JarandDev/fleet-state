package dev.jarand.fleetstate.transition.rest.resource

import jakarta.validation.constraints.NotNull

data class VehicleDepotTransitionResource(@NotNull val vehicleId: String, @NotNull val depotId: String)
