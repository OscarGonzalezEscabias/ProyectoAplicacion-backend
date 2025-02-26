package data.security

import domain.security.PasswordHashInterface
import java.security.MessageDigest

object PasswordHash : PasswordHashInterface {
    override fun hash(pass: String): String {
        val bytes = pass.toByteArray()
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(bytes)
        return hashBytes.joinToString("") { "%02x".format(it) }
    }

    override fun verify(pass: String, passHash: String): Boolean {
        return hash(pass) == passHash
    }
}