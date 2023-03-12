package dev.jarand.fleetstate.state.rest.resource

import dev.jarand.fleetstate.state.domain.StationedVehiclesState
import org.springframework.stereotype.Component

@Component
class StationedVehiclesStateResourceAssembler {

    fun assemble(stationedVehiclesState: StationedVehiclesState): StationedVehiclesStateResource {
        return StationedVehiclesStateResource(
            stationedVehiclesState.stationId.toString(),
            stationedVehiclesState.stationName,
            stationedVehiclesState.vehicles.map { StationedVehicleDataResource(it.vehicleId.toString(), it.name) }
        )
    }
}
