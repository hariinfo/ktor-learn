package com.example.table

import com.example.data.Patient
import io.ktor.server.routing.*
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.slf4j.LoggerFactory

object Patient: IntIdTable("patient") {
    val first_name: Column<String> = varchar("first_name", 50)
    val last_name: Column<String> = varchar("last_name", 50)
}

