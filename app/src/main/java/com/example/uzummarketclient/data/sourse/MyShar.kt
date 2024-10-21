package com.example.uzummarketclient.data.sourse

import android.content.Context
import android.content.SharedPreferences
import com.example.uzummarketclient.data.model.BasketData
import com.example.uzummarketclient.data.model.UserData
import kotlin.math.sign

object MyShar {
    private lateinit var  sharedPreferences:SharedPreferences
    fun init(context: Context){
        sharedPreferences= context.getSharedPreferences("Uzum_Market",Context.MODE_PRIVATE)
    }
    fun setUserData(userData: UserData){
        sharedPreferences.edit().putString("id",userData.id).apply()
        sharedPreferences.edit().putString("name",userData.name).apply()
        sharedPreferences.edit().putString("gmail",userData.gmail).apply()
        sharedPreferences.edit().putString("password",userData.password).apply()
        sharedPreferences.edit().putString("type",userData.type).apply()
        sharedPreferences.edit().putBoolean("login",true).apply()
    }
    fun getUserData():UserData{
        val id= sharedPreferences.getString("id","")?:""
        val name= sharedPreferences.getString("name","")?:""
        val gmail= sharedPreferences.getString("gmail","")?:""
        val password= sharedPreferences.getString("password","")?:""
        val type= sharedPreferences.getString("type","")?:""
        return UserData(id,name,gmail,password,type)
    }
    fun isLogin():Boolean= sharedPreferences.getBoolean("login",false)
    fun logOut(){
        sharedPreferences.edit().putString("name","").apply()
        sharedPreferences.edit().putString("gmail","").apply()
        sharedPreferences.edit().putString("password","").apply()
        sharedPreferences.edit().putString("type","").apply()
        sharedPreferences.edit().putBoolean("login",false).apply()
    }
    fun setBasket(data:BasketData){
        sharedPreferences.edit().putInt("sizeBasket",data.productsId.size).apply()
        for (i in 0..<data.productsId.size){
            sharedPreferences.edit().putString("productsId$i",data.productsId[i]).apply()
            sharedPreferences.edit().putInt("countsArr$i",data.countsArr[i]).apply()
        }
        sharedPreferences.edit().putBoolean("isHasBasket",true).apply()
    }
    fun getBasket():BasketData{
        if (!isHasBasket())return BasketData(arrayListOf(), arrayListOf())
        val size = sharedPreferences.getInt("sizeBasket",0)
        val productsId=ArrayList<String>(size)
        val countsArr=ArrayList<Int>(size)
        for (i in 0..<size){
            productsId.add(sharedPreferences.getString("productsId$i","")?:"")
            countsArr.add(sharedPreferences.getInt("countsArr$i",0))
        }
        return BasketData(productsId,countsArr)
    }
    fun isHasBasket():Boolean= sharedPreferences.getBoolean("isHasBasket",false)

    fun deleteProductInBasket(productId:String):Boolean{
        val basket= getBasket()
        if (!isHasBasket())return false
        for (i in 0..<basket.productsId.size){
            if (productId==basket.productsId[i]){
                basket.productsId.removeAt(i)
                basket.countsArr.removeAt(i)
                setBasket(basket)
                return true
            }
        }
        return false
    }
    fun deleteBasket(){
        sharedPreferences.edit().putBoolean("isHasBasket",false).apply()
    }

}