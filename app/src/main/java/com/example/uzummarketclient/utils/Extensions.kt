package com.example.uzummarketclient.utils

import android.util.Log
import android.widget.Toast
import com.example.uzummarketclient.data.model.UserData
import com.google.firebase.firestore.QuerySnapshot
import android.content.Context


fun String.myLog() = Log.d("TTT", this)
fun String.onlyLetters() = all { it.isLetter() }
fun String.myShortToast(context: Context){
    Toast.makeText(context,this , Toast.LENGTH_SHORT).show()
}



object Mapper {
    fun QuerySnapshot.toUserDataList(): List<UserData> {
        val userList = mutableListOf<UserData>()
        for (document in documents) {
            val id = document.id
            val name = document.getString("name") ?: ""
            val gmail = document.getString("gmail") ?: ""
            val password = document.getString("password") ?: ""
            val type = document.getString("type") ?: ""
            val userData = UserData(id,name, gmail, password, type)
            userList.add(userData)
        }
        return userList
    }
}
