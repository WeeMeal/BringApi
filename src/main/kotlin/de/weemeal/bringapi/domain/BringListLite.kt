package de.weemeal.bringapi.domain

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class BringListLite(
    val purchase: List<Product>
)


