package com.the.war.of.thewarofstars.ui.message

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.the.war.of.thewarofstars.R
import com.the.war.of.thewarofstars.base.BaseFragment
import com.the.war.of.thewarofstars.databinding.FragmentMessageBinding
import com.the.war.of.thewarofstars.ui.home.BannerAdapter
import com.the.war.of.thewarofstars.ui.home.sub.question.ChattingAdapter
import com.the.war.of.thewarofstars.ui.home.sub.question.ChattingListAdapter

class MessageFragment : BaseFragment<FragmentMessageBinding>(R.layout.fragment_message) {

    private val messageViewModel: MessageViewModel by viewModels()

    private lateinit var chattingListAdapter: ChattingListAdapter

    private val TAG = "MessageFragmentLog"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding {
            messageVm = messageViewModel

            rvChattingList.apply {
                setHasFixedSize(true)
                val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                chattingListAdapter = ChattingListAdapter(requireActivity())
                layoutManager = linearLayoutManager
                adapter = chattingListAdapter
            }


        }

    }

    override fun onStart() {
        super.onStart()
        if (messageViewModel.chattingList.value == null) {
            messageViewModel.getChattingList()
        }
    }

}