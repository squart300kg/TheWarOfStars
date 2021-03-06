package com.the.war.of.thewarofstars.repository

import com.the.war.of.thewarofstars.api.FunctionApi
import com.the.war.of.thewarofstars.model.ChattingItem
import com.the.war.of.thewarofstars.model.response.ChattingResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by sangyoon on 2021/07/27
 */
class ChattingRepositoryImp(
    private val functionApi: FunctionApi
): ChattingRepository {
    override fun sendMessage(chattingItem: ChattingItem): Flow<ChattingResponse> {
        return flow {
            val data = functionApi.sendMessage(
                to          = chattingItem.to.toString(),
                from        = chattingItem.from.toString(),
                content     = chattingItem.content.toString(),
                type        = chattingItem.type.toString()
            )
            emit(data)
        }
    }

}