package com.example.plugins

import com.example.adapter.http.TableServiceImpl
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*
import kotlinx.html.*

fun Application.configureRouting() {
    routing {
        get("/demorestaurant/bookTable.html") {
            call.respondHtml(HttpStatusCode.OK) {
                // TODO: Fixa noClassdefError
                //val freeTables = TableServiceImpl().numberOfFreeTables;
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
                        +"Number of free tables: 0"
                        //+freeTables.toString()
                    }
                    br { }
                    br { }
                    a(href = "/demorestaurant/book") { +"Book a table" }
                }
            }
        }
        get("/demorestaurant/book") {
            call.respondHtml(HttpStatusCode.OK) {
                head {
                    title {
                        +"lastminuteresy"
                    }
                }
                body {
                    strong {
                        +"Table booked!"
                    }
                }
            }
        }
        static("/") {
            resources("static")
        }
    }
}
