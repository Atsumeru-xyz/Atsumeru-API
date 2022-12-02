package com.atsumeru.api.model.info

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Stats : Serializable {
    @SerializedName("total_series")
    var totalSeries: Long = 0

    @SerializedName("total_archives")
    var totalArchives: Long = 0

    @SerializedName("total_chapters")
    var totalChapters: Long = 0

    @SerializedName("total_categories")
    var totalCategories: Long = 0
}