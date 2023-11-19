import BringQrConverter.Companion.addProductsToList
import io.github.cdimascio.dotenv.dotenv

suspend fun main() {
    val dotenv = dotenv()
    val email = dotenv["EMAIL"]
    val password = dotenv["PASSWORD"]

    val jsonStr = """
      {
        "purchase": [
            {
                "name": "JsonÄpfel111111111",
                "specification": "111111111"
            },
            {
                "name": "JsonAnanas11111111",
                "specification": "5 kleine111111"
            }
        ]
      }
    """

    val qrCode = QrCode(jsonStr)
    val bringApi = BringApi(email = email, password = password)
    println(bringApi.getPurchaseList())
    BringQrConverter(qrCode = qrCode, bringApi = bringApi).addProductsToList()
    println(bringApi.getPurchaseList())

}