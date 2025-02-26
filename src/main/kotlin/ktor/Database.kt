package ktor
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database


fun Application.configureDatabase(){
    val driver = environment.config.property("ktor.database.driver").getString()
    val url = environment.config.property("ktor.database.url").getString()
    val user = environment.config.property("ktor.database.username").getString()
    val password = environment.config.property("ktor.database.password").getString()

    Database.connect(url, driver, user, password)
}