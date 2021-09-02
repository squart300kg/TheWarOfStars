package com.the.war.of.thewarofstars.model.response


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PageInfo(
    @Expose
    @SerializedName("resultsPerPage")
    val resultsPerPage: Int?,
    @Expose
    @SerializedName("totalResults")
    val totalResults: Int?
)