package com.example.uzummarketclient.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryByProductData(
    val categoryId : String,
    val categoryName : String,
    val productsList : List<ProductByMainData>
) : Parcelable
