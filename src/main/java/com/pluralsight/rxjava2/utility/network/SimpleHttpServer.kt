package com.pluralsight.rxjava2.utility.network

import com.pluralsight.rxjava2.module2.log
import com.sun.net.httpserver.HttpServer
import java.net.InetSocketAddress
import java.util.concurrent.Executor
import java.util.concurrent.Executors

object SimpleHttpServer {
    private var server: HttpServer? = null
    private var executor: Executor? = null

    @JvmStatic
    fun main(args: Array<String>) {
        try {

            // We are going to makeObservable a startlingly simple HttpServer using Java's
            // built-in HttpServer class.  We will listen on port 22221.
            server = HttpServer.create(InetSocketAddress(22221), 10)

            // Any requests with the URI "/addition" will be handled by our AdditionHandler class.
            server?.createContext("/addition", AdditionHandler())

            // We aren't using a special executor.
            executor = Executors.newFixedThreadPool(5)
            server?.executor = executor

            // Start the server.
            server?.start()
        } catch (t: Throwable) {
            log.error(t.message, t)
        }
    }

    @JvmStatic
    fun stop() {
        // Request that the server stop accepting requests
        // and shut down.
        server!!.stop(5)

        // Tell the VM to stop.
        System.exit(0)
    }
}