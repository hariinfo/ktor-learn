package com.example.data

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.statements.UpdateBuilder

@Serializable
data class Patient(val id: Int, val firstName: String, val lastName: String){
    companion object {
        fun fromResultRow(resultRow: ResultRow) = Patient(
            id = resultRow[com.example.table.Patient.id].value,
            firstName = resultRow[com.example.table.Patient.first_name],
            lastName = resultRow[com.example.table.Patient.last_name]
        )

        fun populateUpdateBuilder(builder: UpdateBuilder<Int>, patient: Patient) {
            builder[com.example.table.Patient.id] = patient.id
            builder[com.example.table.Patient.first_name] = patient.firstName
            builder[com.example.table.Patient.last_name] = patient.lastName
        }
    }
}


