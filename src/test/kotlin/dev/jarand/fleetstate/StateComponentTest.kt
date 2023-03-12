package dev.jarand.fleetstate

import com.ninjasquad.springmockk.MockkBean
import dev.jarand.fleetstate.config.ComponentTest
import dev.jarand.fleetstate.id.IdService
import dev.jarand.fleetstate.time.TimeService
import dev.jarand.fleetstate.vehicle.domain.VehicleType
import io.mockk.every
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*

@Transactional
class StateComponentTest : ComponentTest() {

    @MockkBean
    lateinit var idService: IdService

    @MockkBean
    lateinit var timeService: TimeService

    @Autowired
    lateinit var jdbcTemplate: NamedParameterJdbcTemplate

    @BeforeEach
    fun setup() {
        jdbcTemplate.update(
            "INSERT INTO vehicle (id, name, type, created) VALUES (:id, :name, :type, :created)",
            mapOf(
                "id" to UUID.fromString("75920a8f-03ad-4479-bf32-424e949ea051"),
                "name" to "SLFA187-3001",
                "type" to VehicleType.BUS.name,
                "created" to Instant.parse("2023-03-12T09:12:03.567356300Z").toString()
            )
        )
        jdbcTemplate.update(
            "INSERT INTO station (id, name, created) VALUES (:id, :name, :created)",
            mapOf(
                "id" to UUID.fromString("50409f81-2bac-49ce-b238-1b880de6b23e"),
                "name" to "Oslo Sentralstasjon",
                "created" to Instant.parse("2023-03-12T09:12:06.577521Z").toString()
            )
        )
        jdbcTemplate.update(
            "INSERT INTO depot (id, name, created) VALUES (:id, :name, :created)",
            mapOf(
                "id" to UUID.fromString("ec1169d3-1954-4414-b966-9cb74639fc7c"),
                "name" to "BRYN Hall A",
                "created" to Instant.parse("2023-03-12T09:12:09.588554400Z").toString()
            )
        )
    }

    @Test
    fun `POST vehicle station transitions should update state`() {
        every { idService.transitionId() } returns
                UUID.fromString("e56fe5da-2e6d-4ea7-9ca0-7a0fa655608f") andThen
                UUID.fromString("d2ae46d1-960d-40c9-855b-5346c87a7d54")
        every { timeService.transitionCreated() } returns
                Instant.parse("2023-03-12T09:12:12.599031200Z") andThen
                Instant.parse("2023-03-12T09:24:36.786390700Z")

        mockMvc.perform(
            post("/transition/vehicle-to-station").contentType(MediaType.APPLICATION_JSON).content(
                """
                {
                  "vehicleId": "75920a8f-03ad-4479-bf32-424e949ea051",
                  "stationId": "50409f81-2bac-49ce-b238-1b880de6b23e"
                }
                """.trimIndent()
            )
        ).andExpect(status().isAccepted)

        mockMvc.perform(
            get("/state/stationed-vehicles/50409f81-2bac-49ce-b238-1b880de6b23e")
        ).andExpect(status().isOk).andExpect(
            content().json(
                """
                {
                  "stationId": "50409f81-2bac-49ce-b238-1b880de6b23e",
                  "stationName": "Oslo Sentralstasjon",
                  "vehicles": [
                    {
                      "vehicleId": "75920a8f-03ad-4479-bf32-424e949ea051",
                      "name": "SLFA187-3001"
                    }
                  ]
                }
                """.trimIndent(), true
            )
        )

        mockMvc.perform(
            post("/transition/vehicle-from-station").contentType(MediaType.APPLICATION_JSON).content(
                """
                {
                  "vehicleId": "75920a8f-03ad-4479-bf32-424e949ea051",
                  "stationId": "50409f81-2bac-49ce-b238-1b880de6b23e"
                }
                """.trimIndent()
            )
        ).andExpect(status().isAccepted)

        mockMvc.perform(
            get("/state/stationed-vehicles/50409f81-2bac-49ce-b238-1b880de6b23e")
        ).andExpect(status().isOk).andExpect(
            content().json(
                """
                {
                  "stationId": "50409f81-2bac-49ce-b238-1b880de6b23e",
                  "stationName": "Oslo Sentralstasjon",
                  "vehicles": []
                }
                """.trimIndent(), true
            )
        )
    }

    @Test
    fun `POST vehicle depot transitions should update state`() {
        every { idService.transitionId() } returns
                UUID.fromString("775c5dcc-e764-475e-b47c-41f0c6d0fcfd") andThen
                UUID.fromString("8d01532b-be58-4201-8798-9579d93dd95a")
        every { timeService.transitionCreated() } returns
                Instant.parse("2023-03-12T09:26:11.621448200Z") andThen
                Instant.parse("2023-03-12T09:26:14.622725100Z")

        mockMvc.perform(
            post("/transition/vehicle-to-depot").contentType(MediaType.APPLICATION_JSON).content(
                """
                {
                  "vehicleId": "75920a8f-03ad-4479-bf32-424e949ea051",
                  "depotId": "ec1169d3-1954-4414-b966-9cb74639fc7c"
                }
                """.trimIndent()
            )
        ).andExpect(status().isAccepted)

        mockMvc.perform(
            get("/state/parked-vehicles/ec1169d3-1954-4414-b966-9cb74639fc7c")
        ).andExpect(status().isOk).andExpect(
            content().json(
                """
                {
                  "depotId": "ec1169d3-1954-4414-b966-9cb74639fc7c",
                  "depotName": "BRYN Hall A",
                  "vehicles": [
                    {
                      "vehicleId": "75920a8f-03ad-4479-bf32-424e949ea051",
                      "name": "SLFA187-3001"
                    }
                  ]
                }
                """.trimIndent(), true
            )
        )

        mockMvc.perform(
            post("/transition/vehicle-from-depot").contentType(MediaType.APPLICATION_JSON).content(
                """
                {
                  "vehicleId": "75920a8f-03ad-4479-bf32-424e949ea051",
                  "depotId": "ec1169d3-1954-4414-b966-9cb74639fc7c"
                }
                """.trimIndent()
            )
        ).andExpect(status().isAccepted)

        mockMvc.perform(
            get("/state/parked-vehicles/ec1169d3-1954-4414-b966-9cb74639fc7c")
        ).andExpect(status().isOk).andExpect(
            content().json(
                """
                {
                  "depotId": "ec1169d3-1954-4414-b966-9cb74639fc7c",
                  "depotName": "BRYN Hall A",
                  "vehicles": []
                }
                """.trimIndent(), true
            )
        )
    }
}
