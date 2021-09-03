package com.the.war.of.thewarofstars.api

import com.the.war.of.thewarofstars.model.response.FreeLecturesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface YoutubeApi {

    @GET("playlistItems")
    suspend fun getFreeLectures(
        @Query("part")part: String = "snippet",
        @Query("fields")fields: String = "items(snippet(title, resourceId.videoId, thumbnails.high.url))",
        @Query("key")key: String,
        @Query("playlistId")playListId: String,
    ): FreeLecturesResponse

}