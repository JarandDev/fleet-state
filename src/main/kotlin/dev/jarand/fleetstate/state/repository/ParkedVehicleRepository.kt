package dev.jarand.fleetstate.state.repository

import dev.jarand.fleetstate.state.domain.ParkedVehicle
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class ParkedVehicleRepository(private val jdbcTemplate: NamedParameterJdbcTemplate) {

    fun saveParkedVehicle(parkedVehicle: ParkedVehicle) {
        jdbcTemplate.update(
            """
            INSERT INTO parked_vehicle (depot_id, vehicle_id)
            VALUES (:depot_id, :vehicle_id)
            """.trimIndent(),
            mapOf(
                "depot_id" to parkedVehicle.depotId,
                "vehicle_id" to parkedVehicle.vehicleId
            )
        )
    }

    fun deleteParkedVehicle(parkedVehicle: ParkedVehicle) {
        jdbcTemplate.update(
            """
            DELETE FROM parked_vehicle
            WHERE depot_id = :depot_id AND vehicle_id = :vehicle_id
            """.trimIndent(),
            mapOf(
                "depot_id" to parkedVehicle.depotId,
                "vehicle_id" to parkedVehicle.vehicleId
            )
        )
    }

    fun getParkedVehicles(depotId: UUID): List<ParkedVehicle> {
        return jdbcTemplate.query(
            """
                SELECT depot_id, vehicle_id
                FROM parked_vehicle
                WHERE depot_id = :depot_id
                """.trimIndent(),
            mapOf("depot_id" to depotId)
        ) { resultSet, _ ->
            ParkedVehicle(
                resultSet.getObject("vehicle_id", UUID::class.java),
                resultSet.getObject("depot_id", UUID::class.java)
            )
        }
    }
}
