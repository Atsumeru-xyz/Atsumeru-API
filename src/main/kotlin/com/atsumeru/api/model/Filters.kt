package com.atsumeru.api.model

import com.atsumeru.api.utils.itemOrEmpty
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Filters : Serializable {
    var id: String? = null
        get() = itemOrEmpty(field)

    var name: String? = null
        get() = itemOrEmpty(field)

    @SerializedName("has_and_mode")
    var hasAndMode = false

    @SerializedName("single_mode")
    var singleMode = false

    var values: List<String>? = null
        get() = if (field == null) ArrayList() else field
}
