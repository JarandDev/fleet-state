package dev.jarand.fleetstate.depot

import dev.jarand.fleetstate.depot.domain.Depot
import dev.jarand.fleetstate.depot.repository.DepotRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class DepotService(private val depotRepository: DepotRepository) {

    fun saveDepot(depot: Depot) {
        logger.debug("Saving depot with id: ${depot.id}")
        depotRepository.saveDepot(depot)
        logger.info("Saved depot with id: ${depot.id}")
    }

    companion object {
        private val logger = LoggerFactory.getLogger(DepotService::class.java)
    }
}
