package com.example.plugins

import com.example.adapter.http.TableServiceImpl
import com.example.model.Booking
import com.example.model.Integration
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.apache.*
import io.ktor.client.plugins.json.*
import io.ktor.client.plugins.kotlinx.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.coroutines.runBlocking
import kotlinx.html.*
import kotlinx.serialization.json.Json

fun getHttpClient(): HttpClient {
    return HttpClient(Apache) {
        install(JsonPlugin) {
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                ignoreUnknownKeys = true
            })
        }
    }
}


fun getAvailableBookings(): List<Booking> {
    val client = getHttpClient()
    var result: List<Booking>
    runBlocking {
        // TODO: This needs to be inserted into DockerFile or container variables before deploy
        // example TABLE_SERVICE_IP_PORT=localhost:8080
        result = client.get("http://" + System.getenv("TABLE_SERVICE_IP_PORT") + "/bookings/free/all") {
            contentType(ContentType.Application.Json)
        }.body()
    }
    return result
}

fun getAvailableIntegrations(): List<Integration> {
    val client = getHttpClient()
    var result: List<Integration>
    runBlocking {
        // TODO: This needs to be inserted into DockerFile or container variables before deploy
        // example TABLE_SERVICE_IP_PORT=localhost:8080
        result = client.get("http://" + System.getenv("TABLE_SERVICE_IP_PORT") + "/integrations") {
            contentType(ContentType.Application.Json)
        }.body()
    }
    return result
}

fun Application.configureRouting(tableService: TableServiceImpl) {
    routing {
        get("/demorestaurant/bookTable.html") {
            call.respondHtml(HttpStatusCode.OK) {
                val freeTables = tableService.getNumberOfFreeTables()
                head {
                    title {
                        +"lastminuteresy"
                    }
                }
                body {
                    h1 {
                        +"Book a table at chez Meredith"
                    }
                    strong {
                        +"Number of free tables: "
                        +freeTables.toString()
                    }
                    br { }
                    br { }
                    a(href = "/demorestaurant/book") { +"Book a table" }
                }
            }
        }
        get("/demorestaurant/book") {
            call.respondHtml(HttpStatusCode.OK) {
                tableService.bookTable()
                head {
                    title {
                        +"lastminuteresy"
                    }
                }
                body {
                    strong {
                        +"Table booked!"
                    }
                    br { }
                    br { }
                    a(href = "/demorestaurant/bookTable.html") { +"Return to table booking" }
                }
            }
        }
        get("/demorestaurant/admin/unbook") {
            call.respondHtml(HttpStatusCode.OK) {
                tableService.unbookTable()
                head {
                    title {
                        +"lastminuteresy"
                    }
                }
                body {
                    strong {
                        +"Table unbooked!"
                    }
                    br { }
                    br { }
                    a(href = "/demorestaurant/bookTable.html") { +"Return to table booking" }
                }
            }
        }
        get("/") {
            call.respondHtml(HttpStatusCode.OK) {
                head {
                    title {
                        +"lastminuteresy"
                    }
                }
                body {
                    h1 {
                        +"Available restaurant table bookings"
                    }
                    val bookings = getAvailableBookings()
                    bookings.forEach {
                        strong {
                            +it.toString()
                        }
                        br {}
                    }
                }
            }
        }
        get("/admin/book") {
            call.respondHtml(HttpStatusCode.OK) {
                head {
                    title {
                        +"lastminuteresy"
                    }
                }
                body {
                    h1 {
                        +"Request new booking"
                    }
                    val integrations = getAvailableIntegrations()
                    integrations.forEach {
                        strong {
                            +it.toString()
                        }
                        br {}
                    }
                    form(
                        action = "/admin/book",
                        encType = FormEncType.applicationXWwwFormUrlEncoded,
                        method = FormMethod.post
                    ) {
                        p {
                            +"Integration ID:"
                            numberInput(name = "integration_id")
                        }
                        p {
                            +"Time:"
                            dateTimeInput(name = "time")
                        }
                        p {
                            submitInput { value = "Request booking" }
                        }
                    }
                }
            }
        }
        post("/admin/book") {
            val formParameters = call.receiveParameters()
            val integrationId = formParameters["integration_id"]
            val time = formParameters["time"]
            val response = requestNewBooking(integrationId, time)
            call.respondHtml(HttpStatusCode.OK) {
                head {
                    title {
                        +"lastminuteresy"
                    }
                }
                body {
                    h1 {
                        +"Request new booking"
                    }
                    if (response.getStatusCode() == 200) {
                        strong { +"Booking requested successfully" }
                        a(href = "/") { +"Check available bookings" }
                    } else {
                        strong { +"Booking request failed with cause:" }
                        p { +response.toString() }
                        a(href = "/admin/book") { +"Try again" }
                    }
                }
            }
        }
    }
}

fun requestNewBooking(integrationId: String?, time: String?): Any {
    val client = getHttpClient()
    // TODO: Implement!
    //var result: List<Integration>
    //runBlocking {
    // TODO: This needs to be inserted into DockerFile or container variables before deploy
    // example TABLE_SERVICE_IP_PORT=localhost:8080
    //    result = client.get("http://" + System.getenv("TABLE_SERVICE_IP_PORT") + "/integrations") {
    //        contentType(ContentType.Application.Json)
    //    }.body()
    //}
    //return result
    return 0
}
