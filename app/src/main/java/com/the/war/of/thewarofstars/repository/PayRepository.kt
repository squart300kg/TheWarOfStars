package com.the.war.of.thewarofstars.repository

import com.the.war.of.thewarofstars.model.ChattingItem
import com.the.war.of.thewarofstars.model.PayCompleteNotiItem
import com.the.war.of.thewarofstars.model.PayNotiItem
import com.the.war.of.thewarofstars.model.response.ChattingResponse
import com.the.war.of.thewarofstars.model.response.PayCompleteResponse
import com.the.war.of.thewarofstars.model.response.PayResponse
import kotlinx.coroutines.flow.Flow

/**
 * Created by sangyoon on 2021/07/27
 */
interface PayRepository {

    fun sendPayNotification(payNotiItem: PayNotiItem): Flow<PayResponse>
    fun sendPayCompleteNotification(payCompleteNotiItem : PayCompleteNotiItem): Flow<PayCompleteResponse>

}