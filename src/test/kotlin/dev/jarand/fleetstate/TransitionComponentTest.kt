package dev.jarand.fleetstate

import com.ninjasquad.springmockk.MockkBean
import dev.jarand.fleetstate.config.ComponentTest
import dev.jarand.fleetstate.id.IdService
import dev.jarand.fleetstate.time.TimeService
import dev.jarand.fleetstate.vehicle.domain.VehicleType
import io.mockk.every
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*

@Transactional
class TransitionComponentTest : ComponentTest() {

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
                "id" to UUID.fromString("6a69b439-b5b8-4c9d-9268-ec4ea7061d47"),
                "name" to "SLFA187-3001",
                "type" to VehicleType.BUS.name,
                "created" to Instant.parse("2023-03-11T11:15:14.962102700Z").toString()
            )
        )
        jdbcTemplate.update(
            "INSERT INTO station (id, name, created) VALUES (:id, :name, :created)",
            mapOf(
                "id" to UUID.fromString("d67060bf-bbe7-484f-ad87-a300eac14c93"),
                "name" to "Oslo Sentralstasjon",
                "created" to Instant.parse("2023-03-11T11:15:19.965344Z").toString()
            )
        )
        jdbcTemplate.update(
            "INSERT INTO depot (id, name, created) VALUES (:id, :name, :created)",
            mapOf(
                "id" to UUID.fromString("74feeff0-e26a-4713-90ba-fd04f94ce008"),
                "name" to "BRYN Hall A",
                "created" to Instant.parse("2023-03-11T11:25:32.934185300Z").toString()
            )
        )
    }

    @Test
    fun `POST vehicle to station transition should save transition in database and return 202`() {
        every { idService.transitionId() } returns UUID.fromString("9af98a93-6467-40b8-9cee-ba5cb0803170")
        every { timeService.transitionCreated() } returns Instant.parse("2023-03-11T11:26:28.601376700Z")

        mockMvc.perform(
            post("/transition/vehicle-to-station").contentType(MediaType.APPLICATION_JSON).content(
                """
                {
                  "vehicleId": "6a69b439-b5b8-4c9d-9268-ec4ea7061d47",
                  "stationId": "d67060bf-bbe7-484f-ad87-a300eac14c93"
                }
                """.trimIndent()
            )
        ).andExpect(status().isAccepted)

        val exists = jdbcTemplate.queryForObject(
            "SELECT EXISTS(SELECT * FROM vehicle_station_transition WHERE id = :id AND vehicle_id = :vehicle_id AND station_id = :station_id AND type = :type AND created = :created)",
            mapOf(
                "id" to "9af98a93-6467-40b8-9cee-ba5cb0803170",
                "vehicle_id" to "6a69b439-b5b8-4c9d-9268-ec4ea7061d47",
                "station_id" to "d67060bf-bbe7-484f-ad87-a300eac14c93",
                "type" to "TO_STATION",
                "created" to "2023-03-11T11:26:28.601376700Z"
            ),
            Boolean::class.java
        )
        Assertions.assertThat(exists).isTrue
    }

    @Test
    fun `POST vehicle from station transition should save transition in database and return 202`() {
        every { idService.transitionId() } returns UUID.fromString("294d871a-4958-47ab-8d1f-6c501a57c613")
        every { timeService.transitionCreated() } returns Instant.parse("2023-03-11T11:26:28.581224Z")

        mockMvc.perform(
            post("/transition/vehicle-from-station").contentType(MediaType.APPLICATION_JSON).content(
                """
                {
                  "vehicleId": "6a69b439-b5b8-4c9d-9268-ec4ea7061d47",
                  "stationId": "d67060bf-bbe7-484f-ad87-a300eac14c93"
                }
                """.trimIndent()
            )
        ).andExpect(status().isAccepted)

        val exists = jdbcTemplate.queryForObject(
            "SELECT EXISTS(SELECT * FROM vehicle_station_transition WHERE id = :id AND vehicle_id = :vehicle_id AND station_id = :station_id AND type = :type AND created = :created)",
            mapOf(
                "id" to "294d871a-4958-47ab-8d1f-6c501a57c613",
                "vehicle_id" to "6a69b439-b5b8-4c9d-9268-ec4ea7061d47",
                "station_id" to "d67060bf-bbe7-484f-ad87-a300eac14c93",
                "type" to "FROM_STATION",
                "created" to "2023-03-11T11:26:28.581224Z"
            ),
            Boolean::class.java
        )
        Assertions.assertThat(exists).isTrue
    }

    @Test
    fun `POST vehicle to depot transition should save transition in database and return 202`() {
        every { idService.transitionId() } returns UUID.fromString("d9545f96-11fa-42f2-b51e-e8d1309038eb")
        every { timeService.transitionCreated() } returns Instant.parse("2023-03-11T11:26:28.332330500Z")

        mockMvc.perform(
            post("/transition/vehicle-to-depot").contentType(MediaType.APPLICATION_JSON).content(
                """
                {
                  "vehicleId": "6a69b439-b5b8-4c9d-9268-ec4ea7061d47",
                  "depotId": "74feeff0-e26a-4713-90ba-fd04f94ce008"
                }
                """.trimIndent()
            )
        ).andExpect(status().isAccepted)

        val exists = jdbcTemplate.queryForObject(
            "SELECT EXISTS(SELECT * FROM vehicle_depot_transition WHERE id = :id AND vehicle_id = :vehicle_id AND depot_id = :depot_id AND type = :type AND created = :created)",
            mapOf(
                "id" to "d9545f96-11fa-42f2-b51e-e8d1309038eb",
                "vehicle_id" to "6a69b439-b5b8-4c9d-9268-ec4ea7061d47",
                "depot_id" to "74feeff0-e26a-4713-90ba-fd04f94ce008",
                "type" to "TO_DEPOT",
                "created" to "2023-03-11T11:26:28.332330500Z"
            ),
            Boolean::class.java
        )
        Assertions.assertThat(exists).isTrue
    }

    @Test
    fun `POST vehicle from depot transition should save transition in database and return 202`() {
        every { idService.transitionId() } returns UUID.fromString("cf27ed91-1b3d-4ee3-b2ef-09530549f412")
        every { timeService.transitionCreated() } returns Instant.parse("2023-03-11T11:26:28.615445500Z")

        mockMvc.perform(
            post("/transition/vehicle-from-depot").contentType(MediaType.APPLICATION_JSON).content(
                """
                {
                  "vehicleId": "6a69b439-b5b8-4c9d-9268-ec4ea7061d47",
                  "depotId": "74feeff0-e26a-4713-90ba-fd04f94ce008"
                }
                """.trimIndent()
            )
        ).andExpect(status().isAccepted)

        val exists = jdbcTemplate.queryForObject(
            "SELECT EXISTS(SELECT * FROM vehicle_depot_transition WHERE id = :id AND vehicle_id = :vehicle_id AND depot_id = :depot_id AND type = :type AND created = :created)",
            mapOf(
                "id" to "cf27ed91-1b3d-4ee3-b2ef-09530549f412",
                "vehicle_id" to "6a69b439-b5b8-4c9d-9268-ec4ea7061d47",
                "depot_id" to "74feeff0-e26a-4713-90ba-fd04f94ce008",
                "type" to "FROM_DEPOT",
                "created" to "2023-03-11T11:26:28.615445500Z"
            ),
            Boolean::class.java
        )
        Assertions.assertThat(exists).isTrue
    }
}
