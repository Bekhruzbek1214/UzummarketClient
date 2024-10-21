package com.example.uzummarketclient.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MyOrdersData(
    val productsName: List<String>,
    val totalBalanse:Int,
):Parcelable