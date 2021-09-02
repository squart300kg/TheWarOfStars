package com.the.war.of.thewarofstars.repository

import com.the.war.of.thewarofstars.api.ChattingApi
import com.the.war.of.thewarofstars.api.YoutubeApi
import com.the.war.of.thewarofstars.model.ChattingItem
import com.the.war.of.thewarofstars.model.response.ChattingResponse
import com.the.war.of.thewarofstars.model.response.FreeLecturesResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by sangyoon on 2021/07/27
 */
class FreeLectureRepositoryImp(
    private val youtubeApi: YoutubeApi
): FreeLectureRepository {

    override fun getFreeLectures(playListId: String, apiKey: String): Flow<FreeLecturesResponse> {
        return flow {
            val data = youtubeApi.getFreeLectures(
                playListId = playListId,
                key = apiKey)
            emit(data)
        }
    }

}