package kg.bakai.countries.utils

import kg.bakai.countries.db.entities.CountryEntity
import kg.bakai.countries.model.Country

class CountryDataMapper {
    fun transform(model: Country): CountryEntity {
        return CountryEntity(country = model.country, region = model.region)
    }
}