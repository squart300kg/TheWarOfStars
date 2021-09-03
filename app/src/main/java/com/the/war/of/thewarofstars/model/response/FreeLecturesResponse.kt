package com.the.war.of.thewarofstars.model.response


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class FreeLecturesResponse(
    @Expose
    @SerializedName("items")
    val items: List<Item>?

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
        @SerializedName("snippet")
        val snippet: Snippet?
    ) {
        data class Snippet(
            @Expose
            @SerializedName("resourceId")
            val resourceId: ResourceId?,
            @Expose
            @SerializedName("title")
            val title: String?,
            @Expose
            @SerializedName("thumbnails")
            val thumbnails: Thumbnails?

        ) {
            data class Thumbnails(
                @Expose
                @SerializedName("high")
                val high: High?
            ) {
                data class High(
                    @Expose
                    @SerializedName("url")
                    val url: String?
                )
            }
            data class ResourceId(
                @Expose
                @SerializedName("videoId")
                val videoId: String?
            )
        }

    }
}