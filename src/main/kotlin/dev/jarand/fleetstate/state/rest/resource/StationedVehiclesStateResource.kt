package dev.jarand.fleetstate.state.rest.resource

data class StationedVehiclesStateResource(val stationId: String, val stationName: String, val vehicles: List<StationedVehicleDataResource>)

data class StationedVehicleDataResource(val vehicleId: String, val name: String)
