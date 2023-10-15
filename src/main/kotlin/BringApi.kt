import domain.BringList
import domain.User
import domain.User.Companion.isNotNull
import domain.User.Companion.isNull
import exceptions.AlreadyLoggedInException
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.gson.*
import kotlinx.coroutines.runBlocking
import javax.naming.AuthenticationException

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

        val response: HttpResponse = client.get("${baseUrl}bringlists/?email=$email&password=$password")
        if (response.status == HttpStatusCode.BadRequest) {
            throw ResponseException(response, "Error: ${HttpStatusCode.BadRequest}. Login was not successful.")
        }
        this.user = response.body()
    }

    suspend fun getPurchaseList(): BringList {
        if (user.isNull()) {
            throw AuthenticationException("You are not authenticated")
        }


        val response: HttpResponse = client.get("${baseUrl}bringlists/${user!!.bringListUUID}")

        return if (response.status == HttpStatusCode.OK) {
            response.body()
        } else {
            when (response.status){
                HttpStatusCode.BadRequest -> {
                throw ResponseException(response, "Error: ${HttpStatusCode.BadRequest}. Login was not successful.")
            }
                else -> throw Exception()
            }
        }

    }


}