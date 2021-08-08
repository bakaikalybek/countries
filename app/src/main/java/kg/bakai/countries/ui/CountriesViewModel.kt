package kg.bakai.countries.ui

import androidx.lifecycle.*
import kg.bakai.countries.db.entities.CountryEntity
import kg.bakai.countries.repository.CountriesRepository
import kotlinx.coroutines.launch

class CountriesViewModel(private val repository: CountriesRepository): ViewModel() {
    private val scope = viewModelScope

    val countries: LiveData<List<CountryEntity>> = repository.countriesFlow.asLiveData()

    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status

    fun getCountriesFromNetwork(limit: Int) {
        scope.launch {
            _status.value = "LOADING"
            repository.updateCountriesFromNetwork(limit)
            _status.value = "COMPLETED"
        }
    }
}