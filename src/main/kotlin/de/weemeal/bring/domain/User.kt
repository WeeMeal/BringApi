package de.weemeal.bring.domain

data class User(
    val uuid: String,
    val publicUuid: String,
    val email: String,
    val bringListUUID: String,
    val name: String,
){
    companion object{
        fun User?.isNull(): Boolean{
            return this == null
        }

        fun User?.isNotNull(): Boolean{
            return !this.isNull()
        }
    }
}
