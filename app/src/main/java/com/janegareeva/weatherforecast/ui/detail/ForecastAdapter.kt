package com.janegareeva.weatherforecast.ui.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.janegareeva.weatherforecast.R
import com.janegareeva.weatherforecast.db.model.CityForecast
import kotlinx.android.synthetic.main.forecast_item.view.*
import kotlin.math.roundToInt

class ForecastAdapter:
    androidx.recyclerview.widget.RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {

    private var data: List<CityForecast> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.forecast_item, parent,
            false
        )
        return ForecastViewHolder(v)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    fun setData(newData: List<CityForecast>) {
        data = newData
        notifyDataSetChanged()
    }

    class ForecastViewHolder(itemView: View) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        fun bind(item: CityForecast) {
            itemView.date.text = item.time
            itemView.temperature.text = "temperature: ${item.temp.roundToInt()} C"
            itemView.pressure.text = "pressure: ${item.pressure}"
        }
    }
}