//import domain.Product
//import io.github.cdimascio.dotenv.dotenv
//
//// da es hier bald zu einer Lib wird, ist die Main nur noch für einfache Testt Zwecke. Wird in Zukunft gelöscht.
//suspend fun main() {
//    val dotenv = dotenv()
//    val email = dotenv["EMAIL"]
//    val password = dotenv["PASSWORD"]
//
//    val bringApi = BringApi(email = email, password = password)
//    println(bringApi.getPurchaseList())
//    bringApi.addProductToList(Product("JsonÄpfel111111111", "111111111"))
//    println(bringApi.getPurchaseList())
//
//}