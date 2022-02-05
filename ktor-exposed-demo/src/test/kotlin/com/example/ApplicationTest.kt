package com.example

import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import com.example.plugins.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.jetbrains.annotations.TestOnly
import kotlin.system.measureTimeMillis
import kotlin.test.Test

class ApplicationTest {

    suspend fun doSomething1(): Int{
        delay(5000L)
        return 1
    }

    suspend fun doSomething2(): Int{
        delay(5000L)
        return 2
    }

    @Test
    fun testSequentialByDefault() = runBlocking<Unit> {
        println("Running core business logic")

        //Do this to unblock current thread from delayed execution of asyncstuff
        launch {
            doAsyncStuff()
        }

        println("execution complete...")

    }

    suspend fun doAsyncStuff() = coroutineScope{
        var job = launch {
            //Send an event to a Kafka topic
            delay(5000L)
            println("Send message to kafka")
        }
        job.join()

        launch {
            //Post operational metrics to an API
            delay(2000L)
            println("posted metrics to API")
        }
    }
}