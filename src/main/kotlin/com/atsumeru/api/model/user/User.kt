package com.atsumeru.api.model.user

import com.atsumeru.api.utils.itemOrEmpty
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class User : Serializable {
    var id: Long = -1

    @SerializedName("user_name")
    var userName: String? = null
        get() = itemOrEmpty(field)

    var password: String? = null
        get() = itemOrEmpty(field)

    var roles: List<String>? = null
        get() = if (field == null) ArrayList() else field

    var authorities: List<String>? = null
        get() = if (field == null) ArrayList() else field

    @SerializedName("allowed_categories")
    var allowedCategories: List<String>? = null
        get() = if (field == null) ArrayList() else field

    @SerializedName("disallowed_genres")
    var disallowedGenres: List<String>? = null
        get() = if (field == null) ArrayList() else field

    @SerializedName("disallowed_tags")
    var disallowedTags: List<String>? = null
        get() = if (field == null) ArrayList() else field
}
