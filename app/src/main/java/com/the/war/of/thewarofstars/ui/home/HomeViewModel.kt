package com.the.war.of.thewarofstars.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.the.war.of.thewarofstars.Application
import com.the.war.of.thewarofstars.model.BannerItem
import com.the.war.of.thewarofstars.model.GamerItem

class HomeViewModel : ViewModel() {

    private val _gamerList = MutableLiveData<MutableList<GamerItem>>()
    val gamerItemList: LiveData<MutableList<GamerItem>>
        get() = _gamerList

    private val _bannerList = MutableLiveData<MutableList<BannerItem>>()
    val bannerItemList: LiveData<MutableList<BannerItem>>
        get() = _bannerList

    private val TAG = "HomeViewModelLog"

    fun getGamers() {

        Application?.instance?.firebaseStore.let { firebaseDB ->
            firebaseDB?.collection("GamerList").let { gamerList ->
                gamerList?.get()
                    ?.addOnSuccessListener { collection ->
                        if (collection != null) {
                            val gamerList = mutableListOf<GamerItem>()
                            for (document in collection.documents ) {

                                gamerList.add(
                                    GamerItem(
                                        document.id,
                                        document.data?.get("name") as String,
                                        document.data?.get("price") as Long,
                                        document.data?.get("title") as String,
                                        document.data?.get("thumbnailURL") as String,
                                        document.data?.get("description") as String,
                                        document.data?.get("email") as String
                                    )
                                )

                                Log.i(TAG, "uID - ${document.id}")
                            }
                            Log.i(TAG, "gamerList - $gamerList")
                            _gamerList.value = gamerList
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
        Application?.instance?.firebaseStore.let { firebaseDB ->
            firebaseDB?.collection("Banner").let { banner ->
                banner?.get()
                    ?.addOnSuccessListener { collection ->
                        if (collection != null) {
                            val bannerList = mutableListOf<BannerItem>()
                            for (document in collection.documents ) {
                                Log.i(TAG, "${document.data}")
                                bannerList.add(
                                    BannerItem(
                                        document.data?.get("imageURL") as String,
                                        document.data?.get("gamer") as String
                                    )
                                )

                            }
                            Log.i(TAG, "bannerList - $bannerList")
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