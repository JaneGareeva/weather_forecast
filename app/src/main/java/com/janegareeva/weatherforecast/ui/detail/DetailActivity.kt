package com.janegareeva.weatherforecast.ui.detail

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.janegareeva.weatherforecast.R
import com.janegareeva.weatherforecast.app.App
import com.janegareeva.weatherforecast.db.model.CityForecast
import com.janegareeva.weatherforecast.db.model.CityInfo
import com.janegareeva.weatherforecast.di.component.DaggerDetailScreenComponent
import com.janegareeva.weatherforecast.di.module.DetailScreenModule
import kotlinx.android.synthetic.main.activity_detail.*
import javax.inject.Inject
import kotlin.math.roundToInt

class DetailActivity : AppCompatActivity(), DetailScreenContract.View {

    companion object {
        const val ARG_CITY_ID = "id"
    }

    @Inject
    lateinit var presenter: DetailsPresenter
    private lateinit var adapter: ForecastAdapter

    override val cityId: String
        get() = intent.getStringExtra(ARG_CITY_ID) ?: ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        DaggerDetailScreenComponent.builder()
            .detailScreenModule(DetailScreenModule(this))
            .appComponent((application as App).appComponent)
            .build()
            .inject(this)
        adapter = ForecastAdapter()
        forecastInfo.layoutManager = LinearLayoutManager(this)
        forecastInfo.adapter = adapter
        forecastInfo.addItemDecoration(
            DividerItemDecoration(
                this, LinearLayoutManager.VERTICAL
            )
        )
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showCityInfo(city: CityInfo) {
        name.text = city.name
        details.text = "current temperature: ${city.temp.roundToInt()} C, ${city.description},  " +
                "pressure: ${city.pressure}, wind ${city.wind} m/c"
    }

    override fun showErrorMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun showForecast(cityForecast: List<CityForecast>) {
        adapter.setData(cityForecast)
    }

    override fun showProgress(show: Boolean) {
        if (show) {
            progress.show()
        } else {
            progress.hide()
        }
    }
}
