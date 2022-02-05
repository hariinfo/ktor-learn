package com.example.routes

import com.example.data.Event
import com.example.data.Patient
import com.example.plugins.Producer
import com.example.service.PatientService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory

//https://github.com/martinkero/todoapp/blob/40fdc8d0cf15cd562ebf7d2f1d0384a9851fcb76/backend/src/main/kotlin/se/martinkero/routing/TodoRoutes.kt
fun Route.patientRoute(){
    var log = LoggerFactory.getLogger(Route::class.java)
    val producerInstance = Producer.instance;

    route("/patient") {

        get("{patientId}") {
            log.info("in get method")
            val patientId = call.parameters["patientId"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Invalid Patient Id")
            val patient: Patient = PatientService.getPatient(patientId.toInt())
            //val patient = Patient(10,"1","2")
            call.respond(HttpStatusCode.OK, patient)
        }

        post (""){
            println("main thread: "+ Thread.currentThread().name)
            runBlocking {
                println("run blocking thread: "+ Thread.currentThread().name)

                call.application.environment.log.info("Inside the patient post method")
                val patient = call.receive<Patient>()
                val id = PatientService.addPatient(patient)
                launch {
                    println("run blocking launch: "+ Thread.currentThread().name)

                    PatientService.sendMessage()
                }
                println("return main thread: "+ Thread.currentThread().name)

                log.info("returning the REST response now")
                call.respond(patient)
            }
        }

        put("{patientId}") {
            val patientId = call.parameters["patientId"] ?: return@put call.respond(HttpStatusCode.BadRequest, "Invalid Patient Id")

        }

        delete ("patientId") {
            val patientId = call.parameters["patientId"] ?: return@delete call.respond(HttpStatusCode.BadRequest, "Invalid Patient Id")
            val patient = PatientService.getPatient(patientId.toInt())
            if (patient == null){
                call.respond(HttpStatusCode.NotFound)
                return@delete
            }
            PatientService.removePatient(patientId.toInt())
            call.respond(HttpStatusCode.OK, patient)
        }

        suspend fun sendMessage() = runBlocking{
            launch {
                PatientService.log.info("inside sending kafka message......")
                delay(5000L)
                PatientService.producerInstance.sendMessage(Event("patient-created","Patient Created"))

            }
        }
    }
}