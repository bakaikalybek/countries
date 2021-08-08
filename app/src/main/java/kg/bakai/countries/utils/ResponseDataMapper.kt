package kg.bakai.countries.utils

import com.google.gson.JsonObject
import kg.bakai.countries.model.Country

class ResponseDataMapper {
    fun transform(jsonObject: JsonObject): List<Country> {
        val list = mutableListOf<Country>()
        jsonObject.keySet().forEach {
            val jsonElement = jsonObject.getAsJsonObject(it)
            list.add(Country(jsonElement["country"].asString, jsonElement["region"].asString))
        }
        return list
    }
}