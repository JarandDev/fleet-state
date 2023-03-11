package dev.jarand.fleetstate.vehicle.repository

import dev.jarand.fleetstate.vehicle.domain.Vehicle
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class VehicleRepository(private val jdbcTemplate: NamedParameterJdbcTemplate) {

    fun saveVehicle(vehicle: Vehicle) {
        jdbcTemplate.update(
            """
            INSERT INTO vehicle (id, name, type, created)
            VALUES (:id, :name, :type, :created)
            """.trimIndent(),
            mapOf(
                "id" to vehicle.id,
                "name" to vehicle.name,
                "type" to vehicle.type.name,
                "created" to vehicle.created.toString()
            )
        )
    }
}
