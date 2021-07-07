package com.the.war.of.thewarofstars.ui.home

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.the.war.of.thewarofstars.R
import com.the.war.of.thewarofstars.base.BaseFragment
import com.the.war.of.thewarofstars.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val homeViewModel: HomeViewModel by viewModels()

    private var gamerListAdapter: GamerListAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding {
            // TODO saucelive latestAdapter참고할 것
            gamerListRecyclerView.apply {
                val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                layoutManager = linearLayoutManager
                adapter = GamerListAdapter()

            }
        }

        homeViewModel.text.observe(viewLifecycleOwner, Observer {

        })

    }

}