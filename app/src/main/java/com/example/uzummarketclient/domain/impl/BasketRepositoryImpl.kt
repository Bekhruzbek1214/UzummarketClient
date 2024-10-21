package com.example.uzummarketclient.domain.impl

import com.example.uzummarketclient.data.model.OrdersData
import com.example.uzummarketclient.data.model.ProductData
import com.example.uzummarketclient.data.sourse.MyShar
import com.example.uzummarketclient.domain.BasketRepository
import com.example.uzummarketclient.utils.myLog
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BasketRepositoryImpl @Inject constructor():BasketRepository {
    private val fireStore= Firebase.firestore
    override fun getProducts(data: List<String>): Flow<ArrayList<ProductData>> = callbackFlow {
        var size=data.size
        val products=ArrayList<ProductData>(size)
        "size $size".myLog()
        if (size==0){
            trySend(products)
        }
        var index=0
        data.forEach {
            products.add(ProductData(it,null,null,null,null,null,null))
            fireStore.collection("product")
                .document(it)
                .get().addOnSuccessListener {
                    products[index].productName = it.data?.getOrDefault("productName","Product").toString()
                    products[index].description = it.data?.getOrDefault("description","description").toString()
                    products[index].cost = it.data?.getOrDefault("value","10").toString()
                    products[index].images=it.data?.getOrDefault("images",
                        listOf("")
                    ) as List<String>
                    //produktni category null bop kolaypti
                    index++
                    "index $index".myLog()
                    if (size==index){
                        trySend(products)
                    }
                }
                .addOnFailureListener {
                    size--
                }
        }
        awaitClose()
    }

    override fun setBasket(balance:Int): Flow<Result<Unit>> = callbackFlow {
        val myBasket=MyShar.getBasket()
        val status=ArrayList<Boolean>(myBasket.productsId.size)
        for (i in 0..<myBasket.productsId.size){
            status.add(false)
        }
        fireStore.collection("orders")
            .document(UUID.randomUUID().toString())
            .set(
                OrdersData(
                    productArr = myBasket.productsId,
                    countArr = myBasket.countsArr,
                    status = status,
                    totalBalance = balance,
                    userId = MyShar.getUserData().id

                )
            )
            .addOnSuccessListener {
                "salom hehe1".myLog()
                trySend(Result.success(Unit))
                channel.close()
            }
            .addOnFailureListener {
                "salom hehe2".myLog()
                trySend(Result.failure(it))
                channel.close()
            }
        awaitClose()
    }


}
/**
 *  fun getImagesProduct(productId:String):Flow<List<Bitmap>> = callbackFlow{
 *         val data=ArrayList<Bitmap>()
 *         storageRef.child("images/$productId/")
 *             .listAll()
 *             .addOnSuccessListener {
 *                 var size=it.items.size
 *                 it.items.forEach {
 *                     it.getBytes(10*1024*1024)
 *                         .addOnSuccessListener {
 *                             size--
 *                             val bitmap= BitmapFactory.decodeByteArray(it,0,it.size)
 *                             data.add(bitmap)
 *                             if (size==0){
 *                                 trySend(data)
 *                             }
 *                         }
 *                         .addOnFailureListener {
 *
 *                         }
 *                 }
 *             }
 *             .addOnFailureListener {
 *
 *             }
 *         awaitClose()
 *     }
 */