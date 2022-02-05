import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database

fun Application.initDB(){
        log.info("---->" + environment.config.property("db.connection.url").getString())
        val config = HikariConfig()
        config.driverClassName = environment.config.property("db.connection.driverClass").getString()
        config.jdbcUrl = environment.config.property("db.connection.url").getString()
        config.username = environment.config.property("db.connection.username").getString()
        config.password = environment.config.property("db.connection.password").getString()
        val dataSource = HikariDataSource(config)
        Database.connect(dataSource)
}