package dev.jarand.fleetstate.transitionstate

import dev.jarand.fleetstate.state.StateService
import dev.jarand.fleetstate.state.domain.ParkedVehicle
import dev.jarand.fleetstate.state.domain.StationedVehicle
import dev.jarand.fleetstate.transition.domain.VehicleDepotTransition
import dev.jarand.fleetstate.transition.domain.VehicleDepotTransitionType.FROM_DEPOT
import dev.jarand.fleetstate.transition.domain.VehicleDepotTransitionType.TO_DEPOT
import dev.jarand.fleetstate.transition.domain.VehicleStationTransition
import dev.jarand.fleetstate.transition.domain.VehicleStationTransitionType.FROM_STATION
import dev.jarand.fleetstate.transition.domain.VehicleStationTransitionType.TO_STATION
import org.springframework.stereotype.Component

@Component
class TransitionStateLinker(private val stateService: StateService) {

    fun linkVehicleStationTransition(vehicleStationTransition: VehicleStationTransition) {
        when (vehicleStationTransition.type) {
            TO_STATION -> stateService.update(StationedVehicle(vehicleStationTransition.vehicleId, vehicleStationTransition.stationId))
            FROM_STATION -> stateService.revert(StationedVehicle(vehicleStationTransition.vehicleId, vehicleStationTransition.stationId))
        }
    }

    fun linkVehicleDepotTransition(vehicleDepotTransition: VehicleDepotTransition) {
        when (vehicleDepotTransition.type) {
            TO_DEPOT -> stateService.update(ParkedVehicle(vehicleDepotTransition.vehicleId, vehicleDepotTransition.depotId))
            FROM_DEPOT -> stateService.revert(ParkedVehicle(vehicleDepotTransition.vehicleId, vehicleDepotTransition.depotId))
        }
    }
}
