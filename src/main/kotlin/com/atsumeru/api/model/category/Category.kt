package com.atsumeru.api.model.category

import com.atsumeru.api.utils.itemOrEmpty
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Category : Serializable {
    var id: String? = null
        get() = itemOrEmpty(field)

    var name: String? = null
        get() = itemOrEmpty(field)

    @SerializedName("content_type")
    var contentType: String? = null
        get() = itemOrEmpty(field)

    var order: Int = 0
}