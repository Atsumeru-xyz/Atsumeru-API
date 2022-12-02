package com.atsumeru.api.model

import com.atsumeru.api.model.sync.History
import com.atsumeru.api.utils.itemOrEmpty
import com.google.gson.annotations.SerializedName
import java.io.Serializable

open class Readable : Serializable {
    var id: String? = null
        get() = itemOrEmpty(field)

    var title: String? = null
        get() = itemOrEmpty(field)

    @SerializedName("cover_accent")
    var coverAccent: String? = null
        get() = itemOrEmpty(field)

    @SerializedName("pages_count")
    var pagesCount = 0

    @SerializedName("is_book")
    var isBook = false

    @SerializedName("created_at")
    var createdAt = 0L

    @SerializedName("updated_at")
    var updatedAt = 0L

    var history: History? = null
        get() = if (field == null) History() else field

    fun isEbook(): Boolean {
        return isBook && pagesCount <= 1
    }
}