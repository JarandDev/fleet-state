package dev.jarand.fleetstate.state.domain

import java.util.*

data class ParkedVehiclesState(val depotId: UUID, val depotName: String, val vehicles: List<ParkedVehicleData>)

data class ParkedVehicleData(val vehicleId: UUID, val name: String)
