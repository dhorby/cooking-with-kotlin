package com.dhorby.kotlin.cooking.web

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.containsSubstring
import org.http4k.client.OkHttp
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Status.Companion.OK
import org.http4k.hamkrest.hasStatus
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class CookingServerTest {
    private val port = Random().nextInt(1000) + 8000
    private val client = OkHttp()
    private val server = CookingServer(port)

    @BeforeEach
    fun setup(): Unit {
        server.start()
    }

    @AfterEach
    fun teardown(): Unit {
        server.stop()
    }

    @Test
    fun `responds to ping`() {
        assertThat(client(Request(Method.GET, "http://localhost:$port/ping")), hasStatus(OK))
    }

    @Test
    fun `return cooking for kotlin main page`() {
        val response = client(Request(Method.GET, "http://localhost:$port/"))
        assertThat(response.bodyString(), containsSubstring("Cooking for Kotlin"))
    }
}