package dev.jarand.fleetstate.transition.domain

import java.time.Instant
import java.util.*

data class VehicleStationTransition(val id: UUID, val vehicleId: UUID, val stationId: UUID, val type: VehicleStationTransitionType, val created: Instant)
