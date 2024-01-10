package com.elfak.smartfarming.data.models

data class User(
    val id: String,
    val name: String,
    val email: String,
    val mqttToken: String,
) {
    companion object {
        fun fromApiResponse(data: Any): User {
            val user = data as Map<*, *>
            return User(
                id = user["id"].toString(),
                name = user["name"].toString(),
                email = user["email"].toString(),
                mqttToken = user["mqttToken"].toString()
            )
        }
    }
}
