package domain

import util.UUID

data class User(
    val uuid: String,
    val publicUuid: String,
    val email: String,
    val bringListUUID: String,
    val name: String,
)
