package dev.jarand.fleetstate.station.repository

import dev.jarand.fleetstate.station.domain.Station
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository

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
}
