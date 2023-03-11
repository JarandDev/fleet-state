package dev.jarand.fleetstate.station.rest.resource

import dev.jarand.fleetstate.station.domain.Station
import org.springframework.stereotype.Component

@Component
class StationResourceAssembler {

    fun assemble(station: Station): StationResource {
        return StationResource(station.id.toString(), station.name, station.created.toString())
    }
}
