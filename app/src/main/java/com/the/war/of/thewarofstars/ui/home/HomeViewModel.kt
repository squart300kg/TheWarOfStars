package com.the.war.of.thewarofstars.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.the.war.of.thewarofstars.Application
import com.the.war.of.thewarofstars.model.Gamer

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment!!!!"
    }
    val text: LiveData<String> = _text

    private val _gamerList = MutableLiveData<ArrayList<Gamer>>()
    val gamerList
        get() = _gamerList

    private val TAG = "HomeViewModelLog"

    fun getGamerList() {
        val db = Application?.instance?.firebaseDB

        val gamerList = db?.collection("GamerList")
        gamerList?.get()
            ?.addOnSuccessListener { collection ->
                if (collection != null) {
                    for (document in collection.documents ) {
                        Log.i(TAG, "${document.data}")
                        var gamerList = ArrayList<Gamer>()
                        gamerList.add(
                            Gamer(
                                document.data?.get("name") as String,
                                document.data?.get("price") as Int,
                                document.data?.get("title") as String,
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