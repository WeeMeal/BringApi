package de.weemeal.bring.domain

import kotlinx.serialization.Serializable

@Serializable
data class BringList(
    val uuid: String,
    val status: String,
    val purchase: List<Product>,
    val recently: List<Product>
)


