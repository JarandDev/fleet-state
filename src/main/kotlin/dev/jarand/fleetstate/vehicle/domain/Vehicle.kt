package dev.jarand.fleetstate.vehicle.domain

import java.time.Instant
import java.util.*

data class Vehicle(val id: UUID, val name: String, val created: Instant)
