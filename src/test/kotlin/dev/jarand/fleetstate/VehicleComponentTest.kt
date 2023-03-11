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

class VehicleComponentTest : ComponentTest() {

    @MockkBean
    lateinit var idService: IdService

    @MockkBean
    lateinit var timeService: TimeService

    @Autowired
    lateinit var jdbcTemplate: NamedParameterJdbcTemplate

    @Test
    fun `POST tram vehicle should save vehicle in database and return 200 with expected json`() {
        every { idService.vehicleId() } returns UUID.fromString("e5d86d40-58d7-45a9-81ee-4187d6f3e625")
        every { timeService.vehicleCreated() } returns Instant.parse("2023-03-10T21:09:38.973123700Z")

        mockMvc.perform(
            post("/vehicle").contentType(MediaType.APPLICATION_JSON).content(
                """
                {
                  "name": "SL18-401",
                  "type": "TRAM"
                }
                """.trimIndent()
            )
        ).andExpect(status().isOk).andExpect(
            content().json(
                """
                {
                  "id": "e5d86d40-58d7-45a9-81ee-4187d6f3e625",
                  "name": "SL18-401",
                  "type": "TRAM",
                  "created": "2023-03-10T21:09:38.973123700Z"
                }
                """.trimIndent(), true
            )
        )

        val exists = jdbcTemplate.queryForObject(
            "SELECT EXISTS(SELECT * FROM vehicle WHERE id = :id AND name = :name AND type = :type AND created = :created)",
            mapOf(
                "id" to "e5d86d40-58d7-45a9-81ee-4187d6f3e625",
                "name" to "SL18-401",
                "type" to "TRAM",
                "created" to "2023-03-10T21:09:38.973123700Z"
            ),
            Boolean::class.java
        )
        assertThat(exists).isTrue
    }

    @Test
    fun `POST subway vehicle should save vehicle in database and return 200 with expected json`() {
        every { idService.vehicleId() } returns UUID.fromString("7b4016b5-1521-46e6-8f35-50f277560495")
        every { timeService.vehicleCreated() } returns Instant.parse("2023-03-11T08:56:41.237254500Z")

        mockMvc.perform(
            post("/vehicle").contentType(MediaType.APPLICATION_JSON).content(
                """
                {
                  "name": "MX3000-30001",
                  "type": "SUBWAY"
                }
                """.trimIndent()
            )
        ).andExpect(status().isOk).andExpect(
            content().json(
                """
                {
                  "id": "7b4016b5-1521-46e6-8f35-50f277560495",
                  "name": "MX3000-30001",
                  "type": "SUBWAY",
                  "created": "2023-03-11T08:56:41.237254500Z"
                }
                """.trimIndent(), true
            )
        )

        val exists = jdbcTemplate.queryForObject(
            "SELECT EXISTS(SELECT * FROM vehicle WHERE id = :id AND name = :name AND type = :type AND created = :created)",
            mapOf(
                "id" to "7b4016b5-1521-46e6-8f35-50f277560495",
                "name" to "MX3000-30001",
                "type" to "SUBWAY",
                "created" to "2023-03-11T08:56:41.237254500Z"
            ),
            Boolean::class.java
        )
        assertThat(exists).isTrue
    }

    @Test
    fun `POST bus vehicle should save vehicle in database and return 200 with expected json`() {
        every { idService.vehicleId() } returns UUID.fromString("1c9f737b-c1f7-466d-bc55-cff89cbbcdd3")
        every { timeService.vehicleCreated() } returns Instant.parse("2023-03-11T09:03:41.308865300Z")

        mockMvc.perform(
            post("/vehicle").contentType(MediaType.APPLICATION_JSON).content(
                """
                {
                  "name": "SLFA187-3001",
                  "type": "BUS"
                }
                """.trimIndent()
            )
        ).andExpect(status().isOk).andExpect(
            content().json(
                """
                {
                  "id": "1c9f737b-c1f7-466d-bc55-cff89cbbcdd3",
                  "name": "SLFA187-3001",
                  "type": "BUS",
                  "created": "2023-03-11T09:03:41.308865300Z"
                }
                """.trimIndent(), true
            )
        )

        val exists = jdbcTemplate.queryForObject(
            "SELECT EXISTS(SELECT * FROM vehicle WHERE id = :id AND name = :name AND type = :type AND created = :created)",
            mapOf(
                "id" to "1c9f737b-c1f7-466d-bc55-cff89cbbcdd3",
                "name" to "SLFA187-3001",
                "type" to "BUS",
                "created" to "2023-03-11T09:03:41.308865300Z"
            ),
            Boolean::class.java
        )
        assertThat(exists).isTrue
    }
}
