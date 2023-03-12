package dev.jarand.fleetstate.vehicle.repository

import dev.jarand.fleetstate.vehicle.domain.Vehicle
import dev.jarand.fleetstate.vehicle.domain.VehicleType
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import java.time.Instant
import java.util.*

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

    fun getVehicle(id: UUID): Vehicle? {
        try {
            return jdbcTemplate.queryForObject(
                """
                SELECT id, name, type, created
                FROM vehicle
                WHERE id = :id
                """.trimIndent(),
                mapOf("id" to id)
            ) { resultSet, _ ->
                Vehicle(
                    resultSet.getObject("id", UUID::class.java),
                    resultSet.getString("name"),
                    VehicleType.valueOf(resultSet.getString("type")),
                    Instant.parse(resultSet.getString("created"))
                )
            }
        } catch (ex: EmptyResultDataAccessException) {
            return null
        }
    }
}
