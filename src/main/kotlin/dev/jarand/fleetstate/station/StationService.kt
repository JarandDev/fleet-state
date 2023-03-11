package dev.jarand.fleetstate.station

import dev.jarand.fleetstate.station.domain.Station
import dev.jarand.fleetstate.station.repository.StationRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class StationService(private val stationRepository: StationRepository) {

    fun saveStation(station: Station) {
        logger.debug("Saving station with id: ${station.id}")
        stationRepository.saveStation(station)
        logger.info("Saved station with id: ${station.id}")
    }

    companion object {
        private val logger = LoggerFactory.getLogger(StationService::class.java)
    }
}
