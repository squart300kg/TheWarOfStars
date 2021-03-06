package com.the.war.of.thewarofstars.ui.login


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.securepreferences.SecurePreferences
import com.the.war.of.thewarofstars.Application
import com.the.war.of.thewarofstars.base.BaseViewModel
import com.the.war.of.thewarofstars.contant.CollectionType
import com.the.war.of.thewarofstars.contant.UserType
import com.the.war.of.thewarofstars.model.User
import com.the.war.of.thewarofstars.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException

class LoginViewModel(
    private val securePreferences: SecurePreferences,
    private val loginRepository: LoginRepository
): BaseViewModel(securePreferences) {

    private val _email = MutableLiveData<String>()
    val email: LiveData<String>
        get() = _email

    private val _nickname = MutableLiveData<String>()
    val nickname: LiveData<String>
        get() = _nickname

    private val _uID = MutableLiveData<String>()
    val uID: LiveData<String>
        get() = _uID

    private val _naverApiStatus = MutableLiveData<Boolean>()
    val naverApiStatus: LiveData<Boolean>
        get() = _naverApiStatus

    private val _isRegister = MutableLiveData<Boolean>()
    val isRegister: LiveData<Boolean>
        get() = _isRegister

    private val _isTermsChecked = MutableLiveData<Boolean>()
    val isTermsChecked: LiveData<Boolean>
        get() = _isTermsChecked

    private val _isConfirmed = MutableLiveData<Boolean>()
    val isConfirmed: LiveData<Boolean>
        get() = _isConfirmed

    val TAG = "LoginViewModelLog"

    fun requestNaverUserInfo(accessToken: String) {
        job?.cancel()
        job = viewModelScope.launch {

            loginRepository.getNaverUserInfo(accessToken)
                .flowOn(Dispatchers.IO)
                .catch { exception ->
                    if(exception is HttpException) {
                        val errorJson = JSONObject(exception.response()?.errorBody()?.string())
                        _errorCode.value = errorJson.getString("code")
                        _errorMsg.value  = errorJson.getString("message")
                    }
                    Log.i(TAG, "fail-${_errorCode.value} - ${_errorMsg.value}")
                }
                .collect { naverUserInfoResponse ->
                    val response    = JSONObject(naverUserInfoResponse).getJSONObject("response")
                    _email.value    = response.getString("email")
                    _nickname.value = response.getString("nickname")
                    _naverApiStatus.value = true

                    Log.i(TAG, "result : ${email.value}, ${nickname.value}")
                }
        }

    }

    fun isRegister(email: String?) {

        Log.i(TAG, "inputEmail : $email")
        Application.instance?.firebaseStore?.collection(CollectionType.USER_LIST.type)
                ?.whereEqualTo("email", email)
                    ?.get()
                    ?.addOnSuccessListener { documents ->
                        for (document in documents) {

                            _uID.value        = document.id
                            _isRegister.value = true

                            return@addOnSuccessListener
                        }
                        _isRegister.value = false
                    }
    }

    fun registerUser() {

        Application.instance?.firebaseStore
            ?.collection(CollectionType.USER_LIST.type)
            ?.document(email.value.toString())
            ?.set(
                User(
                    email    = _email.value as String,
                    nickname = _nickname.value as String,
                    fcmToken = Application.instance?.userFcmToken as String
                )
            )
            ?.addOnSuccessListener {
                Log.i(TAG, "???????????? ?????? ??????! email : ${_email.value}, nickname : ${_nickname.value}")
                _isRegister.value = true
            }
    }

    fun isTermsChecked(isChecked: Boolean) {
        _isTermsChecked.value = isChecked
    }

    fun logout() {
        val collectionName = if (Application.instance?.userType == UserType.USER.type) CollectionType.USER_LIST.type else CollectionType.GAMER_LIST.type
        Log.i(TAG, "collectionName : $collectionName")

        Application.instance?.firebaseStore
            ?.collection(collectionName)
            ?.document(uID.value.toString())
            ?.update("fcmToken", "")
            ?.addOnSuccessListener {
                Log.d(TAG, "fcmToken ???????????? ??????")
                _isConfirmed.value = true
            }
            ?.addOnFailureListener { e ->
                Log.w(TAG, "fcmTOken ???????????? ??????", e)
            }
    }

}