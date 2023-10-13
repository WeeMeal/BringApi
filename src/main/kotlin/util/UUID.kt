package util

import java.util.UUID

object UUID {
    fun UUID?.isNull(): Boolean {
        return (this == null)
    }

}