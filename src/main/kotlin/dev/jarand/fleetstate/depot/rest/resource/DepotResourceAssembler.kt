package dev.jarand.fleetstate.depot.rest.resource

import dev.jarand.fleetstate.depot.domain.Depot
import org.springframework.stereotype.Component

@Component
class DepotResourceAssembler {

    fun assemble(depot: Depot): DepotResource {
        return DepotResource(depot.id.toString(), depot.name, depot.created.toString())
    }
}
