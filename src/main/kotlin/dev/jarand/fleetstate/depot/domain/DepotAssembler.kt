package dev.jarand.fleetstate.depot.domain

import dev.jarand.fleetstate.depot.rest.resource.CreateDepotResource
import dev.jarand.fleetstate.id.IdService
import dev.jarand.fleetstate.time.TimeService
import org.springframework.stereotype.Component

@Component
class DepotAssembler(private val idService: IdService, private val timeService: TimeService) {

    fun assembleNew(resource: CreateDepotResource): Depot {
        return Depot(idService.depotId(), resource.name, timeService.depotCreated())
    }
}
