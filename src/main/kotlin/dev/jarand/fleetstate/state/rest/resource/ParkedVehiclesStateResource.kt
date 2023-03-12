package dev.jarand.fleetstate.state.rest.resource

data class ParkedVehiclesStateResource(val depotId: String, val depotName: String, val vehicles: List<ParkedVehicleDataResource>)

data class ParkedVehicleDataResource(val vehicleId: String, val name: String)
