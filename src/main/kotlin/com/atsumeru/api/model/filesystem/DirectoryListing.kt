package com.atsumeru.api.model.filesystem

import java.io.Serializable

class DirectoryListing : Serializable {
    var parent: String? = null

    var directories: List<Path>? = null
        get() = if (field == null) ArrayList() else field
}