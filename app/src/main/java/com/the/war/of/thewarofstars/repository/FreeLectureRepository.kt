package com.the.war.of.thewarofstars.repository

import com.the.war.of.thewarofstars.model.response.FreeLecturesResponse
import kotlinx.coroutines.flow.Flow

/**
 * Created by sangyoon on 2021/07/27
 */
interface FreeLectureRepository {

    fun getFreeLectures(playListId: String, apiKey: String): Flow<FreeLecturesResponse>

}