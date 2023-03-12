package dev.jarand.fleetstate.depot.repository

import dev.jarand.fleetstate.depot.domain.Depot
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import java.time.Instant
import java.util.*

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

    fun getDepot(id: UUID): Depot? {
        try {
            return jdbcTemplate.queryForObject(
                """
                SELECT id, name, created
                FROM depot
                WHERE id = :id
                """.trimIndent(),
                mapOf("id" to id)
            ) { resultSet, _ ->
                Depot(
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
