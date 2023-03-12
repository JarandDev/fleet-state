package dev.jarand.fleetstate.transition.rest

import dev.jarand.fleetstate.transition.TransitionService
import dev.jarand.fleetstate.transition.domain.VehicleDepotTransitionAssembler
import dev.jarand.fleetstate.transition.domain.VehicleDepotTransitionType
import dev.jarand.fleetstate.transition.domain.VehicleStationTransitionAssembler
import dev.jarand.fleetstate.transition.domain.VehicleStationTransitionType
import dev.jarand.fleetstate.transition.rest.resource.VehicleDepotTransitionResource
import dev.jarand.fleetstate.transition.rest.resource.VehicleStationTransitionResource
import dev.jarand.fleetstate.transitionstate.TransitionStateLinker
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("transition")
class TransitionController(
    private val vehicleStationTransitionAssembler: VehicleStationTransitionAssembler,
    private val vehicleDepotTransitionAssembler: VehicleDepotTransitionAssembler,
    private val transitionService: TransitionService,
    private val transitionStateLinker: TransitionStateLinker
) {

    @PostMapping("vehicle-to-station")
    fun receiveVehicleToStation(@Valid @RequestBody resource: VehicleStationTransitionResource): ResponseEntity<Any> {
        val transition = vehicleStationTransitionAssembler.assembleNew(resource, VehicleStationTransitionType.TO_STATION)
        transitionService.saveVehicleStationTransition(transition)
        transitionStateLinker.linkVehicleStationTransition(transition)
        return ResponseEntity.accepted().build()
    }

    @PostMapping("vehicle-from-station")
    fun receiveVehicleFromStation(@Valid @RequestBody resource: VehicleStationTransitionResource): ResponseEntity<Any> {
        val transition = vehicleStationTransitionAssembler.assembleNew(resource, VehicleStationTransitionType.FROM_STATION)
        transitionService.saveVehicleStationTransition(transition)
        transitionStateLinker.linkVehicleStationTransition(transition)
        return ResponseEntity.accepted().build()
    }

    @PostMapping("vehicle-to-depot")
    fun receiveVehicleToDepot(@Valid @RequestBody resource: VehicleDepotTransitionResource): ResponseEntity<Any> {
        val transition = vehicleDepotTransitionAssembler.assembleNew(resource, VehicleDepotTransitionType.TO_DEPOT)
        transitionService.saveVehicleDepotTransition(transition)
        transitionStateLinker.linkVehicleDepotTransition(transition)
        return ResponseEntity.accepted().build()
    }

    @PostMapping("vehicle-from-depot")
    fun receiveVehicleFromDepot(@Valid @RequestBody resource: VehicleDepotTransitionResource): ResponseEntity<Any> {
        val transition = vehicleDepotTransitionAssembler.assembleNew(resource, VehicleDepotTransitionType.FROM_DEPOT)
        transitionService.saveVehicleDepotTransition(transition)
        transitionStateLinker.linkVehicleDepotTransition(transition)
        return ResponseEntity.accepted().build()
    }
}
