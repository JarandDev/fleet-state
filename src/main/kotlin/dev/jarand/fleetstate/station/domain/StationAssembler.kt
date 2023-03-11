package dev.jarand.fleetstate.station.domain

import dev.jarand.fleetstate.id.IdService
import dev.jarand.fleetstate.station.rest.resource.CreateStationResource
import dev.jarand.fleetstate.time.TimeService
import org.springframework.stereotype.Component

@Component
class StationAssembler(private val idService: IdService, private val timeService: TimeService) {

    fun assembleNew(resource: CreateStationResource): Station {
        return Station(idService.stationId(), resource.name, timeService.stationCreated())
    }
}
