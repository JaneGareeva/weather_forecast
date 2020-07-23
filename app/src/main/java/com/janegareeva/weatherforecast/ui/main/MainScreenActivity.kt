package com.janegareeva.weatherforecast.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.janegareeva.weatherforecast.R
import com.janegareeva.weatherforecast.app.App
import com.janegareeva.weatherforecast.db.model.CityInfo
import com.janegareeva.weatherforecast.di.component.DaggerMainScreenComponent
import com.janegareeva.weatherforecast.di.module.MainScreenModule
import com.janegareeva.weatherforecast.ui.detail.DetailActivity
import com.janegareeva.weatherforecast.ui.detail.DetailActivity.Companion.ARG_CITY_ID
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainScreenActivity : AppCompatActivity(), MainScreenContract.MainView {

    @Inject
    lateinit var presenter: MainScreenPresenter

    private lateinit var adapter: MainScreenCitiesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DaggerMainScreenComponent.builder()
            .mainScreenModule(MainScreenModule(this))
            .appComponent((application as App).appComponent)
            .build()
            .inject(this)

        adapter = MainScreenCitiesAdapter {
            val intent = Intent(this, DetailActivity::class.java).apply {
                this.putExtra(ARG_CITY_ID, it.id)
            }
            startActivity(intent)
        }
        citiesInfo.adapter = adapter
        citiesInfo.layoutManager = LinearLayoutManager(this)
        citiesInfo.addItemDecoration(
            DividerItemDecoration(
                this, LinearLayoutManager.VERTICAL
            )
        )
        add.setOnClickListener {
            presenter.loadCityInfo(addInput.text.toString())
        }
    }

    override fun showCitiesInfo(cities: List<CityInfo>) {
        adapter.setData(cities)
    }

    override fun showAddCityEnabled(isEnabled: Boolean) {
        add.isEnabled = isEnabled
    }

    override fun showProgress(show: Boolean) {
        if (show) {
            progress.show()
        } else {
            progress.hide()
        }
    }

    override fun showErrorMessage(message: String) {
        Toast.makeText(this, message, LENGTH_LONG).show()
    }
}
