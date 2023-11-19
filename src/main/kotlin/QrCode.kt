import domain.BringListLite
import kotlinx.serialization.json.Json

class QrCode(val jsonStr: String) {

    companion object {
        fun QrCode.parseToBringListLite(): BringListLite {
            return Json.decodeFromString<BringListLite>(this.jsonStr)
        }
    }
}