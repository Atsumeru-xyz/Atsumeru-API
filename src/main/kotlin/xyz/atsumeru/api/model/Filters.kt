package xyz.atsumeru.api.model

import com.google.gson.annotations.SerializedName
import xyz.atsumeru.api.utils.itemOrEmpty
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
