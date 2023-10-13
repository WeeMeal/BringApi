package domain

import java.util.*

data class BringList(
    val uuid: String,
    val status: String,
    val purchase: List<Product>,
    val recently: List<Product>
)


