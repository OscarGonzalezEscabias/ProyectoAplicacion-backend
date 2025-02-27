package domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Review (
    val id: Int = 0,
    val title: String,
    val description: String,
    val image: String
)