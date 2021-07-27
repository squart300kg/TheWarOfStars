package com.the.war.of.thewarofstars.repository

import com.the.war.of.thewarofstars.model.ChattingItem
import com.the.war.of.thewarofstars.model.response.ChattingResponse
import kotlinx.coroutines.flow.Flow

/**
 * Created by sangyoon on 2021/07/27
 */
interface ChattingRepository {

    fun sendMessage(chattingItem: ChattingItem): Flow<ChattingResponse>

}