package dev.jarand.fleetstate.transition.domain

import dev.jarand.fleetstate.id.IdService
import dev.jarand.fleetstate.time.TimeService
import dev.jarand.fleetstate.transition.rest.resource.VehicleStationTransitionResource
import org.springframework.stereotype.Component
import java.util.*

@Component
class VehicleStationTransitionAssembler(private val idService: IdService, private val timeService: TimeService) {

    fun assembleNew(resource: VehicleStationTransitionResource, type: VehicleStationTransitionType): VehicleStationTransition {
        return VehicleStationTransition(idService.transitionId(), UUID.fromString(resource.vehicleId), UUID.fromString(resource.stationId), type, timeService.transitionCreated())
    }
}
