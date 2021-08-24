package com.the.war.of.thewarofstars.ui.message

import android.content.Intent
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
import com.the.war.of.thewarofstars.ui.home.sub.question.QuestionActivity

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
                chattingListAdapter = ChattingListAdapter(requireActivity()) {
                    // TODO 실서비스 오픈하면 이 부분도 채팅 구현해야 함
                    Intent(requireActivity(), QuestionActivity::class.java).apply {
                        putExtra("senderName", "스타가 배우고싶은 아이유")
                        putExtra("isDummy", true)
                        startActivity(this)
                    }
                }
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