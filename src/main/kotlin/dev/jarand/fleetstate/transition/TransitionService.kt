package dev.jarand.fleetstate.transition

import dev.jarand.fleetstate.transition.domain.VehicleDepotTransition
import dev.jarand.fleetstate.transition.domain.VehicleStationTransition
import dev.jarand.fleetstate.transition.repository.TransitionRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class TransitionService(private val transitionRepository: TransitionRepository) {

    fun saveVehicleStationTransition(transition: VehicleStationTransition) {
        logger.debug("Saving vehicle station transition with id: ${transition.id}")
        transitionRepository.saveVehicleStationTransition(transition)
        logger.info("Saved vehicle station transition with id: ${transition.id}")
    }

    fun saveVehicleDepotTransition(transition: VehicleDepotTransition) {
        logger.debug("Saving vehicle depot transition with id: ${transition.id}")
        transitionRepository.saveVehicleDepotTransition(transition)
        logger.info("Saved vehicle depot transition with id: ${transition.id}")
    }

    companion object {
        private val logger = LoggerFactory.getLogger(TransitionService::class.java)
    }
}
