package com.example

import com.example.adapter.http.TableServiceImpl
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
            configureRouting(TableServiceImpl())
        }
        client.get("/index.html").apply {
            assertEquals(HttpStatusCode.OK, status)
        }
    }

    @Test
    fun testDemoRestaurantBookingPage() = testApplication {
        application {
            configureRouting(TableServiceImpl())
        }
        client.get("/demorestaurant/bookTable.html").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertTrue(bodyAsText().contains("Number of free tables: 5"))
        }
    }

    @Test
    fun testDemoRestaurantBookRequest() = testApplication {
        application {
            configureRouting(TableServiceImpl())
        }
        client.get("/demorestaurant/book").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertTrue(bodyAsText().contains("Table booked!"))
        }
    }

    @Test
    fun testDemoRestaurantBookingPageUpdatedAfterBooking() = testApplication {
        application {
            configureRouting(TableServiceImpl())
        }
        client.get("/demorestaurant/bookTable.html").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertTrue(bodyAsText().contains("Number of free tables: 5"))
        }
        client.get("/demorestaurant/book").apply {
        }
        client.get("/demorestaurant/bookTable.html").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertTrue(bodyAsText().contains("Number of free tables: 4"))
        }
    }

    @Test
    fun testDemoRestaurantUnbookRequest() = testApplication {
        application {
            configureRouting(TableServiceImpl())
        }
        client.get("/demorestaurant/admin/unbook").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertTrue(bodyAsText().contains("Table unbooked!"))
        }
    }

    @Test
    fun testDemoRestaurantBookingPageUpdatedAfterUnbooking() = testApplication {
        application {
            configureRouting(TableServiceImpl())
        }
        client.get("/demorestaurant/bookTable.html").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertTrue(bodyAsText().contains("Number of free tables: 5"))
        }
        client.get("/demorestaurant/admin/unbook").apply {
        }
        client.get("/demorestaurant/bookTable.html").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertTrue(bodyAsText().contains("Number of free tables: 6"))
        }
    }
}
