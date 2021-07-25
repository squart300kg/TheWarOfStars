package com.the.war.of.thewarofstars.ui.home.sub.sub

import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import com.smarteist.autoimageslider.IndicatorView.utils.DensityUtils
import com.smarteist.autoimageslider.IndicatorView.utils.DensityUtils.dpToPx
import com.the.war.of.thewarofstars.BaseActivity
import com.the.war.of.thewarofstars.R
import com.the.war.of.thewarofstars.databinding.ActivityQuestionBinding
import com.the.war.of.thewarofstars.model.ChattingItem
import com.the.war.of.thewarofstars.util.KeyboardVisibilityUtils
import java.time.LocalDateTime

class QuestionActivity: BaseActivity<ActivityQuestionBinding>(R.layout.activity_question) {

    lateinit var chattingAdapter: ChattingAdapter

    private lateinit var keyboardVisibilityUtils: KeyboardVisibilityUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        /**
         * 채팅 프로세스
         * 1. 메시지를 입력
         * 2. '전송' 클릭
         * 3. 메시지가 없으면 이벤트 없도록
         * 4. '메시지 값'을 액티비티로 가져옴
         * 5. 키보드 내림
         * 6. '입력창' 지워줌
         * 7. '채팅창'에 '메시지'표시
         *  7.1. '채팅 어댑터'에 '메시지'를 추가
         * 8. 포커스를 마지막 메시지로 이동한다
         */

        binding {
            tvChattingDescription.apply {
                this.text = intent.getStringExtra("name") + "선수님께 보내는 메시지"
            }

            rvChatting.apply {
                setHasFixedSize(false)
                val linearLayout = LinearLayoutManager(this@QuestionActivity, RecyclerView.VERTICAL, false)
                chattingAdapter = ChattingAdapter(this@QuestionActivity)

                layoutManager = linearLayout
                adapter = chattingAdapter

                addItemDecoration(object: RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(
                        outRect: Rect,
                        view: View,
                        parent: RecyclerView,
                        state: RecyclerView.State
                    ) {
                        super.getItemOffsets(outRect, view, parent, state)
                        outRect.bottom = DensityUtils.dpToPx(6)
                        outRect.top = DensityUtils.dpToPx(6)
                        outRect.right = DensityUtils.dpToPx(6)

                    }
                })

            }

            // 최신 메시지가 보이게 설정
            keyboardVisibilityUtils = KeyboardVisibilityUtils(window,
                onShowKeyboard = { keyboardHeight ->
                    rvChatting.apply {
//                        smoothScrollTo(scrollX, scrollY + keyboardHeight)
                        if (chattingAdapter.itemCount != 0)
                            smoothScrollToPosition(chattingAdapter.itemCount - 1)
                    }
                })

            // 메시지 전송
            tvChattingSend.apply {
                setOnClickListener {
                    // 1. 메시지 입력 & '전송' 클릭
                    // 2. 메시지가 없으면 이벤트 없도록
                    if (etChattingInput.text.isNullOrEmpty())
                        return@setOnClickListener

                    // 3. '메시지 값'을 액티비티로 가져옴
                    val message = etChattingInput.text.toString()
                    // 4. 키보드 내림
                    hideKeyBoard(etChattingInput)
                    // 5. '입력창' 지워줌
                    etChattingInput.text.clear()
                    // 6. '채팅 어댑터'에 '메시지'를 추가
                    chattingAdapter.addOneBalloon(
                        ChattingItem(
                            message,
                            Timestamp.now())
                    )
                    // 7. 포커스를 마지막 메시지로 이동한다
                    rvChatting.smoothScrollToPosition(chattingAdapter.itemCount - 1)
                }
            }
        }


    }

    override fun onDestroy() {
        keyboardVisibilityUtils.detachKeyboardListeners()
        super.onDestroy()
    }
    private fun hideKeyBoard(input: EditText) {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(input.windowToken, 0)
        input.clearFocus()
    }
}