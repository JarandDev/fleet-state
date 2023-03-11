package dev.jarand.fleetstate

import com.ninjasquad.springmockk.MockkBean
import dev.jarand.fleetstate.config.ComponentTest
import dev.jarand.fleetstate.id.IdService
import dev.jarand.fleetstate.time.TimeService
import io.mockk.every
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.Instant
import java.util.*

class StationComponentTest : ComponentTest() {

    @MockkBean
    lateinit var idService: IdService

    @MockkBean
    lateinit var timeService: TimeService

    @Autowired
    lateinit var jdbcTemplate: NamedParameterJdbcTemplate

    @Test
    fun `POST station should save station in database and return 200 with expected json`() {
        every { idService.stationId() } returns UUID.fromString("a2cc8f28-8825-42eb-a36b-9b6fe8535a0f")
        every { timeService.stationCreated() } returns Instant.parse("2023-03-11T08:45:26.998487800Z")

        mockMvc.perform(
            post("/station").contentType(MediaType.APPLICATION_JSON).content(
                """
                {
                  "name": "Oslo Sentralstasjon"
                }
                """.trimIndent()
            )
        ).andExpect(status().isOk).andExpect(
            content().json(
                """
                {
                  "id": "a2cc8f28-8825-42eb-a36b-9b6fe8535a0f",
                  "name": "Oslo Sentralstasjon",
                  "created": "2023-03-11T08:45:26.998487800Z"
                }
                """.trimIndent(), true
            )
        )

        val exists = jdbcTemplate.queryForObject(
            "SELECT EXISTS(SELECT * FROM station WHERE id = :id AND name = :name AND created = :created)",
            mapOf(
                "id" to "a2cc8f28-8825-42eb-a36b-9b6fe8535a0f",
                "name" to "Oslo Sentralstasjon",
                "created" to "2023-03-11T08:45:26.998487800Z"
            ),
            Boolean::class.java
        )
        assertThat(exists).isTrue
    }
}
