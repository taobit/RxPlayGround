package com.pluralsight.rxjava2.utility.network

import com.pluralsight.rxjava2.utility.network.SimpleHttpServer.stop
import com.pluralsight.rxjava2.utility.sleep
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit

class AdditionHandler : HttpHandler {
    @Throws(IOException::class)
    override fun handle(exchange: HttpExchange) {

        // Map the incoming parameters from the URL request.
        val parameters = queryToMap(exchange.requestURI.query)
        var a = 0
        var b = 0
        var delay = 0

        // Parse the "a" parameter if provided.
        if (parameters.containsKey("a")) {
            a = (parameters["a"] ?: error("")).toInt()
        }

        // Parse the "b" parameter if provided.
        if (parameters.containsKey("b")) {
            b = (parameters["b"] ?: error("")).toInt()
        }

        // Parse the delay if provided.
        if (parameters.containsKey("delay")) {
            delay = (parameters["delay"] ?: error("")).toInt()
        }

        // Sleep for as long as the delay specifies.
        sleep(delay.toLong(), TimeUnit.MILLISECONDS)

        // Create a string response after adding a plus b.
        val response = (a + b).toString()

        // Set the response header indicating success (200) and how long
        // the response is going to be in bytes.
        exchange.sendResponseHeaders(200, response.length.toLong())

        // Write out the response byte for byte.
        exchange.responseBody.write(response.toByteArray())

        // Close the exchange.
        exchange.close()

        // If the two numbers add up to -1, we are going to
        // request that the server shut down.
        if (a + b == -1) {
            stop()
        }
    }

    companion object {
        private fun queryToMap(query: String): Map<String, String> {
            val result: MutableMap<String, String> = HashMap()
            for (param in query.split("&").toTypedArray()) {
                val pair = param.split("=").toTypedArray()
                if (pair.size > 1) {
                    result[pair[0]] = pair[1]
                } else {
                    result[pair[0]] = ""
                }
            }
            return result
        }
    }
}