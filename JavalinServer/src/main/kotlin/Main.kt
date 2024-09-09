package org.example

import com.google.gson.Gson
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.community.ssl.SslPlugin
import io.javalin.http.bodyAsClass
import org.eclipse.jetty.server.ServerConnector

fun main() {
    var gson = Gson()
    Javalin.create{ context ->
        context.router.apiBuilder {
            before{
                println()
                println("PATH: ${it.path()}")
                println("BODY: ${it.body()}")
                println()
            }
            path("/publicData") {
                get("/offers"){
                    it.json(gson.toJson(Repository.currentDataChunk.offers))
                }
                get("/vacancies"){
                    it.json(gson.toJson(Repository.currentDataChunk.vacancies))
                }
            }
        }
    }.start(8080)
}