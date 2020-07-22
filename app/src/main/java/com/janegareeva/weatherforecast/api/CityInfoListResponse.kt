package com.janegareeva.weatherforecast.api

import com.google.gson.annotations.SerializedName

data class CityInfoListResponse(@SerializedName("list") val list: List<CityInfoResponse>)