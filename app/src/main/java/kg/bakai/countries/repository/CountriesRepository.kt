package kg.bakai.countries.repository

import android.content.Context
import kg.bakai.countries.db.CountriesDao
import kg.bakai.countries.db.entities.CountryEntity
import kg.bakai.countries.network.ApiService
import kg.bakai.countries.utils.CountryDataMapper
import kg.bakai.countries.utils.ResponseDataMapper
import kg.bakai.countries.utils.Result
import kotlinx.coroutines.flow.Flow
import kz.laccent.util.awaitResult

class CountriesRepository(
    private val apiService: ApiService,
    private val dao: CountriesDao
) {
   suspend fun updateCountriesFromNetwork(limit: Int) {
       when(val response = apiService.getCountries(limit).awaitResult()) {
           is Result.Ok -> {
               val data = response.value.data
               dao.deleteAll()
               val list = ResponseDataMapper().transform(data)
               list.map {
                   val entity = CountryDataMapper().transform(it)
                   dao.insert(entity)
               }
           }
       }
   }

    val countriesFlow: Flow<List<CountryEntity>> = dao.getCountries()
 }