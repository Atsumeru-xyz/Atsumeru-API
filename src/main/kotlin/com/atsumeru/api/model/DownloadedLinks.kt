package com.atsumeru.api.model

import com.google.gson.annotations.SerializedName

class DownloadedLinks {
    var downloaded: List<String>? = null
        get() = if (field == null) ArrayList() else field

    @SerializedName("not_downloaded")
    var notDownloaded: List<String>? = null
        get() = if (field == null) ArrayList() else field
}