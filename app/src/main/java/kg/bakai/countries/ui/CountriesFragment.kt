package kg.bakai.countries.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kg.bakai.countries.databinding.FragmentCountriesBinding
import kg.bakai.countries.utils.ConnectionManager
import org.koin.androidx.viewmodel.ext.android.viewModel

class CountriesFragment : Fragment() {
    private var page = 10

    private lateinit var binding: FragmentCountriesBinding
    private val countriesViewModel by viewModel<CountriesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCountriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = CountriesAdapter { country, region -> onCountryClick(country, region) }
        fetchCountries(page)
        binding.apply {
            swipeLayout.setOnRefreshListener {
                fetchCountries(page)
            }
            rvCountries.adapter = adapter
            countriesViewModel.countries.observe(viewLifecycleOwner) { resource ->
                if (resource.isNotEmpty()) {
                    adapter.submit(resource)
                }
            }
            countriesViewModel.status.observe(viewLifecycleOwner) { status ->
                when (status) {
                    "LOADING" -> {
                        swipeLayout.isRefreshing = true
                        page += 10
                    }
                    "COMPLETED" -> {
                        swipeLayout.isRefreshing = false
                    }
                }
            }
        }
    }

    private fun fetchCountries(limit: Int) {
        if (ConnectionManager().isNetworkAvailable(requireContext())) {
            countriesViewModel.getCountriesFromNetwork(limit)
        } else {
            Toast.makeText(requireContext(), "Отсутствует интернет соединение", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onCountryClick(country: String, region: String) {
        Toast.makeText(requireContext(), "Country: $country, Region: $region", Toast.LENGTH_SHORT).show()
    }
}