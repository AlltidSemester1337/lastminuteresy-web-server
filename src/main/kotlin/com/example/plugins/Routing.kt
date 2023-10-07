package com.example.plugins

import com.example.adapter.http.TableServiceImpl
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*
import kotlinx.html.*

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
        static("/") {
            resources("static")
        }
    }
}
