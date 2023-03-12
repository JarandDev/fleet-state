package dev.jarand.fleetstate.state.repository

import dev.jarand.fleetstate.state.domain.StationedVehicle
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class StationedVehicleRepository(private val jdbcTemplate: NamedParameterJdbcTemplate) {

    fun saveStationedVehicle(stationedVehicle: StationedVehicle) {
        jdbcTemplate.update(
            """
            INSERT INTO stationed_vehicle (station_id, vehicle_id)
            VALUES (:station_id, :vehicle_id)
            """.trimIndent(),
            mapOf(
                "station_id" to stationedVehicle.stationId,
                "vehicle_id" to stationedVehicle.vehicleId
            )
        )
    }

    fun deleteStationedVehicle(stationedVehicle: StationedVehicle) {
        jdbcTemplate.update(
            """
            DELETE FROM stationed_vehicle
            WHERE station_id = :station_id AND vehicle_id = :vehicle_id
            """.trimIndent(),
            mapOf(
                "station_id" to stationedVehicle.stationId,
                "vehicle_id" to stationedVehicle.vehicleId
            )
        )
    }

    fun getStationedVehicles(stationId: UUID): List<StationedVehicle> {
        return jdbcTemplate.query(
            """
                SELECT station_id, vehicle_id
                FROM stationed_vehicle
                WHERE station_id = :station_id
                """.trimIndent(),
            mapOf("station_id" to stationId)
        ) { resultSet, _ ->
            StationedVehicle(
                resultSet.getObject("vehicle_id", UUID::class.java),
                resultSet.getObject("station_id", UUID::class.java)
            )
        }
    }
}
