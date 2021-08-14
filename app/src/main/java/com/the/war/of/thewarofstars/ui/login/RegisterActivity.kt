package com.the.war.of.thewarofstars.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.the.war.of.thewarofstars.BaseActivity
import com.the.war.of.thewarofstars.R
import com.the.war.of.thewarofstars.databinding.ActivityRegisterBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity: BaseActivity<ActivityRegisterBinding>(R.layout.activity_register) {

    private val registerViewModel: RegisterViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding {
            registerVm = registerViewModel
        }

    }
}