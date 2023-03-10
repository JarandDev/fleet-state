package dev.jarand.fleetstate.vehicle

import dev.jarand.fleetstate.vehicle.domain.Vehicle
import dev.jarand.fleetstate.vehicle.repository.VehicleRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class VehicleService(private val vehicleRepository: VehicleRepository) {

    fun saveVehicle(vehicle: Vehicle) {
        logger.debug("Saving vehicle with id: ${vehicle.id}")
        vehicleRepository.saveVehicle(vehicle)
        logger.info("Saved vehicle with id: ${vehicle.id}")
    }

    companion object {
        private val logger = LoggerFactory.getLogger(VehicleService::class.java)
    }
}
