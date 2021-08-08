package kg.bakai.countries.model

import com.google.gson.JsonObject

data class Response(
    val access: String,
    val data: JsonObject,
    val limit: Int,
    val offset: Int,
    val status: String,
    val statusCode: Int,
    val total: Int,
    val version: String
)