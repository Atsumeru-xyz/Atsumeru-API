package xyz.atsumeru.api.model

import xyz.atsumeru.api.utils.itemOrEmpty
import java.io.Serializable

class Link : Serializable {
    var source: String? = null
        get() = itemOrEmpty(field)

    var link: String? = null
        get() = itemOrEmpty(field)
}