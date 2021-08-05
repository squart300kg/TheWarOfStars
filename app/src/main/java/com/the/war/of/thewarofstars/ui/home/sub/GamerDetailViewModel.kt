package com.the.war.of.thewarofstars.ui.home.sub

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.Timestamp
import com.securepreferences.SecurePreferences
import com.the.war.of.thewarofstars.Application
import com.the.war.of.thewarofstars.base.BaseViewModel
import com.the.war.of.thewarofstars.model.ReviewItem

class GamerDetailViewModel(
    private val securePreferences: SecurePreferences
): BaseViewModel(securePreferences) {

    private val _reviewList = MutableLiveData<MutableList<ReviewItem>>()
    val reviewList: LiveData<MutableList<ReviewItem>>
        get() = _reviewList

    private val _reviewCount = MutableLiveData<Int>()
    val reviewCount: LiveData<Int>
        get() = _reviewCount

    val TAG = "GamerDetailViewModelLog"

    fun getReviews(gamer: String?) {
        Application?.instance?.firebaseStore.let { firebaseDB ->
            firebaseDB?.collection("ReviewList")
                ?.whereEqualTo("gamer", gamer)
                ?.get()
                ?.addOnSuccessListener { documents ->
                    val reviewList = mutableListOf<ReviewItem>()
                    for (document in documents) {
                        reviewList.add(
                            ReviewItem(
                                document.data?.get("content") as String,
                                document.data?.get("date") as Timestamp,
                                document.data?.get("gamer") as String,
                                document.data?.get("nickname") as String,
                                document.data?.get("score") as Long
                            )
                        )
                    }
                    Log.i(TAG, "reviewList : $reviewList")
                    _reviewList.value = reviewList
                    _reviewCount.value = reviewList.size
                }
        }
    }


}