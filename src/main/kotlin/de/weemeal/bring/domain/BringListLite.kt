package de.weemeal.bring.domain

import kotlinx.serialization.Serializable

@Serializable
data class BringListLite(
    val purchase: List<Product>
)


