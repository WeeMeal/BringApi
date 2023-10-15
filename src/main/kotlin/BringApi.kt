import domain.BringList
import domain.User
import domain.User.Companion.isNotNull
import exceptions.AlreadyLoggedInException
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.gson.*
import kotlinx.coroutines.runBlocking

class BringApi(email: String, password: String) {

    private var user: User? = null
    private val baseUrl = "https://api.getbring.com/rest/"

    //        val List<domain.BringList> = listOf()
    private var client = HttpClient(CIO) {
        install(ContentNegotiation) {
            gson()
        }
    }

    init {
        runBlocking {
            login(email = email, password = password)
        }
    }


    private suspend fun login(email: String, password: String) {
        if (user.isNotNull()) {
            throw AlreadyLoggedInException("You are already Logged In")
        }

        val response: User = client.get("${baseUrl}bringlists/?email=$email&password=$password").body()
        this.user = response
        println(this.user)
        client.close()
    }

    suspend fun test() {
        val response: BringList =
            client.get("https://api.getbring.com/rest/bringlists/076df773-61e1-4aa2-8cea-ceada409fab9").body()
        println(response)
        client.close()
    }


}