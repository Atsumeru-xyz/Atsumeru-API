package com.atsumeru.api.model

import com.atsumeru.api.utils.itemOrEmpty
import com.google.gson.annotations.SerializedName

class Volume : Readable() {
    @SerializedName("additional_title")
    var additionalTitle: String? = null
        get() = itemOrEmpty(field)

    var year: String? = null
        get() = itemOrEmpty(field)

    @SerializedName("file_name")
    var fileName: String? = null
        get() = itemOrEmpty(field)

    @SerializedName("file_path")
    var filePath: String? = null
        get() = itemOrEmpty(field)

    var volume: Float = -1.0f
}