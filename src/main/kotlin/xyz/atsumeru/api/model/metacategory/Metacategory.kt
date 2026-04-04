package xyz.atsumeru.api.model.metacategory

import xyz.atsumeru.api.utils.itemOrEmpty
import java.io.Serializable

class Metacategory : Serializable {
    var id: String? = null
        get() = itemOrEmpty(field)

    var name: String? = null
        get() = itemOrEmpty(field)

    var count: Int = 0
}
