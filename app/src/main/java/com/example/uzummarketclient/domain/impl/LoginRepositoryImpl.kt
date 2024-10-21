package com.example.uzummarketclient.domain.impl

import com.example.uzummarketclient.data.sourse.MyShar
import com.example.uzummarketclient.domain.LoginRepository
import com.example.uzummarketclient.utils.Mapper
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepositoryImpl @Inject constructor() : LoginRepository {
    private val fireStore = Firebase.firestore
    override fun loginUser(password: String, gmail: String): Flow<Result<Boolean>> = callbackFlow {
        fireStore.collection("user")
            .whereEqualTo("password", password)
            .whereEqualTo("gmail", gmail)
            .whereEqualTo("type", "client")
            .limit(1)
            .get().addOnSuccessListener {
                if (it.size() == 0) {
                    trySend(Result.failure(Throwable("Bunaqa user mavjud emas !")))
                } else {
                    val user = Mapper.run { it.toUserDataList()[0] }
                    MyShar.setUserData(user)
                    trySend(Result.success(true))
                    channel.close()
                }
            }
            .addOnFailureListener {
                trySend(Result.success(false))
                channel.close()
            }
        awaitClose()
    }

}