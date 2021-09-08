package com.the.war.of.thewarofstars.ui.mypage

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.the.war.of.thewarofstars.Application
import com.the.war.of.thewarofstars.R
import com.the.war.of.thewarofstars.base.BaseFragment
import com.the.war.of.thewarofstars.databinding.FragmentMypageBinding
import com.the.war.of.thewarofstars.ui.home.sub.pay.PayCompleteActivity
import com.the.war.of.thewarofstars.ui.login.LoginActivity
import com.the.war.of.thewarofstars.ui.login.LoginViewModel
import com.the.war.of.thewarofstars.ui.mypage.sub.ConvertActivity

class MyPageFragment : BaseFragment<FragmentMypageBinding>(R.layout.fragment_mypage) {

    private val myPageViewModel: MyPageViewModel by viewModels()
//    private val loginViewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeValues()

        binding {

            myPageVm = myPageViewModel

            tvRequestConvert.apply {
                setOnClickListener {
                    Intent(requireActivity(), ConvertActivity::class.java).apply {
                        startActivity(this)
                    }
                }
            }

            tvSellPage.apply {
                setOnClickListener {
                    Intent(requireActivity(), PayCompleteActivity::class.java).apply {
                        startActivity(this)
                    }
                }
            }

            tvLogout.apply {
                setOnClickListener {
                    //TODO loginViewModel에 넣을 수 있도록 고려할 것
//                    loginViewModel.logout()
                    myPageViewModel.logout()
                }
            }
        }

//        observingForLogin {
//            isConfirmed.observe(requireActivity(), { isConfirmed ->
//                when (isConfirmed) {
//                    true -> {
//
//                        deleteAutoLogin()
//
//                        Application.instance?.userUID      = null
//                        Application.instance?.userName     = null
//                        Application.instance?.userEmail    = null
//                        Application.instance?.userType     = null
//                        Application.instance?.userFcmToken = null
//
//                        goNext(LoginActivity::class.java)
//                    }
//
//                }
//            })
//        }

        observingForMyPage {
            isConfirmed.observe(requireActivity(), { isConfirmed ->
                when (isConfirmed) {
                    true -> {
                        deleteAutoLogin()

                        goNext(LoginActivity::class.java)
                    }

                }
            })
        }
    }

    private fun initializeValues() {
        binding.tvEmail.text  = Application.instance?.userEmail
        binding.tvTribe.text  = Application.instance?.userTribe
        binding.tvGameId.text = Application.instance?.userGameID
    }


//    private fun observingForLogin(action: LoginViewModel.() -> Unit) {
//        loginViewModel.run(action)
//    }

    private fun observingForMyPage(action: MyPageViewModel.() -> Unit) {
        myPageViewModel.run(action)
    }

    private fun goNext(activity: Class<*>) {
        val intent = Intent(requireActivity(), activity)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        requireActivity().finish()
    }



}