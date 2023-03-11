package dev.jarand.fleetstate.depot.repository

import dev.jarand.fleetstate.depot.domain.Depot
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class DepotRepository(private val jdbcTemplate: NamedParameterJdbcTemplate) {

    fun saveDepot(depot: Depot) {
        jdbcTemplate.update(
            """
            INSERT INTO depot (id, name, created)
            VALUES (:id, :name, :created)
            """.trimIndent(),
            mapOf(
                "id" to depot.id,
                "name" to depot.name,
                "created" to depot.created.toString()
            )
        )
    }
}
