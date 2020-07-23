package com.janegareeva.weatherforecast.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.janegareeva.weatherforecast.R
import com.janegareeva.weatherforecast.db.model.CityInfo
import kotlinx.android.synthetic.main.city_item.view.*
import kotlin.math.roundToInt

class MainScreenCitiesAdapter(private val itemClick: (CityInfo) -> Unit): androidx.recyclerview.widget.RecyclerView.Adapter<MainScreenCitiesAdapter.CityViewHolder>() {

    private var data: List<CityInfo> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.city_item, parent,
            false)
        return CityViewHolder(v)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bind(data[position])
        holder.itemView.root.setOnClickListener {
            itemClick.invoke(data[position])
        }
    }

    override fun getItemCount(): Int = data.size

    fun setData(newData: List<CityInfo>) {
        data = newData
        notifyDataSetChanged()
    }

    class CityViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        fun bind(item: CityInfo) {
            itemView.name.text = item.name
            itemView.details.text = "current temperature: ${item.temp.roundToInt()} C, pressure: ${item.pressure}"
        }
    }
}