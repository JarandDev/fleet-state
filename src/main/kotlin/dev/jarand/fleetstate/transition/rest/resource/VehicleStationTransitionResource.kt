package dev.jarand.fleetstate.transition.rest.resource

import jakarta.validation.constraints.NotNull

data class VehicleStationTransitionResource(@NotNull val vehicleId: String, @NotNull val stationId: String)
