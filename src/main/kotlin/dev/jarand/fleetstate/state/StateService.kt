package dev.jarand.fleetstate.state

import dev.jarand.fleetstate.depot.repository.DepotRepository
import dev.jarand.fleetstate.state.domain.*
import dev.jarand.fleetstate.state.repository.ParkedVehicleRepository
import dev.jarand.fleetstate.state.repository.StationedVehicleRepository
import dev.jarand.fleetstate.station.repository.StationRepository
import dev.jarand.fleetstate.vehicle.repository.VehicleRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*

@Service
class StateService(
    private val stationedVehicleRepository: StationedVehicleRepository,
    private val parkedVehicleRepository: ParkedVehicleRepository,
    private val stationRepository: StationRepository,
    private val depotRepository: DepotRepository,
    private val vehicleRepository: VehicleRepository
) {

    fun update(update: StationedVehicle) {
        logger.debug("Updating state for stationed vehicle with stationId: ${update.stationId} and vehicleId: ${update.vehicleId}")
        stationedVehicleRepository.saveStationedVehicle(update)
        logger.info("Updated state for stationed vehicle with stationId: ${update.stationId} and vehicleId: ${update.vehicleId}")
    }

    fun revert(update: StationedVehicle) {
        logger.debug("Reverting state for stationed vehicle with stationId: ${update.stationId} and vehicleId: ${update.vehicleId}")
        stationedVehicleRepository.deleteStationedVehicle(update)
        logger.info("Reverted state for stationed vehicle with stationId: ${update.stationId} and vehicleId: ${update.vehicleId}")
    }

    fun update(update: ParkedVehicle) {
        logger.debug("Updating state for parked vehicle with depotId: ${update.depotId} and vehicleId: ${update.vehicleId}")
        parkedVehicleRepository.saveParkedVehicle(update)
        logger.info("Updated state for parked vehicle with depotId: ${update.depotId} and vehicleId: ${update.vehicleId}")
    }

    fun revert(update: ParkedVehicle) {
        logger.debug("Reverting state for parked vehicle with depotId: ${update.depotId} and vehicleId: ${update.vehicleId}")
        parkedVehicleRepository.deleteParkedVehicle(update)
        logger.info("Reverted state for parked vehicle with depotId: ${update.depotId} and vehicleId: ${update.vehicleId}")
    }

    fun getStationedVehicleState(stationId: UUID): StationedVehiclesState? {
        val station = stationRepository.getStation(stationId) ?: return null
        val stationedVehicles = stationedVehicleRepository.getStationedVehicles(station.id).map { it.vehicleId }
        return StationedVehiclesState(
            station.id,
            station.name,
            stationedVehicles.mapNotNull { vehicleRepository.getVehicle(it) }.map { StationedVehicleData(it.id, it.name) }
        )
    }

    fun getParkedVehicleState(depotId: UUID): ParkedVehiclesState? {
        val depot = depotRepository.getDepot(depotId) ?: return null
        val parkedVehicles = parkedVehicleRepository.getParkedVehicles(depot.id).map { it.vehicleId }
        return ParkedVehiclesState(
            depot.id,
            depot.name,
            parkedVehicles.mapNotNull { vehicleRepository.getVehicle(it) }.map { ParkedVehicleData(it.id, it.name) }
        )
    }

    companion object {
        private val logger = LoggerFactory.getLogger(StateService::class.java)
    }
}
