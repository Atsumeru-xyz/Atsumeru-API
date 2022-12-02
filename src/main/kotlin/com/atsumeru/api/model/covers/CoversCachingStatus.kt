package com.atsumeru.api.model.covers

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class CoversCachingStatus : Serializable {
    @SerializedName("covers_caching_active")
    var isCoversCachingActive: Boolean = false

    @SerializedName("running_ms")
    val runningMs: Long = 0

    val saved = 0
    val total = 0
    val percent = 0f
}