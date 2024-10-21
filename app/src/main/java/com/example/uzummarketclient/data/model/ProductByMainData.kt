package com.example.uzummarketclient.data.model

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductByMainData(
    var id: String,
    var images:List<String>?,
    var category: String?,
    var description: String?,
    var productName: String?,
    var sellerName: String?,
    var cost: String?
) : Parcelable