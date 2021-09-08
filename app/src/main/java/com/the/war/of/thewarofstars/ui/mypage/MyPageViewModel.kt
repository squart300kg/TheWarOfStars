package com.the.war.of.thewarofstars.ui.mypage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.securepreferences.SecurePreferences
import com.the.war.of.thewarofstars.Application
import com.the.war.of.thewarofstars.base.BaseViewModel
import com.the.war.of.thewarofstars.contant.CollectionType
import com.the.war.of.thewarofstars.contant.UserType
import com.the.war.of.thewarofstars.ui.login.LoginViewModel
import org.koin.java.KoinJavaComponent.inject

class MyPageViewModel: ViewModel() {

    val securePreferences: SecurePreferences by inject(SecurePreferences::class.java)

    private val _text = MutableLiveData<String>()
    val text: LiveData<String>
        get() = _text

    private val _isConfirmed = MutableLiveData<Boolean>()
    val isConfirmed: LiveData<Boolean>
        get() = _isConfirmed

    private val TAG = "MyPageViewModelLog"

    fun logout() {
        val collectionName = if (Application.instance?.userType == UserType.USER.type) CollectionType.USER_LIST.type else CollectionType.GAMER_LIST.type
        val uID = Application.instance?.userUID.toString()
        Log.i(TAG, "collectionName : $collectionName, uID : $uID")

        Application.instance?.firebaseStore
            ?.collection(collectionName)
            ?.document(uID)
            ?.update("fcmToken", "")
            ?.addOnSuccessListener {
                Log.d(TAG, "fcmToken 업데이트 완료")
                Application.instance?.userUID      = null
                Application.instance?.userEmail    = null
                Application.instance?.userName     = null
//                Application.instance?.userFcmToken = null
                Application.instance?.userType     = null
                Application.instance?.userGameID   = null
                Application.instance?.userTribe    = null

                _isConfirmed.value = true
            }
            ?.addOnFailureListener { e ->
                Log.w(TAG, "fcmTOken 업데이트 실패", e)
            }
    }

    fun deleteAutoLogin() {
        securePreferences.edit().putUnencryptedString("autoLoginStatus", "false").commit()
        securePreferences.edit().putUnencryptedString("email", null).commit()
        securePreferences.edit().putUnencryptedString("name", null).commit()
        securePreferences.edit().putUnencryptedString("uID", null).commit()
        securePreferences.edit().putUnencryptedString("password", null).commit()
        securePreferences.edit().putUnencryptedString("type", null).commit()
        securePreferences.edit().putUnencryptedString("tribe", null).commit()
        securePreferences.edit().putUnencryptedString("gameID", null).commit()
    }

}