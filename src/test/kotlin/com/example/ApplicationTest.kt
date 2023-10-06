package com.example

import com.example.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.*

class ApplicationTest {
    @Test
    fun testMainPage() = testApplication {
        application {
            configureRouting()
        }
        client.get("/index.html").apply {
            assertEquals(HttpStatusCode.OK, status)
        }
    }

    @Test
    fun testDemoRestaurantBookingPage() = testApplication {
        application {
            configureRouting()
        }
        client.get("/demorestaurant/bookTable.html").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertTrue(bodyAsText().contains("Number of free tables: 0"))
        }
    }

    @Test
    fun testDemoRestaurantBookRequest() = testApplication {
        application {
            configureRouting()
        }
        client.get("/demorestaurant/book").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertTrue(bodyAsText().contains("Table booked!"))
        }
    }
}
