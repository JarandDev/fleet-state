package dev.jarand.fleetstate.transition.repository

import dev.jarand.fleetstate.transition.domain.VehicleDepotTransition
import dev.jarand.fleetstate.transition.domain.VehicleStationTransition
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class TransitionRepository(private val jdbcTemplate: NamedParameterJdbcTemplate) {

    fun saveVehicleStationTransition(transition: VehicleStationTransition) {
        jdbcTemplate.update(
            """
            INSERT INTO vehicle_station_transition (id, vehicle_id, station_id, type, created)
            VALUES (:id, :vehicle_id, :station_id, :type, :created)
            """.trimIndent(),
            mapOf(
                "id" to transition.id,
                "vehicle_id" to transition.vehicleId,
                "station_id" to transition.stationId,
                "type" to transition.type.name,
                "created" to transition.created.toString()
            )
        )
    }

    fun saveVehicleDepotTransition(transition: VehicleDepotTransition) {
        jdbcTemplate.update(
            """
            INSERT INTO vehicle_depot_transition (id, vehicle_id, depot_id, type, created)
            VALUES (:id, :vehicle_id, :depot_id, :type, :created)
            """.trimIndent(),
            mapOf(
                "id" to transition.id,
                "vehicle_id" to transition.vehicleId,
                "depot_id" to transition.depotId,
                "type" to transition.type.name,
                "created" to transition.created.toString()
            )
        )
    }
}
