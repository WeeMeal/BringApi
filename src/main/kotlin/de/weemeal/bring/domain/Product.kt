package de.weemeal.bring.domain

import kotlinx.serialization.Serializable


@Serializable
data class Product(
    val name: String,
    val specification: String
)