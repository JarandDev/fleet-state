package dev.jarand.fleetstate.state.rest.resource

import dev.jarand.fleetstate.state.domain.ParkedVehiclesState
import org.springframework.stereotype.Component

@Component
class ParkedVehiclesStateResourceAssembler {

    fun assemble(parkedVehiclesState: ParkedVehiclesState): ParkedVehiclesStateResource {
        return ParkedVehiclesStateResource(
            parkedVehiclesState.depotId.toString(),
            parkedVehiclesState.depotName,
            parkedVehiclesState.vehicles.map { ParkedVehicleDataResource(it.vehicleId.toString(), it.name) }
        )
    }
}
