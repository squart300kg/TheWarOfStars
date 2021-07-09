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

    private val _gamerList = MutableLiveData<MutableList<Gamer>>()
    val gamerList: LiveData<MutableList<Gamer>>
        get() = _gamerList

    private val TAG = "HomeViewModelLog"

    fun getGamers() {

        Application?.instance?.firebaseDB.let { firebaseDB ->
            firebaseDB?.collection("GamerList").let { gamerList ->
                gamerList?.get()
                    ?.addOnSuccessListener { collection ->
                        if (collection != null) {
                            var gamerList = mutableListOf<Gamer>()
                            for (document in collection.documents ) {
                                Log.i(TAG, "${document.data}")
                                gamerList.add(
                                    Gamer(
                                        document.data?.get("name") as String,
                                        document.data?.get("price") as Long,
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

    }
}