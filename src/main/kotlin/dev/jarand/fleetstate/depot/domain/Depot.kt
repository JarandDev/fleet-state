package dev.jarand.fleetstate.depot.domain

import java.time.Instant
import java.util.*

data class Depot(val id: UUID, val name: String, val created: Instant)
