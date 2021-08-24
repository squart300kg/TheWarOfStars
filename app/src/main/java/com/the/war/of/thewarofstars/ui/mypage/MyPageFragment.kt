package com.the.war.of.thewarofstars.ui.mypage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.the.war.of.thewarofstars.Application
import com.the.war.of.thewarofstars.MainActivity
import com.the.war.of.thewarofstars.R
import com.the.war.of.thewarofstars.base.BaseFragment
import com.the.war.of.thewarofstars.databinding.FragmentHomeBinding
import com.the.war.of.thewarofstars.databinding.FragmentMypageBinding
import com.the.war.of.thewarofstars.ui.login.EmailLoginViewModel
import com.the.war.of.thewarofstars.ui.login.LoginActivity

class MyPageFragment : BaseFragment<FragmentMypageBinding>(R.layout.fragment_mypage) {

    private val myPageViewModel: MyPageViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding {

            myPageVm = myPageViewModel

//            tvLogout.apply {
//                setOnClickListener {
//
//                    myPageViewModel.logout()
//                }
//            }
        }

        observing {
            isConfirmed.observe(requireActivity(), { isConfirmed ->
                when (isConfirmed) {
                    true -> {
                        deleteAutoLogin()

                        Application.instance?.userUID      = null
                        Application.instance?.userName     = null
                        Application.instance?.userEmail    = null
                        Application.instance?.userType     = null
                        Application.instance?.userFcmToken = null

                        goNext(LoginActivity::class.java)
                    }

                }
            })
        }
    }



    private fun observing(action: MyPageViewModel.() -> Unit) {
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