package dev.jarand.fleetstate.depot.rest

import dev.jarand.fleetstate.depot.DepotService
import dev.jarand.fleetstate.depot.domain.DepotAssembler
import dev.jarand.fleetstate.depot.rest.resource.CreateDepotResource
import dev.jarand.fleetstate.depot.rest.resource.DepotResource
import dev.jarand.fleetstate.depot.rest.resource.DepotResourceAssembler
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("depot")
class DepotController(private val depotAssembler: DepotAssembler, private val depotService: DepotService, private val depotResourceAssembler: DepotResourceAssembler) {

    @PostMapping
    fun createDepot(@Valid @RequestBody resource: CreateDepotResource): ResponseEntity<DepotResource> {
        val depot = depotAssembler.assembleNew(resource)
        depotService.saveDepot(depot)
        return ResponseEntity.ok(depotResourceAssembler.assemble(depot))
    }
}
