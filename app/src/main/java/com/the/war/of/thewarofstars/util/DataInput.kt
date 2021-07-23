package com.the.war.of.thewarofstars.util

import android.util.Log
import com.google.firebase.Timestamp
import com.the.war.of.thewarofstars.Application
import java.util.*


object DataInput {
    fun reviewListInsert() {

        val TAG = "reviewListInsertLog"
        val gamerNameList = arrayListOf(
            "김택용", "김명운", "임요환",
            "안기효", "김성현", "이성은",
            "이영호", "허영무", "이제동",
            "송병구", "김준영")

        for (i in 0 until gamerNameList.size) {
            val review = hashMapOf(
                "content" to "한 번의 게임과 한 번의 피드백이 얼마나 도움이 되겠어?\\n\\n  했습니다. 하지만 막상 해보니 생각이 달라졌어요. 이 한 판의 게임과 한 번의 피드백이 저의 게임 실력을 많이 끌어올리진 않았습니다. 다만, 앞으로 어떻게 연습해야 할지 명확히 알았죠. 무엇을 해야하고 무엇을 하지 말아야할지 방향이 잡힌 느낌입니다.\\n\\n  솔직히 다른 분들은 안받았으면 좋겠다는 생각했습니다.. 이 좋은걸 저 혼자 독차지하고 싶으니까요.. 하지만, E스포츠의 번영과 부흥을 위해 이렇게 후기를 남김니다. 아무튼 강추합니다!",
                "gamer" to gamerNameList[i],
                "nickname" to "스타가좋아",
                "score" to 5,
                "date" to Timestamp(Date())
            )
            // 30 ~ 60개 사이의 랜덤한 리뷰 리스트 생성
            for (j in 0 until ((Math.random() * 20).toInt())) {
                Application.instance?.firebaseDB
                    ?.collection("ReviewList")
                    ?.add(review)
                    ?.addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
                    ?.addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
            }

        }



    }
}