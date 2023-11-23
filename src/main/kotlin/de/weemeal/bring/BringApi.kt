package de.weemeal.bring

import de.weemeal.bring.domain.BringList
import de.weemeal.bring.domain.Product
import de.weemeal.bring.domain.User
import de.weemeal.bring.domain.User.Companion.isNotNull
import de.weemeal.bring.domain.User.Companion.isNull
import de.weemeal.bring.exceptions.AlreadyLoggedInException
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
    private var customHeaders: Map<String, String>? = null

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
        customHeaders = mapOf(
            "X-BRING-API-KEY" to "cof4Nc6D8saplXjE3h3HXqHH8m7VU2i1Gs0g85Sp",
            "X-BRING-CLIENT" to "webApp",
            "X-BRING-USER-UUID" to user!!.uuid,
            "X-BRING-VERSION" to "303070050",
            "Content-Type" to "application/x-www-form-urlencoded; charset=UTF-8",
        )
    }

    suspend fun getPurchaseList(): BringList {
        if (user.isNull()) {
            throw AuthenticationException("You are not authenticated")
        }


        val response: HttpResponse = client.get("${baseUrl}bringlists/${user!!.bringListUUID}") {
            headers {
                customHeaders?.forEach { (key, value) ->
                    append(key, value)
                }
            }
        }

        return if (response.status == HttpStatusCode.OK) {
            response.body()
        } else {
            when (response.status) {
                HttpStatusCode.BadRequest -> {
                    throw ResponseException(
                        response, "Error: ${HttpStatusCode.BadRequest}. Could not read Purchase List from User."
                    )
                }

                else -> throw Exception()
            }
        }

    }


    suspend fun addProductToList(product: Product) {
        if (user.isNull()) {
            throw AuthenticationException("You are not authenticated")
        }

        val url = "${baseUrl}bringlists/${user!!.bringListUUID}".encodeURLPath()
        val params =
            "purchase=${product.name.encodeURLParameter()}&specification=${product.specification.encodeURLParameter()}"

        println("${url}?${params}")

        val response: HttpResponse = client.put("${url}?${params}") {
            headers {
                customHeaders?.forEach { (key, value) ->
                    append(key, value)
                }
            }
        }

        return if (response.status == HttpStatusCode.OK || response.status == HttpStatusCode.NoContent) {
            response.body()
        } else {
            when (response.status) {
                HttpStatusCode.BadRequest -> {
                    throw ResponseException(
                        response,
                        "Error: ${HttpStatusCode.BadRequest}. Could not add Product (${product.name}) with Specification (${product.specification}) to the List from User."
                    )
                }

                else -> throw Exception("response.status: ${response.status}")
            }
        }
    }
}