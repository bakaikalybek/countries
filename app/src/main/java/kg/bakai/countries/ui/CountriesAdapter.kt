package kg.bakai.countries.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kg.bakai.countries.R
import kg.bakai.countries.db.entities.CountryEntity

class CountriesAdapter(
    private val onCountryClick: (country: String, region: String) -> Unit
): RecyclerView.Adapter<CountriesAdapter.ViewHolder>() {
    private var list = mutableListOf<CountryEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountriesAdapter.ViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.rv_item, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CountriesAdapter.ViewHolder, position: Int) {
        val item = list[position]
        holder.apply {
            country.text = item.country
            region.text = item.region
            main.setOnClickListener {
                onCountryClick(item.country, item.region)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun submit(countries: List<CountryEntity>) {
        list.clear()
        list.addAll(countries)
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val country: TextView = view.findViewById(R.id.tv_country)
        val region: TextView = view.findViewById(R.id.tv_region)
        val main: LinearLayout = view.findViewById(R.id.main_view)
    }
}