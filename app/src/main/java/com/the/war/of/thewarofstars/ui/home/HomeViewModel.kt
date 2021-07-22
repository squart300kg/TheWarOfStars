package com.the.war.of.thewarofstars.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.the.war.of.thewarofstars.Application
import com.the.war.of.thewarofstars.model.Banner
import com.the.war.of.thewarofstars.model.Gamer

class HomeViewModel : ViewModel() {

    private val _gamerList = MutableLiveData<MutableList<Gamer>>()
    val gamerList: LiveData<MutableList<Gamer>>
        get() = _gamerList

    private val _bannerList = MutableLiveData<MutableList<Banner>>()
    val bannerList: LiveData<MutableList<Banner>>
        get() = _bannerList

    private val TAG = "HomeViewModelLog"

    fun getGamers() {

        Application?.instance?.firebaseDB.let { firebaseDB ->
            firebaseDB?.collection("GamerList").let { gamerList ->
                gamerList?.get()
                    ?.addOnSuccessListener { collection ->
                        if (collection != null) {
                            val gamerList = mutableListOf<Gamer>()
                            for (document in collection.documents ) {

                                gamerList.add(
                                    Gamer(
                                        document.data?.get("name") as String,
                                        document.data?.get("price") as Long,
                                        document.data?.get("title") as String,
                                        document.data?.get("thumbnailURL") as String,
                                        document.data?.get("description") as String,
                                        null
                                    )
                                )

                                _gamerList.value = gamerList
                            }
                        } else {
                            Log.d(TAG, "No such document")
                        }
                    }
                    ?.addOnFailureListener { exception ->
                        Log.d(TAG, "get failed with ", exception)
                    }

            }
        }

    }

    fun getBanners() {
        Application?.instance?.firebaseDB.let { firebaseDB ->
            firebaseDB?.collection("Banner").let { banner ->
                banner?.get()
                    ?.addOnSuccessListener { collection ->
                        if (collection != null) {
                            val bannerList = mutableListOf<Banner>()
                            for (document in collection.documents ) {
                                Log.i(TAG, "${document.data}")
                                bannerList.add(
                                    Banner(
                                        document.data?.get("imageURL") as String,
                                        document.data?.get("gamer") as String
                                    )
                                )

                            }
                            _bannerList.value = bannerList
                        }
                    }
                    ?.addOnFailureListener { exception ->
                        Log.d(TAG, "get failed with ", exception)
                    }

            }


        }

    }
}