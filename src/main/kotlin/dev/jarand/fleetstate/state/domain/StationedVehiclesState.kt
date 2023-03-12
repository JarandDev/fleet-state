package dev.jarand.fleetstate.state.domain

import java.util.*

data class StationedVehiclesState(val stationId: UUID, val stationName: String, val vehicles: List<StationedVehicleData>)

data class StationedVehicleData(val vehicleId: UUID, val name: String)
