package dev.jarand.fleetstate.station.domain

import java.time.Instant
import java.util.*

data class Station(val id: UUID, val name: String, val created: Instant)
