package com.the.war.of.thewarofstars.repository

import com.the.war.of.thewarofstars.api.ChattingApi
import com.the.war.of.thewarofstars.model.ChattingItem
import com.the.war.of.thewarofstars.model.response.ChattingResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by sangyoon on 2021/07/27
 */
class ChattingRepositoryImp(
    private val chattingApi: ChattingApi
): ChattingRepository {
    override fun sendMessage(chattingItem: ChattingItem): Flow<ChattingResponse> {
        return flow {
            val data = chattingApi.sendMessage(
                to          = chattingItem.to,
                from        = chattingItem.from,
                content     = chattingItem.content,
                currentTime = chattingItem.currentTime
            )
            emit(data)
        }
    }

}