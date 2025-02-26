package data.model

import domain.model.User
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class UserDao(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<UserDao>(UserTable)

    var username by UserTable.username
    var email by UserTable.email
    var password by UserTable.password

    fun toUser(): User {
        return User(
            id = id.value,
            username = username,
            email = email,
            password = password
        )
    }
}