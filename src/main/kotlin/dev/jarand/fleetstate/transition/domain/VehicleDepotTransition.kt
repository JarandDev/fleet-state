package dev.jarand.fleetstate.transition.domain

import java.time.Instant
import java.util.*

data class VehicleDepotTransition(val id: UUID, val vehicleId: UUID, val depotId: UUID, val type: VehicleDepotTransitionType, val created: Instant)
