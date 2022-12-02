package com.atsumeru.api.model

import com.atsumeru.api.utils.itemOrEmpty
import java.io.Serializable

class Link : Serializable {
    var source: String? = null
        get() = itemOrEmpty(field)

    var link: String? = null
        get() = itemOrEmpty(field)
}