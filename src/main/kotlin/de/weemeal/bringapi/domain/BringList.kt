package de.weemeal.bringapi.domain

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class BringList(
    val uuid: String,
    val status: String,
    val purchase: List<Product>,
    val recently: List<Product>
)


