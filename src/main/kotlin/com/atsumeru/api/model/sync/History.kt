package com.atsumeru.api.model.sync

import com.atsumeru.api.utils.itemOrEmpty
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class History : Serializable {
    @SerializedName("serie_hash")
    var serieHash: String? = null
        get() = itemOrEmpty(field)

    @SerializedName("archive_hash")
    var archiveHash: String? = null
        get() = itemOrEmpty(field)

    @SerializedName("current_page")
    var currentPage = Integer.MIN_VALUE

    @SerializedName("pages_count")
    var pagesCount = Integer.MIN_VALUE

    @SerializedName("last_read_at")
    var lastReadAt = 0L
}
