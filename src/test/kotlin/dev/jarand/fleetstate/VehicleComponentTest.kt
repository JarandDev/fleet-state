package dev.jarand.fleetstate

import com.ninjasquad.springmockk.MockkBean
import dev.jarand.fleetstate.componenttest.ComponentTest
import dev.jarand.fleetstate.id.IdService
import dev.jarand.fleetstate.time.TimeService
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
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

    @Test
    fun `GET health should return 200 and expected json`() {
        every { idService.vehicleId() } returns UUID.fromString("e5d86d40-58d7-45a9-81ee-4187d6f3e625")
        every { timeService.vehicleCreated() } returns Instant.parse("2023-03-10T21:09:38.973123700Z")

        mockMvc.perform(
                post("/vehicle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                {
                                  "name": "SL18-401"
                                }
                                """.trimIndent()))
                .andExpect(status().isOk)
                .andExpect(content().json(
                        """
                        {
                          "id": "e5d86d40-58d7-45a9-81ee-4187d6f3e625",
                          "name": "SL18-401",
                          "created": "2023-03-10T21:09:38.973123700Z"
                        }
                        """.trimIndent(), true))
    }
}