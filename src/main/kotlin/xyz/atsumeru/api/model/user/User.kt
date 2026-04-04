package xyz.atsumeru.api.model.user

import com.google.gson.annotations.SerializedName
import xyz.atsumeru.api.utils.itemOrEmpty
import java.io.Serializable
import java.util.stream.Stream

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

    fun isImporter(): Boolean {
        return Stream.concat(authorities?.stream(), roles?.stream())
            .anyMatch { role -> role == "IMPORTER" }
    }

    fun isUploader(): Boolean {
        return Stream.concat(authorities?.stream(), roles?.stream())
            .anyMatch { role -> role == "UPLOADER" }
    }

    fun isDownloader(): Boolean {
        return Stream.concat(authorities?.stream(), roles?.stream())
            .anyMatch { role -> role == "DOWNLOAD_FILES" }
    }

    fun isMetadataUpdater(): Boolean {
        return Stream.concat(authorities?.stream(), roles?.stream())
            .anyMatch { role -> role == "METADATA_UPDATER" }
    }

    fun isAdmin(): Boolean {
        return Stream.concat(authorities?.stream(), roles?.stream())
            .anyMatch { role -> role == "ADMIN" || role == "ROLE_ADMIN" }
    }
}
