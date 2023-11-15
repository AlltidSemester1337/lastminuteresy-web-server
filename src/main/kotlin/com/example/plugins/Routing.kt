package com.example.plugins

import com.example.adapter.http.TableServiceImpl
import com.example.model.Booking
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.apache.*
import io.ktor.client.plugins.json.*
import io.ktor.client.plugins.kotlinx.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.routing.*
import kotlinx.coroutines.runBlocking
import kotlinx.html.*
import kotlinx.serialization.json.Json

fun getAvailableBookings(): List<Booking> {
    val client = HttpClient(Apache) {
        install(JsonPlugin) {
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                ignoreUnknownKeys = true
            })
        }
    }
    var result: List<Booking>
    runBlocking {
        result = client.get(System.getenv("BOOKINGS_URL")) {
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
                        br{}
                    }
                }
            }
        }
    }
}
