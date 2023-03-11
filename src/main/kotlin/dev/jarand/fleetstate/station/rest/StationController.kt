package dev.jarand.fleetstate.station.rest

import dev.jarand.fleetstate.station.StationService
import dev.jarand.fleetstate.station.domain.StationAssembler
import dev.jarand.fleetstate.station.rest.resource.CreateStationResource
import dev.jarand.fleetstate.station.rest.resource.StationResource
import dev.jarand.fleetstate.station.rest.resource.StationResourceAssembler
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("station")
class StationController(private val stationAssembler: StationAssembler, private val stationService: StationService, private val stationResourceAssembler: StationResourceAssembler) {

    @PostMapping
    fun createStation(@Valid @RequestBody resource: CreateStationResource): ResponseEntity<StationResource> {
        val station = stationAssembler.assembleNew(resource)
        stationService.saveStation(station)
        return ResponseEntity.ok(stationResourceAssembler.assemble(station))
    }
}
