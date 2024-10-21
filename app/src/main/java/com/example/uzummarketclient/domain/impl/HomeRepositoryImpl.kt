package com.example.uzummarketclient.domain.impl
import android.graphics.Bitmap
import android.util.Log
import com.example.uzummarketclient.data.model.CategoryByProductData
import com.example.uzummarketclient.data.model.ProductByMainData
import com.example.uzummarketclient.domain.HomeRepository
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepositoryImpl @Inject constructor() : HomeRepository {
    private var category : CategoryByProductData? = null
    private val fireStore = Firebase.firestore
    private val allCategoryByProductData = ArrayList<CategoryByProductData>()
    private val allProductsData = ArrayList<ProductByMainData>()
    override fun getAllCategoryByProduct(): Flow<Result<List<Pair<String, String>>>> = callbackFlow {
        val data=ArrayList<Pair<String, String>>()
        fireStore.collection("category")
            .get().addOnSuccessListener {
                var size=it.size()
                it.forEach {
                    size--
                    val categoryId=it.id
                    val categoryName=it.data?.getOrDefault("categoryName","Ali").toString()
                    data.add(Pair(categoryId,categoryName))
                    if (size==0){
                        trySend(Result.success(data))
                    }
                }
            }
        awaitClose()
    }
    override fun getProductInCategory(categoryid: String, categoryName: String): Flow<ArrayList<ProductByMainData>> = callbackFlow{
        val productData= ArrayList<ProductByMainData>()
        var index1=0
        var index2=0
        fireStore.collection("category")
            .document(categoryid)
            .get().addOnSuccessListener {
                fireStore.collection("product")
                    .whereEqualTo("categoryId",it.id)
                    .get().addOnSuccessListener {
                        val size=it.size()
                        it.forEach {
                            val prod=it.id
                            productData.add(ProductByMainData(prod,null, null, null, null, null, null))
                            fireStore.collection("product")
                                .document(prod)
                                .get().addOnSuccessListener {
                                    val sellerId=it.data?.getOrDefault("sellerId","0").toString()
                                    productData[index1].productName = it.data?.getOrDefault("productName","Product").toString()
                                    productData[index1].description = it.data?.getOrDefault("description","description").toString()
                                    productData[index1].cost = it.data?.getOrDefault("value","10").toString()
                                    productData[index1].category =categoryName
                                    productData[index1].images=it.data?.getOrDefault("images",
                                        listOf("")
                                    ) as List<String>
                                    index1++
                                    fireStore.collection("users")
                                        .document(sellerId)
                                        .get().addOnSuccessListener {
                                            productData[index2].sellerName=it.data?.getOrDefault("name","Ali").toString()
                                            index2++
                                            if (index2==size){
                                                trySend(productData)
                                            }
                                        }
                                }
                                .addOnFailureListener {

                                }
                        }
                    }
                }

        awaitClose()
    }

    override fun setCategory(category: CategoryByProductData) {
        this.category = category
    }

    override fun getCategory(): CategoryByProductData {
        return  category!!
    }

    override fun getProductsByName(name: String): List<ProductByMainData> {
        if(name.isEmpty()) return arrayListOf()
        val list = allProductsData.filter {
            Log.d("BAHA", "getProductsByName: productName ${it.productName} and name $name")
            it.productName?.lowercase()?.contains(name.lowercase()) ?: false
        }
        Log.d("BAHA", "getProductsByName: $list")
        return list
    }

    override fun setAllCategoryByProductsData(list: List<CategoryByProductData>) {
        allCategoryByProductData.clear()
        allCategoryByProductData.addAll(list)
        allProductsData.clear()
        for (i in allCategoryByProductData.indices) {
            allProductsData.addAll(allCategoryByProductData[i].productsList)
        }
    }
}