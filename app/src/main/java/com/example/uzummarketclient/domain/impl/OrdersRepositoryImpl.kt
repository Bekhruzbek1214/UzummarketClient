package com.example.uzummarketclient.domain.impl

import com.example.uzummarketclient.data.sourse.MyShar
import com.example.uzummarketclient.domain.OrdersRepository
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrdersRepositoryImpl @Inject constructor() : OrdersRepository {
    private val fireStore = Firebase.firestore
    override fun getActiveMyOrder(): Flow<List<Pair<Int, ArrayList<String>>>> = callbackFlow {
        val data = ArrayList<Pair<Int, ArrayList<String>>>()
        fireStore.collection("orders")
            .whereEqualTo("userId", MyShar.getUserData().id)
            .get().addOnSuccessListener {
                val size = it.size()
                var index = 0
                it.forEach {
                    val status=it.data?.getOrDefault("status", listOf(false)) as List<Boolean>
                    if (status.contains(false)){
                        val productArr = it.data?.getOrDefault("productArr", listOf("")) as ArrayList<String>
                        val totalBalance = it.data?.getOrDefault("totalBalance", 0).toString().toInt()
                        data.add(Pair(totalBalance, productArr))
                    }
                    index++
                    if (size == index) {
                        trySend(data)
                    }
                }
            }
            .addOnFailureListener {
                trySend(data)
            }
        awaitClose()
    }
    override fun getAllMyOrder(): Flow<List<Pair<Int, ArrayList<String>>>> = callbackFlow {
        val data = ArrayList<Pair<Int, ArrayList<String>>>()
        fireStore.collection("orders")
            .whereEqualTo("userId", MyShar.getUserData().id)
            .get().addOnSuccessListener {
                val size = it.size()
                var index = 0
                it.forEach {
                    val status=it.data?.getOrDefault("status", listOf(false)) as List<Boolean>
                    if (!status.contains(false)){
                        val productArr = it.data?.getOrDefault("productArr", listOf("")) as ArrayList<String>
                        val totalBalance = it.data?.getOrDefault("totalBalance", 0).toString().toInt()
                        data.add(Pair(totalBalance, productArr))
                    }
                    index++
                    if (size == index) {
                        trySend(data)
                    }
                }
            }
            .addOnFailureListener {
                trySend(data)
            }
        awaitClose()
    }

    override fun getProductName(productsId: List<String>): Flow<ArrayList<String>> = callbackFlow {
        val data = ArrayList<String>()
        val size = productsId.size
        var index = 0
        productsId.forEach { productId ->
            fireStore.collection("product")
                .document(productId)
                .get().addOnSuccessListener {
                    index++
                    val productName =
                        (it.data?.getOrDefault("productName", "Ali") ?: "Vali").toString()
                    data.add(productName)
                    if (index == size) {
                        trySend(data)
                    }
                }
                .addOnFailureListener {
                    index++
                }
        }
        awaitClose()
    }
}