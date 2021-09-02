package com.the.war.of.thewarofstars.model.response


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class FreeLecturesResponse(
    @Expose
    @SerializedName("etag")
    val etag: String?,
    @Expose
    @SerializedName("items")
    val items: List<Item>?,
    @Expose
    @SerializedName("kind")
    val kind: String?,
    @Expose
    @SerializedName("nextPageToken")
    val nextPageToken: String?,
    @Expose
    @SerializedName("pageInfo")
    val pageInfo: PageInfo?
) {
    fun toPresentation(): List<Item> {
        val list = mutableListOf<Item>()
        items?.forEach {
            list.add(it)
        }
        return list
    }
    data class Item(
        @Expose
        @SerializedName("contentDetails")
        val contentDetails: ContentDetails?,
        @Expose
        @SerializedName("etag")
        val etag: String?,
        @Expose
        @SerializedName("id")
        val id: String?,
        @Expose
        @SerializedName("kind")
        val kind: String?
    ) {
        data class ContentDetails(
            @Expose
            @SerializedName("videoId")
            val videoId: String?,
            @Expose
            @SerializedName("videoPublishedAt")
            val videoPublishedAt: String?
        )
    }
}