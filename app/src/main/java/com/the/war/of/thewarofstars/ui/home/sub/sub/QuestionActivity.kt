package com.the.war.of.thewarofstars.ui.home.sub.sub

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import com.smarteist.autoimageslider.IndicatorView.utils.DensityUtils.dpToPx
import com.the.war.of.thewarofstars.Application
import com.the.war.of.thewarofstars.BaseActivity
import com.the.war.of.thewarofstars.R
import com.the.war.of.thewarofstars.databinding.ActivityQuestionBinding
import com.the.war.of.thewarofstars.model.ChattingItem
import com.the.war.of.thewarofstars.util.KeyboardVisibilityUtils
import org.koin.androidx.viewmodel.ext.android.viewModel

class QuestionActivity: BaseActivity<ActivityQuestionBinding>(R.layout.activity_question) {

    private val questionviewModel: QuestionViewModel by viewModel()

    lateinit var chattingAdapter: ChattingAdapter
    lateinit var gamerUID: String
    lateinit var gamerName: String
    lateinit var gamerEmail : String

    lateinit var userName: String

    private lateinit var keyboardVisibilityUtils: KeyboardVisibilityUtils

    private val TAG = "QuestionActivityLog"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        /**
         * 채팅 프로세스
         * === 기기 안에서 ===
         * 1. 메시지를 입력
         * 2. '전송' 클릭
         * 3. 메시지가 없으면 이벤트 없도록
         * 4. '메시지 값'을 액티비티로 가져옴
         * 5. 키보드 내림
         * 6. '입력창' 지워줌
         * 7. '채팅창'에 '메시지'표시
         *  7.1. '채팅 어댑터'에 '메시지'를 추가
         * 8. 포커스를 마지막 메시지로 이동한다
         *
         *  === 네트워크 통신 ===
         *  1. FireFunction서버로 '메시지'를 보낸다.
         *   1.1. '메시지'는 다음 정보를 담는다. '유저 정보', '전송 내용', '전송 시각', '보내는 선수'
         *  2. FireFunction에서 두 가지 처리를 한다.
         *   2.1.  받은 메시지를 DB에 저장
         *   2.2. '선수'에게 Noti 보내기
         *
         */

        /**
         * 선수에게 노티를 보내기 위해선? 선수의 FCM토큰을 알아야 한다.
         * 선수의 FCM토큰 저장을 위해선? 선수가 로그인 해야 한다.
         * 선수의 로그인, 유저의 로그인을 분리해야한다.
         *
         * 우선, 선수의 계정은 내가 따로 공급해준다.
         * 1. 아이디와 비밀번호를 공급해준다.
         * 2. 스스로 변경할 수 있게한다.
         * 3. 공급한 아이디로 선수가 로그인할 때, FCM토큰을 FireStore에 저장한다.
         */
        binding {
            tvChattingDescription.apply {
                gamerUID   = intent.getStringExtra("uID").toString()
                gamerName  = intent.getStringExtra("name").toString()
                gamerEmail = intent.getStringExtra("email").toString()

                userName   = Application.instance?.userNickname.toString()

                val roomTitle = gamerName + "선수님께 보내는 메시지"
                this.text = roomTitle
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
                        outRect.bottom = dpToPx(6)
                        outRect.top = dpToPx(6)
                        outRect.right = dpToPx(6)

                    }
                })

            }

            // 키보드가 올라올 때, 최신 메시지가 보이게 설정
            keyboardVisibilityUtils = KeyboardVisibilityUtils(
                window,
                onShowKeyboard = {
                    rvChatting.apply {
//                        smoothScrollTo(scrollX, scrollY + keyboardHeight)
                        if (chattingAdapter.itemCount != 0)
                            smoothScrollToPosition(chattingAdapter.itemCount - 1)
                    }
                },
                onHideKeyboard = { }
            )

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
                            to          = gamerUID,
                            from        = userName,
                            content     = message,
                            currentTime = Timestamp.now()
                        )
                    )
                    // 7. 포커스를 마지막 메시지로 이동한다
                    rvChatting.smoothScrollToPosition(chattingAdapter.itemCount - 1)

                    // 8. 네트워크 통신을 시작한다
                    questionviewModel.sendMessage(
                        ChattingItem(
                            to          = gamerUID,
                            from        = userName,
                            content     = message,
                            currentTime = Timestamp.now()
                        )
                    )
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

    companion object {

    }
}