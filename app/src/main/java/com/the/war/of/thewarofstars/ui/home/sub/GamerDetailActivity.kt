package com.the.war.of.thewarofstars.ui.home.sub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.the.war.of.thewarofstars.BaseActivity
import com.the.war.of.thewarofstars.R
import com.the.war.of.thewarofstars.databinding.ActivityGamerDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class GamerDetailActivity: BaseActivity<ActivityGamerDetailBinding>(R.layout.activity_gamer_detail) {

    private val gamerDetailViewModel: GamerDetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding {

        }

        observing {


        }


    }

    override fun onResume() {
        super.onResume()

    }

    private fun observing(action: GamerDetailViewModel.() -> Unit) {
        gamerDetailViewModel.run(action)

    }
}