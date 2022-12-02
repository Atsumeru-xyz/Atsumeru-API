package com.atsumeru.api.model

import com.atsumeru.api.utils.itemOrEmpty
import com.google.gson.annotations.SerializedName
import java.io.Serializable

open class Serie : Serializable {
    var id: String? = null
        get() = itemOrEmpty(field)

    var link: String? = null
        get() = itemOrEmpty(field)

    var links: List<Link>? = null
        get() = if (field == null) ArrayList() else field

    @SerializedName("is_single")
    var isSingle: Boolean = false

    var title: String? = null
        get() = itemOrEmpty(field)

    @SerializedName("alt_title")
    var altTitle: String? = null
        get() = itemOrEmpty(field)

    @SerializedName("jap_title")
    var japTitle: String? = null
        get() = itemOrEmpty(field)

    @SerializedName("kor_title")
    var korTitle: String? = null
        get() = itemOrEmpty(field)

    var folder: String? = null
        get() = itemOrEmpty(field)

    var volume: Float = -1f

    var cover: String? = null
        get() = itemOrEmpty(field)

    @SerializedName("cover_accent")
    var coverAccent: String? = null
        get() = itemOrEmpty(field)

    var publisher: String? = null
        get() = itemOrEmpty(field)

    var authors: List<String>? = null
        get() = if (field == null) ArrayList() else field

    var artists: List<String>? = null
        get() = if (field == null) ArrayList() else field

    var translators: List<String>? = null
        get() = if (field == null) ArrayList() else field

    var genres: List<String>? = null
        get() = if (field == null) ArrayList() else field

    var tags: List<String>? = null
        get() = if (field == null) ArrayList() else field

    var year: String? = null
        get() = itemOrEmpty(field)

    var country: String? = null
        get() = itemOrEmpty(field)

    var languages: List<String>? = null
        get() = if (field == null) ArrayList() else field

    @SerializedName("content_type")
    var contentType: String? = null
        get() = itemOrEmpty(field)

    var description: String? = null
        get() = itemOrEmpty(field)

    var related: String? = null
        get() = itemOrEmpty(field)

    var event: String? = null
        get() = itemOrEmpty(field)

    @SerializedName("is_mature")
    var isMature = false

    @SerializedName("is_adult")
    var isAdult = false

    @SerializedName("volumes_count")
    var volumesCount: Long = -1

    @SerializedName("chapters_count")
    var chaptersCount: Long = -1

    var status: String? = null
        get() = itemOrEmpty(field)

    @SerializedName("translation_status")
    var translationStatus: String? = null
        get() = itemOrEmpty(field)

    var censorship: String? = null
        get() = itemOrEmpty(field)

    var parodies: List<String>? = null
        get() = if (field == null) ArrayList() else field

    var circles: List<String>? = null
        get() = if (field == null) ArrayList() else field

    var magazines: List<String>? = null
        get() = if (field == null) ArrayList() else field

    var characters: List<String>? = null
        get() = if (field == null) ArrayList() else field

    var color: String? = null
        get() = itemOrEmpty(field)

    var rating = 0

    var score: String? = null
        get() = itemOrEmpty(field)

    @SerializedName("created_at")
    var createdAt: Long = -1

    @SerializedName("updated_at")
    var updatedAt: Long = -1

    var categories: List<String>? = null
        get() = if (field == null) ArrayList() else field

    var volumes: List<Volume>? = null
        get() = if (field == null) ArrayList() else field

    @SerializedName("bound_services")
    var boundServices: List<BoundService>? = null
        get() = if (field == null) ArrayList() else field
}
