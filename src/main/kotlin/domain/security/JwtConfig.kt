package domain.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.auth.jwt.*

object JwtConfig {
    private const val secret = "super_secret_key"  // 🔑 Cambia esto por algo más seguro
    private const val issuer = "domain.com"
    private const val audience = "ktor_audience"
    private const val realm = "ktor_realm"
    private val algorithm = Algorithm.HMAC256(secret)

    fun generateToken(username: String): String {
        return JWT.create()
            .withIssuer(issuer)
            .withAudience(audience)
            .withSubject("Authentication")
            .withClaim("username", username)
            .withClaim("time", System.currentTimeMillis())
            .sign(algorithm)
    }

    fun configureAuthentication(config: JWTAuthenticationProvider.Config) {
        config.realm = realm
        config.verifier(
            JWT.require(algorithm)
                .withIssuer(issuer)
                .withAudience(audience)
                .build()
        )
        config.validate { credential ->
            if (credential.payload.getClaim("username").asString() != null) {
                JWTPrincipal(credential.payload)
            } else null
        }
    }
}