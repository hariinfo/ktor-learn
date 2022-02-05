package com.example.service

import com.example.data.Event
import com.example.data.Patient
import com.example.plugins.Producer
import io.ktor.server.routing.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.suspendedTransactionAsync
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import org.slf4j.LoggerFactory

object PatientService {
    var log = LoggerFactory.getLogger(PatientService::class.java)

    val producerInstance = Producer.instance;

     suspend fun addPatient(patient: Patient): Int? {
            val recordCount =  transaction {
                com.example.table.Patient.insertAndGetId {
                    it[first_name] = patient.firstName
                    it[last_name] = patient.lastName
                }
            }.value
            log.info("returning REST response......")
       return recordCount
    }

    suspend fun sendMessage() {
        println("send message thread: "+ Thread.currentThread().name)

        //launch {
        log.info("inside sending kafka message......")
        delay(5000L)
        producerInstance.sendMessage(Event("patient-created","Patient Created"))


    }

    fun getPatient(patientId: Int): Patient{
        return transaction {
            val query = com.example.table.Patient.select { com.example.table.Patient.id eq patientId }
            val resultRow = query.firstOrNull() ?: return@transaction Patient(0,"","")
            Patient.fromResultRow(resultRow)
        }
    }

    fun removePatient(patientId: Int): Boolean{
        return transaction {
           com.example.table.Patient.deleteWhere { com.example.table.Patient.id eq patientId } > 0
        }
    }

    fun updatePatient(patient: Patient) {
        transaction {
            com.example.table.Patient.update({com.example.table.Patient.id eq patient.id}){
                Patient.populateUpdateBuilder(it, patient)
            }
        }
    }
}