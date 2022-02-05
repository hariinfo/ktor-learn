package com.example.plugins

import com.example.data.Event
import com.example.service.PatientService
import io.ktor.server.application.*
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.clients.producer.RecordMetadata
import org.slf4j.LoggerFactory
import java.util.*
import java.util.concurrent.Future

class Producer {
    var producer: KafkaProducer<String, String>
    var log = LoggerFactory.getLogger(Producer::class.java)

    constructor() {
        val map = mutableMapOf<String, String>()
        map["key.serializer"] = "org.apache.kafka.common.serialization.StringSerializer"
        map["value.serializer"] = "org.apache.kafka.common.serialization.StringSerializer"
        map["bootstrap.servers"] = "localhost:9094"
        this.producer = KafkaProducer<String, String>(map as Map<String, Any>?)
    }
    companion object {
        var instance: Producer = Producer()
    }

    open fun sendMessage(event: Event) {
        var producerRecord: ProducerRecord<String,String> = ProducerRecord(event.eventType,event.message)
        var future: Future<RecordMetadata> = this.producer.send(producerRecord)
        println("future blocking thread: "+ Thread.currentThread().name)
        log.info("Future topic is" + future.get().topic())
    }
}

