import io.github.cdimascio.dotenv.Dotenv
import io.github.cdimascio.dotenv.dotenv
import java.lang.System.getenv

suspend fun main(args: Array<String>) {
    val dotenv = dotenv()
    val email = dotenv["EMAIL"]
    val password = dotenv["PASSWORD"]


    val bringApi = BringApi(email = email, password = password)
//    BringApi().test()
//    BringApi().test()
//    bringApi.test()
}