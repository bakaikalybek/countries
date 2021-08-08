package kg.bakai.countries.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kg.bakai.countries.db.entities.CountryEntity
import kg.bakai.countries.model.Country
import kotlinx.coroutines.flow.Flow

@Dao
interface CountriesDao {

    @Query("SELECT * FROM country")
    fun getCountries(): Flow<List<CountryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(country: CountryEntity)

    @Query("DELETE FROM country")
    suspend fun deleteAll()
}