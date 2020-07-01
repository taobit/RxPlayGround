package com.pluralsight.rxjava2.utility.network

import com.pluralsight.rxjava2.module2.logError
import com.sun.net.httpserver.HttpServer
import java.net.InetSocketAddress
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import kotlin.system.exitProcess

object SimpleHttpServer {
    var server: HttpServer? = null
    var executor: Executor? = null

    fun stop() {
        // Request that the server stop accepting requests
        // and shut down.
        server?.stop(5)

        // Tell the VM to stop.
        exitProcess(0)
    }
}

fun main() {
    try {

        // We are going to makeObservable a startlingly simple HttpServer using Java's
        // built-in HttpServer class.  We will listen on port 22221.
        SimpleHttpServer.server = HttpServer.create(InetSocketAddress(22221), 10)

        // Any requests with the URI "/addition" will be handled by our AdditionHandler class.
        SimpleHttpServer.server?.createContext("/addition", AdditionHandler())

        // We aren't using a special executor.
        SimpleHttpServer.executor = Executors.newFixedThreadPool(5)
        SimpleHttpServer.server?.executor = SimpleHttpServer.executor

        // Start the server.
        SimpleHttpServer.server?.start()
    } catch (t: Throwable) {
        logError(t)
    }
}