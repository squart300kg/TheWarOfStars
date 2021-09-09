package com.the.war.of.thewarofstars.repository

import com.the.war.of.thewarofstars.api.FunctionApi
import com.the.war.of.thewarofstars.model.ChattingItem
import com.the.war.of.thewarofstars.model.PayNotiItem
import com.the.war.of.thewarofstars.model.response.ChattingResponse
import com.the.war.of.thewarofstars.model.response.PayResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by sangyoon on 2021/07/27
 */
class PayRepositoryImp(
    private val functionApi: FunctionApi
): PayRepository {

    override fun sendPayNotification(payNotiItem: PayNotiItem): Flow<PayResponse> {
        return flow {
            val data = functionApi.sendPayNotification(
                to          = payNotiItem.to.toString(),
                from        = payNotiItem.from.toString(),
            )
            emit(data)
        }
    }

}