package com.example.plugins

import io.ktor.serialization.kotlinx.json.*
import io.ktor.serialization.kotlinx.xml.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import kotlinx.serialization.json.Json
import nl.adaptivity.xmlutil.XmlDeclMode
import nl.adaptivity.xmlutil.serialization.XML

fun Application.configureSerialization() {
    log.info("Installing features now...")
    //To support JSON request/response
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            ignoreUnknownKeys = true
            isLenient = true
        })

        /*
        xml(format = XML {
            xmlDeclMode = XmlDeclMode.Charset
        })
         */
    }
}