package dev.jarand.fleetstate.station.repository

import dev.jarand.fleetstate.station.domain.Station
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import java.time.Instant
import java.util.*

@Repository
class StationRepository(private val jdbcTemplate: NamedParameterJdbcTemplate) {

    fun saveStation(station: Station) {
        jdbcTemplate.update(
            """
            INSERT INTO station (id, name, created)
            VALUES (:id, :name, :created)
            """.trimIndent(),
            mapOf(
                "id" to station.id,
                "name" to station.name,
                "created" to station.created.toString()
            )
        )
    }

    fun getStation(id: UUID): Station? {
        try {
            return jdbcTemplate.queryForObject(
                """
                SELECT id, name, created
                FROM station
                WHERE id = :id
                """.trimIndent(),
                mapOf("id" to id)
            ) { resultSet, _ ->
                Station(
                    resultSet.getObject("id", UUID::class.java),
                    resultSet.getString("name"),
                    Instant.parse(resultSet.getString("created"))
                )
            }
        } catch (ex: EmptyResultDataAccessException) {
            return null
        }
    }
}
