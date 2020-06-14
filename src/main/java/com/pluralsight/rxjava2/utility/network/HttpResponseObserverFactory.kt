package com.pluralsight.rxjava2.utility.network

import com.pluralsight.rxjava2.module2.log
import io.reactivex.rxjava3.core.Observable
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.charset.StandardCharsets
import java.time.Duration

object HttpResponseObserverFactory {
    @JvmStatic
    fun additionRequestResponseObservable(httpUri: URI): Observable<Int> {
        log.info("Creating observable for: {}", httpUri.toString())
        val httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .connectTimeout(Duration.ofSeconds(20))
                .build()
        val request = HttpRequest.newBuilder()
                .uri(httpUri)
                .GET()
                .build()
        val futureResponse = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8))
        return Observable.fromFuture(futureResponse)
                .map { obj: HttpResponse<String> -> obj.body() }
                .map { s: String -> s.toInt() }
                .doOnNext { responseInteger: Int? -> log.info("Response: {}", responseInteger) }
    }
}