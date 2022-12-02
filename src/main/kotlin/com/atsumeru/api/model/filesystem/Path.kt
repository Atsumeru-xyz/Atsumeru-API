package com.atsumeru.api.model.filesystem

import com.atsumeru.api.utils.itemOrEmpty
import java.io.Serializable

class Path : Serializable {
    var type: String? = null
        get() = itemOrEmpty(field)

    var name: String? = null
        get() = itemOrEmpty(field)

    var path: String? = null
        get() = itemOrEmpty(field)
}