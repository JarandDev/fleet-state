package dev.jarand.fleetstate.state.rest

import dev.jarand.fleetstate.state.StateService
import dev.jarand.fleetstate.state.rest.resource.ParkedVehiclesStateResource
import dev.jarand.fleetstate.state.rest.resource.ParkedVehiclesStateResourceAssembler
import dev.jarand.fleetstate.state.rest.resource.StationedVehiclesStateResource
import dev.jarand.fleetstate.state.rest.resource.StationedVehiclesStateResourceAssembler
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("state")
class StateController(
    private val stateService: StateService,
    private val stationedVehiclesStateResourceAssembler: StationedVehiclesStateResourceAssembler,
    private val parkedVehiclesStateResourceAssembler: ParkedVehiclesStateResourceAssembler
) {

    @GetMapping("stationed-vehicles/{stationId}")
    fun getStationedVehiclesState(@PathVariable stationId: UUID): ResponseEntity<StationedVehiclesStateResource> {
        val stationedVehiclesState = stateService.getStationedVehicleState(stationId) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(stationedVehiclesStateResourceAssembler.assemble(stationedVehiclesState))
    }

    @GetMapping("parked-vehicles/{depotId}")
    fun getParkedVehiclesState(@PathVariable depotId: UUID): ResponseEntity<ParkedVehiclesStateResource> {
        val parkedVehiclesState = stateService.getParkedVehicleState(depotId) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(parkedVehiclesStateResourceAssembler.assemble(parkedVehiclesState))
    }
}
