package com.janegareeva.weatherforecast.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleRegistry
import androidx.recyclerview.widget.LinearLayoutManager
import com.janegareeva.weatherforecast.R
import com.janegareeva.weatherforecast.app.App
import com.janegareeva.weatherforecast.db.model.CityInfo
import com.janegareeva.weatherforecast.di.component.DaggerMainScreenComponent
import com.janegareeva.weatherforecast.di.module.MainScreenModule
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import javax.inject.Inject

class MainScreenActivity : AppCompatActivity(), MainScreenContract.MainView {

    @Inject
    lateinit var presenter: MainScreenPresenter

    lateinit var adapter: MainScreenCitiesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DaggerMainScreenComponent.builder()
            .mainScreenModule(MainScreenModule(this))
            .appComponent((application as App).appComponent)
            .build()
            .inject(this)

        //presenter.loadCitiesInfo()
        adapter = MainScreenCitiesAdapter()
        citiesInfo.adapter = adapter
        citiesInfo.layoutManager = LinearLayoutManager(this)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun showCitiesInfo(cities: List<CityInfo>) {
        showProgress(false)
        adapter.setData(cities)
    }

    override fun showProgress(show: Boolean) {
        if (show) {
            progress.show()
        } else {
            progress.hide()
        }
    }

    override fun showErrorMessage(message: String) {
        showProgress(false)
       Toast.makeText(this, message, LENGTH_LONG).show()
    }
}
