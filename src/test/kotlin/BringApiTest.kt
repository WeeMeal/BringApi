import io.github.cdimascio.dotenv.dotenv
import io.ktor.client.engine.mock.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.utils.io.*
import kotlinx.coroutines.runBlocking

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class BringApiTest {

    @Test
    fun sampleClientTest() {
        runBlocking {
            val mockEngine = MockEngine { request ->
                respond(
                    content = ByteReadChannel("""{"ip":"127.0.0.1"}"""),
                    status = HttpStatusCode.OK,
                    headers = headersOf(HttpHeaders.ContentType, "application/json")
                )
            }
            val dotenv = dotenv()
            val email = dotenv["EMAIL"]
            val password = dotenv["PASSWORD"]


            val bringApi = BringApi(email = email, password = password)
            bringApi.getPurchaseList()
//            Assert.assertEquals("127.0.0.1", apiClient.getIp().ip)
        }
    }

}