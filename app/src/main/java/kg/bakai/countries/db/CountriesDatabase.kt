package kg.bakai.countries.db

import androidx.room.Database
import androidx.room.RoomDatabase
import kg.bakai.countries.db.entities.CountryEntity

@Database(
    entities = [ CountryEntity::class ],
    version = 3,
    exportSchema = false
)
abstract class CountriesDatabase: RoomDatabase() {
    abstract val countriesDao: CountriesDao
}