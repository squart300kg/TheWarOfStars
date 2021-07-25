package com.the.war.of.thewarofstars.util

import android.graphics.Rect
import android.view.ViewTreeObserver
import android.view.Window

/**
 * Created by sangyoon on 2021/07/25
 */
class KeyboardVisibilityUtils(
    private val window: Window,
    private val onShowKeyboard: ((keyboardHeight: Int) -> Unit)? = null,
    private val onHideKeyboard: (() -> Unit)? = null
) {

    private val MIN_KEYBOARD_HEIGHT_PX = 150

    private val windowVisibleDisplayFrame = Rect()
    private var lastVisibleDecorViewHeight: Int = 0


    private val onGlobalLayoutListener = ViewTreeObserver.OnGlobalLayoutListener {
        window.decorView.getWindowVisibleDisplayFrame(windowVisibleDisplayFrame)
        val visibleDecorViewHeight = windowVisibleDisplayFrame.height()

        // 뷰의 높이 변화를 통해 키보드가 올라와있는지를 알아낸다.
        if (lastVisibleDecorViewHeight != 0) {
            if (lastVisibleDecorViewHeight > visibleDecorViewHeight + MIN_KEYBOARD_HEIGHT_PX) {
                // 현재 키보드 높이를 계산한다. (풀스크린일 때 네비게이션바 높이 또한 포함한다)
                val currentKeyboardHeight = window.decorView.height - windowVisibleDisplayFrame.bottom
                // 리스너에게 키보드가 올라왔음을 알려준다.
                onShowKeyboard?.invoke(currentKeyboardHeight)
            } else if (lastVisibleDecorViewHeight + MIN_KEYBOARD_HEIGHT_PX < visibleDecorViewHeight) {
                // 리스너에게 키보드가 내려갔음을 알려준다.
                onHideKeyboard?.invoke()
            }
        }
        // 다음 호출을 위해 현재 뷰 높이를 저장한다.
        lastVisibleDecorViewHeight = visibleDecorViewHeight
    }

    init {
        window.decorView.viewTreeObserver.addOnGlobalLayoutListener(onGlobalLayoutListener)
    }

    fun detachKeyboardListeners() {
        window.decorView.viewTreeObserver.removeOnGlobalLayoutListener(onGlobalLayoutListener)
    }
}