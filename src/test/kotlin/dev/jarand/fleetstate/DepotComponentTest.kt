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

class DepotComponentTest : ComponentTest() {

    @MockkBean
    lateinit var idService: IdService

    @MockkBean
    lateinit var timeService: TimeService

    @Autowired
    lateinit var jdbcTemplate: NamedParameterJdbcTemplate

    @Test
    fun `POST depot should save depot in database and return 200 with expected json`() {
        every { idService.depotId() } returns UUID.fromString("c77f459a-4f07-4920-9e5e-2200d566c8d1")
        every { timeService.depotCreated() } returns Instant.parse("2023-03-11T08:03:02.719630800Z")

        mockMvc.perform(
            post("/depot").contentType(MediaType.APPLICATION_JSON).content(
                """
                {
                  "name": "BRYN Hall A"
                }
                """.trimIndent()
            )
        ).andExpect(status().isOk).andExpect(
            content().json(
                """
                {
                  "id": "c77f459a-4f07-4920-9e5e-2200d566c8d1",
                  "name": "BRYN Hall A",
                  "created": "2023-03-11T08:03:02.719630800Z"
                }
                """.trimIndent(), true
            )
        )

        val exists = jdbcTemplate.queryForObject(
            "SELECT EXISTS(SELECT * FROM depot WHERE id = :id AND name = :name AND created = :created)",
            mapOf(
                "id" to "c77f459a-4f07-4920-9e5e-2200d566c8d1",
                "name" to "BRYN Hall A",
                "created" to "2023-03-11T08:03:02.719630800Z"
            ),
            Boolean::class.java
        )
        assertThat(exists).isTrue
    }
}
