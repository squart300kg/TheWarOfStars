package com.the.war.of.thewarofstars.ui.mypage.sub

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.the.war.of.thewarofstars.R
import com.the.war.of.thewarofstars.BR
import com.the.war.of.thewarofstars.base.BaseViewHolder
import com.the.war.of.thewarofstars.databinding.ItemConvertHistoryBinding
import com.the.war.of.thewarofstars.databinding.ItemMainGamerListBinding
import com.the.war.of.thewarofstars.model.ConvertItem
import com.the.war.of.thewarofstars.model.GamerItem
import com.the.war.of.thewarofstars.ui.home.sub.GamerDetailActivity

class ConvertAdapter(
    val context: Context
): RecyclerView.Adapter<ConvertAdapter.ConvertViewHolder>() {

    private val items: MutableList<ConvertItem> = mutableListOf(
        ConvertItem(
            date = "21년 10월 9일 16시 50분",
            account = "135,000원",
            state = "환전 진행중"
        ),
        ConvertItem(
            date = "21년 10월 8일 11시 50분",
            account = "190,000원",
            state = "환전 완료"
        ),
        ConvertItem(
            date = "21년 10월 4일 11시 50분",
            account = "123,000원",
            state = "환전 완료"
        ) ,
        ConvertItem(
            date = "21년 9월 28일 11시 50분",
            account = "150,000원",
            state = "환전 완료"
        ) ,
        ConvertItem(
            date = "21년 09월 09일 11시 10분",
            account = "170,000원",
            state = "환전 완료"
        ) ,
        ConvertItem(
            date = "21년 09월 08일 16시 50분",
            account = "111,000원",
            state = "환전 완료"
        ) ,
        ConvertItem(
            date = "21년 09월 07일 16시 50분",
            account = "150,000원",
            state = "환전 완료"
        ) ,
        ConvertItem(
            date = "21년 09월 06일 16시 50분",
            account = "120,000원",
            state = "환전 완료"
        ) ,
        ConvertItem(
            date = "21년 09월 3일 16시 50분",
            account = "135,000원",
            state = "환전 완료"
        ) ,
        ConvertItem(
            date = "21년 09월 2일 12시 50분",
            account = "135,000원",
            state = "환전 완료"
        ) ,
        ConvertItem(
            date = "21년 09월 1일 16시 50분",
            account = "135,000원",
            state = "환전 완료"
        ) ,
        ConvertItem(
            date = "21년 09월 1일 12시 50분",
            account = "135,000원",
            state = "환전 완료"
        ) ,
        ConvertItem(
            date = "21년 08월 19일 16시 50분",
            account = "170,000원",
            state = "환전 완료"
        )
    )

    private val TAG = "GamerListAdapterLog"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConvertViewHolder {
        return ConvertViewHolder(
            BR.convertItem,
            parent,
            R.layout.item_convert_history)
    }


    override fun onBindViewHolder(holder: ConvertViewHolder, position: Int) {
        holder.bindItem(items[position])

    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun loadConvertList(list: List<ConvertItem>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()

        Log.i(TAG, list.toString())
        Log.i(TAG, list.size.toString())
        Log.i(TAG, items.size.toString())
    }

    class ConvertViewHolder(
        itemId: Int,
        parent: ViewGroup,
        layoutRes: Int
    ): BaseViewHolder<ConvertItem, ItemConvertHistoryBinding>(itemId, parent, layoutRes)

}