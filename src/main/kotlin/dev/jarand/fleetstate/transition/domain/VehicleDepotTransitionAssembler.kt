package dev.jarand.fleetstate.transition.domain

import dev.jarand.fleetstate.id.IdService
import dev.jarand.fleetstate.time.TimeService
import dev.jarand.fleetstate.transition.rest.resource.VehicleDepotTransitionResource
import org.springframework.stereotype.Component
import java.util.*

@Component
class VehicleDepotTransitionAssembler(private val idService: IdService, private val timeService: TimeService) {

    fun assembleNew(resource: VehicleDepotTransitionResource, type: VehicleDepotTransitionType): VehicleDepotTransition {
        return VehicleDepotTransition(idService.transitionId(), UUID.fromString(resource.vehicleId), UUID.fromString(resource.depotId), type, timeService.transitionCreated())
    }
}
