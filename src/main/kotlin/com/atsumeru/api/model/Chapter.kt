package com.atsumeru.api.model

import com.atsumeru.api.utils.itemOrEmpty
import com.google.gson.annotations.SerializedName

class Chapter : Readable() {
    @SerializedName("alt_title")
    var altTitle: String? = null
        get() = itemOrEmpty(field)

    var folder: String? = null
        get() = itemOrEmpty(field)

    var artists: List<String>? = null
        get() = if (field == null) ArrayList() else field

    var translators: List<String>? = null
        get() = if (field == null) ArrayList() else field

    var languages: List<String>? = null
        get() = if (field == null) ArrayList() else field

    var parodies: List<String>? = null
        get() = if (field == null) ArrayList() else field

    var characters: List<String>? = null
        get() = if (field == null) ArrayList() else field

    var censorship: String? = null
        get() = itemOrEmpty(field)

    var color: String? = null
        get() = itemOrEmpty(field)

    var description: String? = null
        get() = itemOrEmpty(field)

    var genres: List<String>? = null
        get() = if (field == null) ArrayList() else field

    var tags: List<String>? = null
        get() = if (field == null) ArrayList() else field
}