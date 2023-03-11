package dev.jarand.fleetstate.monitoring

import dev.jarand.fleetstate.config.ComponentTest
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class MonitoringComponentTest : ComponentTest() {

    @Test
    fun `GET health should return 200 and expected json`() {
        mockMvc.perform(get("/actuator/health"))
            .andExpect(status().isOk)
            .andExpect(
                content().json(
                    """
                    {
                      "status": "UP",
                      "groups": [
                        "liveness",
                        "readiness"
                      ]
                    }
                    """.trimIndent(), true
                )
            )
    }

    @Test
    fun `GET health liveness group should return 200 and expected json`() {
        mockMvc.perform(get("/actuator/health/liveness"))
            .andExpect(status().isOk)
            .andExpect(
                content().json(
                    """
                    {
                      "status": "UP"
                    }
                    """.trimIndent(), true
                )
            )
    }

    @Test
    fun `GET health readiness group should return 200 and expected json`() {
        mockMvc.perform(get("/actuator/health/readiness"))
            .andExpect(status().isOk)
            .andExpect(
                content().json(
                    """
                    {
                      "status": "UP"
                    }
                    """.trimIndent(), true
                )
            )
    }

    @Test
    fun `GET prometheus should return 200 and expected content type`() {
        mockMvc.perform(get("/actuator/prometheus"))
            .andExpect(status().isOk)
            .andExpect(header().string("Content-Type", "text/plain;version=0.0.4;charset=utf-8"))

    }
}
